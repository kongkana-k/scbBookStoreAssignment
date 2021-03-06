package com.scb.assignment.scbBookStoreRestApi;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.assignment.bookStoreRestApi.ScbBookStoreRestApiApplication;
import com.scb.assignment.bookStoreRestApi.jsonObject.LoginResponse;
import com.scb.assignment.bookStoreRestApi.model.UserRepository;

//@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ScbBookStoreRestApiApplication.class)
public class AuthenticationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void AuthenticationFailInvalidUsername() throws Exception {
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("", "thisismysecret"), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(response.getBody()).contains("invalid username");
	}

	@Test
	public void AuthenticationFailInvalidPassword() throws Exception {
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("john.doe", ""), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(response.getBody()).contains("invalid password");
	}

	@Test
	public void AuthenticationFailUnknownUsername() throws Exception {
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("abcd", "abcd"), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		Assertions.assertThat(response.getBody()).contains("invalid username or password");
	}

	@Test
	public void AuthenticationFailWrongPassword() throws Exception {
		restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("wrong.user", "secret", "01/01/1999"), String.class);
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("wrong.user", "abcd"), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		Assertions.assertThat(response.getBody()).contains("invalid username or password");
	}

	@Test
	public void AuthenticationSuccess() throws Exception {
		restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("success.user", "secret", "01/01/1999"), String.class);
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("success.user", "secret"), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).contains("token");
		LoginResponse loginResponse = objectMapper.readValue(response.getBody(), LoginResponse.class);
		Assertions.assertThat(userRepository.findByToken(loginResponse.getToken())).isNotNull();
	}

}
