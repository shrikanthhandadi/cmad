package com.cisco.iot.ccs.controller;

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

import com.cisco.iot.ccs.exception.ValidationException;
import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.Stat;
import com.cisco.iot.ccs.service.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("/v1")
@RestController("/v1/events") // ideally events path should be added here, however swagger doc not working
@CrossOrigin
public class EventController {

	private static final Logger log = LoggerFactory.getLogger(EventController.class);

	@Autowired
	private EventService eventService;

	@ApiOperation(value = "Create car event", notes = "Create")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/events")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Long> add(@RequestBody Event event) {
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
	public ResponseEntity<Page<Event>> get(
			@ApiParam("Pagination page size") @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@ApiParam("Pagination page number") @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
			@ApiParam("Make of car") @RequestParam(name = "make", required = false) String make,
			@ApiParam("Model of car") @RequestParam(name = "model", required = false) String model) {
		log.info("Started fetching events, pageSize: {}, pageNum {}, make {}, model {}", pageSize, pageNum, make,
				model);
		Page<Event> page = null;
		try {
			if (!StringUtils.isBlank(make) && !StringUtils.isBlank(model)) {
				page = eventService.get(make, model, pageSize, pageNum);
			} else if (!StringUtils.isBlank(make)) {
				page = eventService.get(make, pageSize, pageNum);
			} else {
				page = eventService.get(pageSize, pageNum);
			}
			log.info("Finished fetching events with total: {}", page.getData().size());
			return new ResponseEntity<Page<Event>>(page, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("Exception while fetching events", e);
			return new ResponseEntity<Page<Event>>(page, HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<List<Stat>> getStats(
			@ApiParam("Make of car") @RequestParam(name = "make", required = false) String make,
			@ApiParam("Model of car") @RequestParam(name = "model", required = false) String model) {
		log.info("Started stats for: make {}, model {}", make, model);
		List<Stat> stats = new ArrayList<>();
		try {
			if (!StringUtils.isBlank(make) && !StringUtils.isBlank(model)) {
				stats = eventService.getStats(make, model);
			} else if (!StringUtils.isBlank(make)) {
				stats = eventService.getStats(make);
			} else {
				stats = eventService.getStats();
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
