package com.cisco.iot.ccs.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.iot.ccs.exception.InvalidDataException;
import com.cisco.iot.ccs.model.CarEvent;
import com.cisco.iot.ccs.service.CarEventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("/v1")
@RestController("/v1")
public class CarEventController {

	private static final Logger log = LoggerFactory.getLogger(CarEventController.class);

	@Autowired
	private CarEventService carEventService;

	@ApiOperation(value = "Create car event", notes = "Create")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/carEvents")
	public ResponseEntity<Long> add(@RequestBody CarEvent carEvent) {
		log.info("Started creating event: {}", carEvent);
		try {
			carEvent = carEventService.create(carEvent);
			log.info("Finished creating event");
			return new ResponseEntity<Long>(carEvent.getId(), HttpStatus.CREATED);
		} catch (InvalidDataException e) {
			log.error("Exception while creating event", e);
			return new ResponseEntity<Long>(carEvent.getId(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while creating event", e);
			return new ResponseEntity<Long>(carEvent.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get car events for given make ", notes = "get car events for given make")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Found", response = CarEvent.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/carEvents/{make}")
	public ResponseEntity<List<CarEvent>> get(@ApiParam(name = "Make of car") @PathVariable String make,
			@ApiParam("Distinct group") @RequestParam boolean groupBy,
			@ApiParam("Comma separated list of fields") @RequestParam String fieldsStr) {
		List<CarEvent> carEvents = new ArrayList<CarEvent>();
		try {
			String[] fields = fieldsStr.split(",");
			carEvents = carEventService.get(make, groupBy, fields);
			return new ResponseEntity<List<CarEvent>>(carEvents, HttpStatus.CREATED);
		} catch (InvalidDataException e) {
			log.error("Exception while creating event", e);
			return new ResponseEntity<List<CarEvent>>(carEvents, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while creating event", e);
			return new ResponseEntity<List<CarEvent>>(carEvents, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get all car events", notes = "get models")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Found", response = CarEvent.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/carEvents")
	public ResponseEntity<List<CarEvent>> get(@ApiParam("Distinct group") @RequestParam boolean groupBy,
			@ApiParam("Comma separated list of fields") @RequestParam String fieldsStr,
			@ApiParam("Pagination page number") @RequestParam int pageNum,
			@ApiParam("Pagination page size") @RequestParam int pageSize) {
		List<CarEvent> carEvents = new ArrayList<CarEvent>();
		try {
			String[] fields = fieldsStr.split(",");
			carEvents = carEventService.get(groupBy, fields, pageNum, pageSize);
			return new ResponseEntity<List<CarEvent>>(carEvents, HttpStatus.CREATED);
		} catch (InvalidDataException e) {
			log.error("Exception while creating event", e);
			return new ResponseEntity<List<CarEvent>>(carEvents, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while creating event", e);
			return new ResponseEntity<List<CarEvent>>(carEvents, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
