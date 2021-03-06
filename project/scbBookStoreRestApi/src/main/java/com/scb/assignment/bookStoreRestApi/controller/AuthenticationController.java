package com.scb.assignment.bookStoreRestApi.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scb.assignment.bookStoreRestApi.exception.AuthenticationException;
import com.scb.assignment.bookStoreRestApi.exception.InternalErrorException;
import com.scb.assignment.bookStoreRestApi.exception.InvalidParameterException;
import com.scb.assignment.bookStoreRestApi.jsonObject.LoginRequest;
import com.scb.assignment.bookStoreRestApi.jsonObject.LoginResponse;
import com.scb.assignment.bookStoreRestApi.model.User;
import com.scb.assignment.bookStoreRestApi.model.UserRepository;
import com.scb.assignment.bookStoreRestApi.utility.CryptographUtility;
import com.scb.assignment.bookStoreRestApi.utility.InputValidationUtility;

@RestController
public class AuthenticationController {
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody LoginRequest loginReq)
			throws AuthenticationException, InternalErrorException, InvalidParameterException {
		// validate input
		InputValidationUtility.validateLoginRequest(loginReq);

		User user = userRepository.findByUsername(loginReq.getUsername());
		if (user == null) {
			throw new AuthenticationException("invalid username or password.");
		}
		try {
			String base64ShaPwd = CryptographUtility.shaToBase64(loginReq.getPassword(), user.getSalt());
			if (!user.getPassword().equals(base64ShaPwd)) {
				throw new AuthenticationException("invalid username or password.");
			}
		} catch (NoSuchAlgorithmException e) {
			throw new InternalErrorException(e.getMessage());
		}
		int randomKey = CryptographUtility.randomInt();
		String token;
		try {
			token = CryptographUtility.shaToBase64(user.getUsername(), randomKey);
		} catch (NoSuchAlgorithmException e) {
			throw new InternalErrorException(e.getMessage());
		}
		user.setRandomKey(randomKey);
		user.setToken(token);
		user.setTokenExpiredTime(CryptographUtility.getNextExpiredTime());
		userRepository.save(user);
		return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
	}
}
