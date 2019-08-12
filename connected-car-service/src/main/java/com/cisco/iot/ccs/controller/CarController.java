package com.cisco.iot.ccs.controller;

import java.net.URI;
import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cisco.iot.ccs.exception.ForbiddenException;
import com.cisco.iot.ccs.exception.NotFoundException;
import com.cisco.iot.ccs.exception.ValidationException;
import com.cisco.iot.ccs.model.Car;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.User;
import com.cisco.iot.ccs.service.CarService;
import com.cisco.iot.ccs.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api("/v1")
@RestController("/v1/cars") // ideally cars path should be added here, however swagger doc not working
@CrossOrigin
public class CarController {

	private static final Logger log = LoggerFactory.getLogger(CarController.class);

	@Autowired
	private CarService carService;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Create car", notes = "Create")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/cars")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Long> add(@RequestBody Car car) {
		log.info("Started creating car, data: {}", car);
		ResponseEntity<Long> response;
		Long id = null;
		try {
			car = carService.create(car);
			log.info("Finished creating car");
			id = car.getId();
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uri);
			response = new ResponseEntity<Long>(id, headers, HttpStatus.CREATED);
			log.info("Finished creating car");
			return response;
		} catch (ValidationException e) {
			log.error("Exception while creating car", e);
			return new ResponseEntity<Long>(id, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while creating car", e);
			return new ResponseEntity<Long>(id, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get car", notes = "Get")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 200, message = "Ok", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/cars/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Car> get(@PathVariable("id") Long id) {
		log.info("Started getting car, id: {}.", id);
		Car car = null;
		try {
			car = carService.get(id);
			log.info("Finished getting car.");
			return new ResponseEntity<Car>(car, HttpStatus.OK);
		} catch (NotFoundException e) {
			log.error("Exception while getting car", e);
			return new ResponseEntity<Car>(car, HttpStatus.NOT_FOUND);
		} catch (ValidationException e) {
			log.error("Exception while getting car", e);
			return new ResponseEntity<Car>(car, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while getting car", e);
			return new ResponseEntity<Car>(car, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get all car details  ", notes = "Get all cars")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Ok", response = Car.class, responseContainer = "Page"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/cars")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Page<Car>> get(@ApiIgnore Principal principal,
			@ApiParam("Make of car") @RequestParam(name = "make", required = false) String make,
			@ApiParam("Pagination page size") @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@ApiParam("Pagination page number") @RequestParam(name = "pageNum", defaultValue = "0") int pageNum) {
		log.info("Started fetching cars, pageSize: {}, pageNum {}", pageSize, pageNum);
		Page<Car> page = null;
		try {
			User user = userService.get(principal.getName());
			if (!StringUtils.isBlank(make)) {
				if (!user.getMakes().contains(make)) {
					throw new ForbiddenException(
							"Not allowed to access make: " + make + " Allowed makes are: " + user.getMakes());
				}
				page = carService.get(make, pageSize, pageNum);
			} else {
				if (user.getRoles().contains("ROLE_ADMIN")) {
					page = carService.get(pageSize, pageNum);
				} else {
					page = carService.get(user.getMakes(), pageSize, pageNum);
				}
			}
			log.info("Finished fetching cars with total: {}", page.getData().size());
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("Exception while fetching car", e);
			return new ResponseEntity<>(page, HttpStatus.BAD_REQUEST);
		} catch (ForbiddenException e) {
			log.error("Exception while fetching events", e);
			return new ResponseEntity<>(page, HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			log.error("Exception while fetching car", e);
			return new ResponseEntity<>(page, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
