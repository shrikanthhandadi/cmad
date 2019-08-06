package com.cisco.iot.ccs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<Long> add(@ApiIgnore Principal principal, @RequestBody Event event) {
		log.info("Started creating event: {}", event);
		try {
			event = eventService.create(event);
			log.info("Finished creating event");
			return new ResponseEntity<Long>(event.getId(), HttpStatus.CREATED);
		} catch (ValidationException e) {
			log.error("Exception while creating event", e);
			return new ResponseEntity<Long>(event.getId(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while creating event", e);
			return new ResponseEntity<Long>(event.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get all car events", notes = "get models")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Found", response = Event.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/events")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Page<Event>> get(@ApiIgnore Principal principal,
			@ApiParam("Pagination page size") @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@ApiParam("Pagination page number") @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
			@ApiParam("Make of car") @RequestParam(name = "make", required = false) String make) {
		log.info("Started fetching events, pageSize: {}, pageNum {}, make {}", pageSize, pageNum, make);
		Page<Event> page = null;
		try {
			User user = userService.get(principal.getName());
			if (!StringUtils.isBlank(make)) {
				if (!user.getMakes().contains(make)) {
					throw new ForbiddenException(
							"Not allowed to access make: " + make + " Allowed makes are: " + user.getMakes());
				}
				page = eventService.get(make, pageSize, pageNum);
			} else {
				page = eventService.get(user.getMakes(), pageSize, pageNum);
			}
			log.info("Finished fetching events with total: {}", page.getData().size());
			return new ResponseEntity<Page<Event>>(page, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("Exception while fetching events", e);
			return new ResponseEntity<Page<Event>>(page, HttpStatus.BAD_REQUEST);
		} catch (ForbiddenException e) {
			log.error("Exception while fetching events", e);
			return new ResponseEntity<Page<Event>>(page, HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			log.error("Exception while fetching events", e);
			return new ResponseEntity<Page<Event>>(page, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get severity stats", notes = "get stats")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Found", response = Event.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/stats")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<Stat>> getStats(@ApiIgnore Principal principal,
			@ApiParam("Make of car") @RequestParam(name = "make", required = false) String make) {
		log.info("Started stats for: make {}", make);
		List<Stat> stats = new ArrayList<>();
		try {
			User user = userService.get(principal.getName());
			if (!StringUtils.isBlank(make)) {
				if (!user.getMakes().contains(make)) {
					throw new ForbiddenException(
							"Not allowed to access make: " + make + " Allowed makes are: " + user.getMakes());
				}
				stats = eventService.getStats(make);
			} else {
				stats = eventService.getStats(user.getMakes());
			}
			log.info("Finished fetching stats with total: {}", stats.size());
			return new ResponseEntity<>(stats, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("Validation exception while fetching events", e);
			return new ResponseEntity<>(stats, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while fetching events", e);
			return new ResponseEntity<>(stats, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
