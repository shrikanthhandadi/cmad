package com.cisco.iot.ccs.controller;

import java.net.URI;

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

import com.cisco.iot.ccs.exception.ValidationException;
import com.cisco.iot.ccs.model.CustomerCar;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.service.CustomerCarService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("/v1")
@RestController("/v1/customerCars") // ideally cars path should be added here, however swagger doc not working
@CrossOrigin
public class CustomerCarController {

	private static final Logger log = LoggerFactory.getLogger(CustomerCarController.class);

	@Autowired
	private CustomerCarService customerCarService;

	@ApiOperation(value = "Create customer car", notes = "Create")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/customerCars")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Long> add(@RequestBody CustomerCar customerCar) {
		log.info("Started creating customerCar, data: {}", customerCar);
		ResponseEntity<Long> response;
		Long id = null;
		try {
			customerCar = customerCarService.create(customerCar);
			log.info("Finished creating customerCar");
			id = customerCar.getId();
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uri);
			response = new ResponseEntity<Long>(id, headers, HttpStatus.CREATED);
			log.info("Finished creating customerCar");
			return response;
		} catch (ValidationException e) {
			log.error("Exception while creating customerCar", e);
			return new ResponseEntity<Long>(id, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while creating customerCar", e);
			return new ResponseEntity<Long>(id, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Fetch customer car", notes = "get")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Ok", response = CustomerCar.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/customerCars/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<CustomerCar> get(@PathVariable("id") Long id) {
		log.info("Started getting customerCar, id: {}.", id);
		CustomerCar customerCar = null;
		try {
			customerCar = customerCarService.get(id);
			log.info("Finished getting customer car.");
			return new ResponseEntity<>(customerCar, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("Exception while getting customer car", e);
			return new ResponseEntity<>(customerCar, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while getting customer car", e);
			return new ResponseEntity<>(customerCar, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get all customer car details  ", notes = "get customer car")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Found", response = CustomerCar.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/customerCars")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<CustomerCar>> get(
			@ApiParam("Pagination page size") @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@ApiParam("Pagination page number") @RequestParam(name = "pageNum", defaultValue = "0") int pageNum) {
		log.info("Started fetching cars, pageSize: {}, pageNum {}", pageSize, pageNum);
		Page<CustomerCar> page = null;
		try {
			page = customerCarService.get(pageSize, pageNum);
			log.info("Finished fetching customer cars with total: {}", page.getData().size());
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("Exception while fetching customer car", e);
			return new ResponseEntity<>(page, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while fetching customer car", e);
			return new ResponseEntity<>(page, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
