package com.cisco.iot.ccs.controller;

import java.net.URI;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cisco.iot.ccs.exception.InvalidDataException;
import com.cisco.iot.ccs.model.Car;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.service.CarService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("/v1")
@RestController("/v1/cars") // ideally cars path should be added here, however swagger doc not working
public class CarController {

	private static final Logger log = LoggerFactory.getLogger(CarController.class);

	@Autowired
	private CarService carService;

	@ApiOperation(value = "Create car", notes = "Create")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/cars")
	public ResponseEntity<Long> add(@RequestBody Car car) {
		log.info("Started creating car, data: {}", car);
		ResponseEntity<Long> response;
		Long id = null;
		try {
			car = carService.create(car);
			log.info("Finished creating car");
			id = car.getId();
			response = new ResponseEntity<Long>(id, HttpStatus.CREATED);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
			response.getHeaders().add("Location", uri.toASCIIString());
			log.info("Finished creating car");
			return response;
		} catch (InvalidDataException e) {
			log.error("Exception while creating car", e);
			return new ResponseEntity<Long>(id, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while creating car", e);
			return new ResponseEntity<Long>(id, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Create car", notes = "Create")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/cars/{id}")
	public ResponseEntity<Car> get(@PathVariable("id") Long id) {
		log.info("Started getting car, id: {}.", id);
		Car car = null;
		try {
			car = carService.get(id);
			log.info("Finished getting car.");
			return new ResponseEntity<Car>(car, HttpStatus.OK);
		} catch (InvalidDataException e) {
			log.error("Exception while getting car", e);
			return new ResponseEntity<Car>(car, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while getting car", e);
			return new ResponseEntity<Car>(car, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get all car cars", notes = "get models")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Found", response = Car.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/cars")
	public ResponseEntity<Page<Car>> get(
			@ApiParam("Pagination page size") @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@ApiParam("Pagination page number") @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
			@ApiParam("Comma separated list of fields") @RequestParam(name = "fields", required = false) String fields,
			@ApiParam("Distinct values") @RequestParam(name = "distinct", defaultValue = "false") boolean distinct,
			@ApiParam("Make of car") @RequestParam(name = "make", required = false) String make) {
		log.info("Started fetching cars, pageSize: {}, pageNum {}, fields {}, groupBy {}", pageSize, pageNum, fields,
				distinct);
		Page<Car> page = null;
		try {
			String[] fieldsArr = null;
			if (fields != null) {
				fieldsArr = fields.split(",");
			}
			page = carService.get(make, fieldsArr, distinct, pageSize, pageNum);
			log.info("Finished fetching cars with total: {}", page.getData().size());
			return new ResponseEntity<Page<Car>>(page, HttpStatus.CREATED);
		} catch (InvalidDataException e) {
			log.error("Exception while fetching car", e);
			return new ResponseEntity<Page<Car>>(page, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while fetching car", e);
			return new ResponseEntity<Page<Car>>(page, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
