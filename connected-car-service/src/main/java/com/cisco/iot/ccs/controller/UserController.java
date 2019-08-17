package com.cisco.iot.ccs.controller;

import static com.cisco.iot.ccs.model.ErrorResponse.code;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import java.net.URI;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public ResponseEntity<?> add(@RequestBody User user) {
		log.info("Started creating user, data: {}", user);
		try {
			user = userService.create(user);
			log.info("Finished creating user");
			Long id = user.getId();
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uri);
			log.info("Finished creating user");
			return status(CREATED).headers(headers).body(id);
		} catch (ValidationException e) {
			log.error("Exception while creating user", e);
			return status(BAD_REQUEST).body(code(99999).message(e.getMessage()));
		} catch (Exception e) {
			log.error("Exception while creating user", e);
			return status(INTERNAL_SERVER_ERROR).body(code(99999).message(e.getMessage()));
		}
	}

	@ApiOperation(value = "Get all user details  ", notes = "get models")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Found", response = User.class, responseContainer = "Page"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getAll(
			@ApiParam("Username") @RequestParam(name = "username", required = false) String username,
			@ApiParam("Pagination page size") @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@ApiParam("Pagination page number") @RequestParam(name = "pageNum", defaultValue = "0") int pageNum) {
		log.info("Started fetching users, pageSize: {}, pageNum {}", pageSize, pageNum);
		try {
			Page<User> page = userService.get(pageSize, pageNum);
			log.info("Finished fetching users with total: {}", page.getData().size());
			return ok().body(page);
		} catch (ValidationException e) {
			log.error("Exception while fetching user", e);
			return status(BAD_REQUEST).body(code(99999).message(e.getMessage()));
		} catch (Exception e) {
			log.error("Exception while fetching user", e);
			return status(INTERNAL_SERVER_ERROR).body(code(99999).message(e.getMessage()));
		}
	}

	@ApiOperation(value = "Delete user", notes = "Delete")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Ok", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@DeleteMapping("/users/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		log.info("Started getting user, id: {}.", id);
		boolean result = false;
		try {
			result = userService.delete(id);
			log.info("Finished getting user.");
			return ok().body(result);
		} catch (ValidationException e) {
			log.error("Exception while getting user", e);
			return status(BAD_REQUEST).body(code(99999).message(e.getMessage()));
		} catch (Exception e) {
			log.error("Exception while getting user", e);
			return status(INTERNAL_SERVER_ERROR).body(code(99999).message(e.getMessage()));
		}
	}

	@ApiOperation(value = "Get user", notes = "Get")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Ok", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/users/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> get(@ApiIgnore Principal principal, @PathVariable("id") Long id) {
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
			return ok().body(user);
		} catch (ValidationException e) {
			log.error("Exception while getting user", e);
			return status(BAD_REQUEST).body(code(99999).message(e.getMessage()));
		} catch (Exception e) {
			log.error("Exception while getting user", e);
			return status(INTERNAL_SERVER_ERROR).body(code(99999).message(e.getMessage()));
		}
	}

	@ApiOperation(value = "Get user by username", notes = "Get")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "Ok", response = Long.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/users/username/{username}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<?> get(@ApiIgnore Principal principal, @PathVariable("username") String username) {
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
			return ok().body(user);
		} catch (ValidationException e) {
			log.error("Exception while getting user", e);
			return status(BAD_REQUEST).body(code(99999).message(e.getMessage()));
		} catch (Exception e) {
			log.error("Exception while getting user", e);
			return status(INTERNAL_SERVER_ERROR).body(code(99999).message(e.getMessage()));
		}
	}

	@ApiOperation(value = "Login user", notes = "Login")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 200, message = "created", response = JwtAuthenticationResponse.class),
			@ApiResponse(code = 500, message = "Internal server error") })
	@RequestMapping(path = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user) {
		log.info("Started login for user: {}", user.getUsername());
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.generateToken(authentication);
			return ok(JwtAuthenticationResponse.token(jwt));
		} catch (NotFoundException e) {
			log.error("Exception while login user", e);
			return status(NOT_FOUND).body(code(99999).message(e.getMessage()));
		} catch (ValidationException e) {
			log.error("Exception while login user", e);
			return status(BAD_REQUEST).body(code(99999).message(e.getMessage()));
		} catch (Exception e) {
			log.error("Exception while login user {} ", e);
			return status(INTERNAL_SERVER_ERROR).body(code(99999).message(e.getMessage()));
		}
	}

}
