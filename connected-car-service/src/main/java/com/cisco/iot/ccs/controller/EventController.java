package com.cisco.iot.ccs.controller;

import static com.cisco.iot.ccs.model.ErrorResponse.code;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.iot.ccs.exception.ForbiddenException;
import com.cisco.iot.ccs.exception.ValidationException;
import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.Stat;
import com.cisco.iot.ccs.model.User;
import com.cisco.iot.ccs.service.EventService;
import com.cisco.iot.ccs.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api("/v1")
@RestController("/v1/events") // ideally events path should be added here, however swagger doc not working
@CrossOrigin
public class EventController {

	private static final Logger log = LoggerFactory.getLogger(EventController.class);

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Create car event", notes = "Create")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/events")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> add(@ApiIgnore Principal principal, @RequestBody Event event) {
		log.info("Started creating event: {}", event);
		try {
			event = eventService.create(event);
			log.info("Finished creating event");
			Long id = event.getId();
			URI uri = fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uri);
			log.info("Finished creating event");
			return status(CREATED).headers(headers).body(id);
		} catch (ValidationException e) {
			log.error("Exception while creating event", e);
			return status(BAD_REQUEST).body(code(99999).message(e.getMessage()));
		} catch (Exception e) {
			log.error("Exception while creating event", e);
			return status(INTERNAL_SERVER_ERROR).body(code(99999).message(e.getMessage()));
		}
	}

	@ApiOperation(value = "Get all car events", notes = "get models")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Found", response = Event.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/events")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> get(@ApiIgnore Principal principal,
			@ApiParam("Pagination page size") @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@ApiParam("Pagination page number") @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
			@ApiParam("Make of car") @RequestParam(name = "make", required = false) String make,
			@ApiParam("Model of car") @RequestParam(name = "model", required = false) String model) {
		log.info("Started fetching events, pageSize: {}, pageNum {}, make {}", pageSize, pageNum, make);
		try {
			Page<Event> page = null;
			User user = userService.get(principal.getName());
			if (!StringUtils.isBlank(make)) {
				if (!user.getMakes().contains(make)) {
					throw new ForbiddenException(
							"Not allowed to access make: " + make + " Allowed makes are: " + user.getMakes());
				}
				if (StringUtils.isNotBlank(model)) {
					page = eventService.get(make, model, pageSize, pageNum);
				} else {
					page = eventService.get(make, pageSize, pageNum);
				}
			} else {
				page = eventService.get(user.getMakes(), pageSize, pageNum);
			}
			log.info("Finished fetching events with total: {}", page.getData().size());
			return ok(page);
		} catch (ValidationException e) {
			log.error("Exception while fetching events", e);
			return status(BAD_REQUEST).body(code(99999).message(e.getMessage()));
		} catch (ForbiddenException e) {
			log.error("Exception while fetching events", e);
			return status(FORBIDDEN).body(code(99999).message(e.getMessage()));
		} catch (Exception e) {
			log.error("Exception while fetching events", e);
			return status(INTERNAL_SERVER_ERROR).body(code(99999).message(e.getMessage()));
		}
	}

	@ApiOperation(value = "Get severity stats", notes = "get stats")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Found", response = Stat.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/stats")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getStats(@ApiIgnore Principal principal,
			@ApiParam("Make of car") @RequestParam(name = "make", required = false) String make,
			@ApiParam("Model of car") @RequestParam(name = "model", required = false) String model) {
		log.info("Started stats for: make {}, model {}", make, model);
		try {
			List<Stat> stats = new ArrayList<>();
			User user = userService.get(principal.getName());
			if (StringUtils.isNotBlank(make)) {
				if (!user.getMakes().contains(make)) {
					throw new ForbiddenException(
							"Not allowed to access make: " + make + " Allowed makes are: " + user.getMakes());
				}
				if (StringUtils.isNotBlank(model)) {
					stats = eventService.getStats(make, model);
				} else {
					stats = eventService.getStats(make);
				}
			} else {
				stats = eventService.getStats(user.getMakes());
			}
			log.info("Finished fetching stats with total: {}", stats.size());
			return ResponseEntity.ok(stats);
		} catch (ValidationException e) {
			log.error("Validation exception while fetching events", e);
			return status(BAD_REQUEST).body(code(99999).message(e.getMessage()));
		} catch (Exception e) {
			log.error("Exception while fetching events", e);
			return status(INTERNAL_SERVER_ERROR).body(code(99999).message(e.getMessage()));
		}
	}

}
