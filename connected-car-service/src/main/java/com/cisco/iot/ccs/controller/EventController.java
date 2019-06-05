package com.cisco.iot.ccs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.iot.ccs.exception.InvalidDataException;
import com.cisco.iot.ccs.model.Event;
import com.cisco.iot.ccs.service.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("events")
@RestController
public class EventController {

	private static final Logger log = LoggerFactory.getLogger(EventController.class);

	@Autowired
	private EventService eventService;

	@ApiOperation(value = "create", notes = "create")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/events")
	public ResponseEntity<Long> add(@RequestBody Event event) {
		log.info("Started creating event: {}", event);
		try {
			event = eventService.create(event);
			log.info("Finished creating event");
			return new ResponseEntity<Long>(event.getId(), HttpStatus.CREATED);
		} catch (InvalidDataException e) {
			log.error("Exception while creating event", e);
			return new ResponseEntity<Long>(event.getId(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while creating event", e);
			return new ResponseEntity<Long>(event.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
