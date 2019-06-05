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
import com.cisco.iot.ccs.model.Car;
import com.cisco.iot.ccs.service.CarService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("cars")
@RestController
public class CarController {

	private static final Logger log = LoggerFactory.getLogger(CarController.class);

	@Autowired
	private CarService carService;

	@ApiOperation(value = "create", notes = "create")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/cars")
	public ResponseEntity<Long> add(@RequestBody Car car) {
		log.info("Started creating car: {}", car);
		try {
			car = carService.create(car);
			log.info("Finished creating car");
			return new ResponseEntity<Long>(car.getId(), HttpStatus.CREATED);
		} catch (InvalidDataException e) {
			log.error("Exception while creating car", e);
			return new ResponseEntity<Long>(car.getId(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while creating car", e);
			return new ResponseEntity<Long>(car.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
