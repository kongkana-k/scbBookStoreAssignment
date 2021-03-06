package com.scb.assignment.scbBookStoreRestApi;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.assignment.bookStoreRestApi.jsonObject.CreateUserRequest;
import com.scb.assignment.bookStoreRestApi.jsonObject.LoginRequest;
import com.scb.assignment.bookStoreRestApi.jsonObject.OrderRequest;

public class TestUtility {
	public static final String HOST_NAME = "http://localhost:";

	public static HttpEntity<String> createLoginEntityRequest(String username, String password)
			throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		String requestJson = objectMapper.writeValueAsString(new LoginRequest(username, password));
		return new HttpEntity<String>(requestJson, headers);
	}

	public static HttpEntity<String> createUserEntityRequest(String username, String password, String dateOfBirth)
			throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		String requestJson = objectMapper.writeValueAsString(new CreateUserRequest(username, password, dateOfBirth));
		return new HttpEntity<String>(requestJson, headers);
	}

	public static HttpEntity<String> createEmptyWithTokenEntityRequest(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("token", token);
		return new HttpEntity<String>("", headers);
	}

	public static HttpEntity<String> createOrderWithTokenEntityRequest(String token, List<Long> orders)
			throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("token", token);
		ObjectMapper objectMapper = new ObjectMapper();
		String requestJson = objectMapper.writeValueAsString(new OrderRequest(orders));
		return new HttpEntity<String>(requestJson, headers);
	}
}
