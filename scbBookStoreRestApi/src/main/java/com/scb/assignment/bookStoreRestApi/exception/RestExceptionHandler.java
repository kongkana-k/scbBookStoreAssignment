package com.scb.assignment.bookStoreRestApi.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.scb.assignment.bookStoreRestApi.jsonObject.CommonResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ItemNotFoundException.class)
	protected ResponseEntity<Object> handleItemNotFound(ItemNotFoundException ex) {
		CommonResponse response = new CommonResponse(false, HttpStatus.NOT_FOUND,
				ex.getClass().getSimpleName() + ": " + ex.getMessage());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(InvalidTokenException.class)
	protected ResponseEntity<Object> handleInvalidToken(InvalidTokenException ex) {
		CommonResponse response = new CommonResponse(false, HttpStatus.UNAUTHORIZED,
				ex.getClass().getSimpleName() + ": " + ex.getMessage());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(TokenExpiredException.class)
	protected ResponseEntity<Object> handleTokenExpired(TokenExpiredException ex) {
		CommonResponse response = new CommonResponse(false, HttpStatus.UNAUTHORIZED,
				ex.getClass().getSimpleName() + ": " + ex.getMessage());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(InvalidParameterException.class)
	protected ResponseEntity<Object> handleInvalidParameter(InvalidParameterException ex) {
		CommonResponse response = new CommonResponse(false, HttpStatus.BAD_REQUEST,
				ex.getClass().getSimpleName() + ": " + ex.getMessage());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(InternalErrorException.class)
	protected ResponseEntity<Object> handleInternalError(InternalErrorException ex) {
		CommonResponse response = new CommonResponse(false, HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getClass().getSimpleName() + ": " + ex.getMessage());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<Object> handleAuthentication(AuthenticationException ex) {
		CommonResponse response = new CommonResponse(false, HttpStatus.UNAUTHORIZED,
				ex.getClass().getSimpleName() + ": " + ex.getMessage());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(DuplicateItemException.class)
	protected ResponseEntity<Object> handleDuplicateItem(DuplicateItemException ex) {
		CommonResponse response = new CommonResponse(false, HttpStatus.CONFLICT,
				ex.getClass().getSimpleName() + ": " + ex.getMessage());
		return new ResponseEntity<>(response, response.getStatus());
	}
}
