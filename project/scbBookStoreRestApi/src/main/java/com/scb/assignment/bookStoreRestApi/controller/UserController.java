package com.scb.assignment.bookStoreRestApi.controller;

import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scb.assignment.bookStoreRestApi.exception.DuplicateItemException;
import com.scb.assignment.bookStoreRestApi.exception.InternalErrorException;
import com.scb.assignment.bookStoreRestApi.exception.InvalidParameterException;
import com.scb.assignment.bookStoreRestApi.exception.InvalidTokenException;
import com.scb.assignment.bookStoreRestApi.exception.TokenExpiredException;
import com.scb.assignment.bookStoreRestApi.jsonObject.CommonResponse;
import com.scb.assignment.bookStoreRestApi.jsonObject.CreateUserRequest;
import com.scb.assignment.bookStoreRestApi.jsonObject.UserResponse;
import com.scb.assignment.bookStoreRestApi.model.User;
import com.scb.assignment.bookStoreRestApi.model.UserRepository;
import com.scb.assignment.bookStoreRestApi.utility.CryptographUtility;
import com.scb.assignment.bookStoreRestApi.utility.DateTimeUtility;
import com.scb.assignment.bookStoreRestApi.utility.InputValidationUtility;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<Object> getUser(@RequestHeader(value = "token") String token)
			throws InvalidTokenException, TokenExpiredException, InvalidParameterException {
		// validate input
		InputValidationUtility.validateToken(token);

		User user = userRepository.findByToken(token);
		InputValidationUtility.validateUser(user);
		userRepository.save(user);
		return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@RequestHeader(value = "token") String token)
			throws InvalidTokenException, TokenExpiredException, InvalidParameterException {
		// validate input
		InputValidationUtility.validateToken(token);

		User user = userRepository.findByToken(token);
		InputValidationUtility.validateUser(user);
		userRepository.delete(user);
		CommonResponse response = new CommonResponse(true, HttpStatus.OK, "success.");
		return new ResponseEntity<>(response, response.getStatus());
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest userReq)
			throws InternalErrorException, InvalidParameterException, DuplicateItemException {
		// validate input
		InputValidationUtility.validateCreatUserRequest(userReq);

		// check existing user
		if (userRepository.findByUsername(userReq.getUsername()) != null) {
			throw new DuplicateItemException("duplicate username.");
		}

		// save user
		try {
			int salt = CryptographUtility.randomInt();
			String base64ShaPwd = CryptographUtility.shaToBase64(userReq.getPassword(), salt);
			User user = new User(userReq.getUsername(), base64ShaPwd,
					DateTimeUtility.fromString(userReq.getDateOfBirth()));
			user.setSalt(salt);
			userRepository.save(user);
		} catch (NoSuchAlgorithmException e) {
			throw new InternalErrorException(e.getMessage());
		} catch (DateTimeParseException e) {
			throw new InvalidParameterException("invalid date of birth.");
		}
		CommonResponse response = new CommonResponse(true, HttpStatus.OK, "success.");
		return new ResponseEntity<>(response, response.getStatus());
	}
}
