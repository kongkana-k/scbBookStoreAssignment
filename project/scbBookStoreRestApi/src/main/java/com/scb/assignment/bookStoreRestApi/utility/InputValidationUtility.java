package com.scb.assignment.bookStoreRestApi.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.scb.assignment.bookStoreRestApi.exception.InvalidParameterException;
import com.scb.assignment.bookStoreRestApi.exception.InvalidTokenException;
import com.scb.assignment.bookStoreRestApi.exception.TokenExpiredException;
import com.scb.assignment.bookStoreRestApi.jsonObject.CreateUserRequest;
import com.scb.assignment.bookStoreRestApi.jsonObject.LoginRequest;
import com.scb.assignment.bookStoreRestApi.model.User;

public class InputValidationUtility {
	public static void validateUser(User user) throws InvalidTokenException, TokenExpiredException {
		if (user == null) {
			throw new InvalidTokenException("invalid token.");
		}
		if (user.getTokenExpiredTime().before(Timestamp.valueOf(LocalDateTime.now()))) {
			throw new TokenExpiredException("token expired.");
		}
		user.setTokenExpiredTime(CryptographUtility.getNextExpiredTime());
	}

	public static void validateToken(String token) throws InvalidParameterException {
		if (token == null || token.equals("")) {
			throw new InvalidParameterException("invalid token.");
		}
	}

	public static void validateCreatUserRequest(CreateUserRequest req) throws InvalidParameterException {
		if (req.getUsername() == null || req.getUsername().equals("")) {
			throw new InvalidParameterException("invalid username.");
		}
		if (req.getPassword() == null || req.getPassword().equals("")) {
			throw new InvalidParameterException("invalid password.");
		}
		if (req.getDateOfBirth() == null || req.getDateOfBirth().equals("")) {
			throw new InvalidParameterException("invalid date of birth.");
		}
	}

	public static void validateLoginRequest(LoginRequest req) throws InvalidParameterException {
		if (req.getUsername() == null || req.getUsername().equals("")) {
			throw new InvalidParameterException("invalid username.");
		}
		if (req.getPassword() == null || req.getPassword().equals("")) {
			throw new InvalidParameterException("invalid password.");
		}
	}
}
