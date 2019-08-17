package com.cisco.iot.ccs.controller;

import java.net.URI;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cisco.iot.ccs.exception.ForbiddenException;
import com.cisco.iot.ccs.exception.NotFoundException;
import com.cisco.iot.ccs.exception.ValidationException;
import com.cisco.iot.ccs.model.JwtAuthenticationResponse;
import com.cisco.iot.ccs.model.Page;
import com.cisco.iot.ccs.model.User;
import com.cisco.iot.ccs.security.JwtTokenProvider;
import com.cisco.iot.ccs.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api("/v1")
@RestController("/v1/users") // ideally users path should be added here, however swagger doc not working
@CrossOrigin
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@ApiOperation(value = "Create user", notes = "Create")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Long> add(@RequestBody User user) {
		log.info("Started creating user, data: {}", user);
		ResponseEntity<Long> response;
		Long id = null;
		try {
			user = userService.create(user);
			log.info("Finished creating user");
			id = user.getId();
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uri);
			response = new ResponseEntity<Long>(id, headers, HttpStatus.CREATED);
			log.info("Finished creating user");
			return response;
		} catch (ValidationException e) {
			log.error("Exception while creating user", e);
			return new ResponseEntity<Long>(id, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while creating user", e);
			return new ResponseEntity<Long>(id, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get all user details  ", notes = "get models")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Found", response = User.class, responseContainer = "Page"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/users")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<Page<User>> getAll(
			@ApiParam("Username") @RequestParam(name = "username", required = false) String username,
			@ApiParam("Pagination page size") @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@ApiParam("Pagination page number") @RequestParam(name = "pageNum", defaultValue = "0") int pageNum) {
		log.info("Started fetching users, pageSize: {}, pageNum {}", pageSize, pageNum);
		Page<User> page = null;
		try {
			page = userService.get(pageSize, pageNum);
			log.info("Finished fetching users with total: {}", page.getData().size());
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("Exception while fetching user", e);
			return new ResponseEntity<>(page, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while fetching user", e);
			return new ResponseEntity<>(page, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Update user", notes = "Update")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Ok", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PutMapping("/users/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user) {
		log.info("Started updating user, id: {}.", id);
		try {
			user = userService.update(id, user);
			log.info("Finished updating user.");
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (NotFoundException e) {
			log.error("Exception while updating user", e);
			return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
		} catch (ValidationException e) {
			log.error("Exception while updating user", e);
			return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while updating user", e);
			return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Delete user", notes = "Delete")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Ok", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@DeleteMapping("/users/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
		log.info("Started getting user, id: {}.", id);
		boolean result = false;
		try {
			result = userService.delete(id);
			log.info("Finished getting user.");
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("Exception while getting user", e);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while getting user", e);
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get user", notes = "Get")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Ok", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/users/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<User> get(@ApiIgnore Principal principal, @PathVariable("id") Long id) {
		log.info("Started getting user, id: {}.", id);
		User user = null;
		try {
			User loggedInUser = userService.get(principal.getName());
			if (!loggedInUser.getRoles().contains("ROLE_ADMIN") && loggedInUser.getId() != id) {
				throw new ForbiddenException("User: " + loggedInUser.getUsername() + " with id: " + loggedInUser.getId()
						+ " is not allowed to access user id: " + id);
			}
			user = userService.get(id);
			log.info("Finished getting user.");
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("Exception while getting user", e);
			return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while getting user", e);
			return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get user by username", notes = "Get")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Ok", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/users/username/{username}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<User> get(@ApiIgnore Principal principal, @PathVariable("username") String username) {
		log.info("Started getting user, username: {}.", username);
		User user = null;
		try {
			User loggedInUser = userService.get(principal.getName());
			if (!loggedInUser.getRoles().contains("ROLE_ADMIN") && !loggedInUser.getUsername().equals(username)) {
				throw new ForbiddenException("User: " + loggedInUser.getUsername()
						+ " is not allowed to access use with name username: " + username);
			}
			user = userService.get(username);
			log.info("Finished getting user.");
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (ValidationException e) {
			log.error("Exception while getting user", e);
			return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while getting user", e);
			return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Login user", notes = "Login")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 201, message = "created", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping("/users/login")
	public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody User user) {
		log.info("Started login for user: {}", user.getUsername());
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.generateToken(authentication);
			return ResponseEntity.ok(JwtAuthenticationResponse.token(jwt));
		} catch (NotFoundException e) {
			log.error("Exception while login user", e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (ValidationException e) {
			log.error("Exception while login user", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Exception while login user", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
