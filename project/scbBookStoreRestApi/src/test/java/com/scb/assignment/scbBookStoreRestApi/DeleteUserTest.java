package com.scb.assignment.scbBookStoreRestApi;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.assignment.bookStoreRestApi.ScbBookStoreRestApiApplication;
import com.scb.assignment.bookStoreRestApi.jsonObject.LoginResponse;
import com.scb.assignment.bookStoreRestApi.model.User;
import com.scb.assignment.bookStoreRestApi.model.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ScbBookStoreRestApiApplication.class)
public class DeleteUserTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void deleteUserFailInvalidToken() throws Exception {
		ResponseEntity<String> response = restTemplate.exchange(TestUtility.HOST_NAME + port + "/users",
				HttpMethod.DELETE, TestUtility.createEmptyWithTokenEntityRequest(""), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(response.getBody()).contains("invalid token");
	}

	@Test
	public void deleteUserFailTokenExpired() throws Exception {
		restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("deleteuser.tokenexpire", "thisismysecret", "15/01/1985"),
				String.class);
		ResponseEntity<String> loginRsp = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("deleteuser.tokenexpire", "thisismysecret"), String.class);
		LoginResponse loginResponse = objectMapper.readValue(loginRsp.getBody(), LoginResponse.class);
		User user = userRepository.findByToken(loginResponse.getToken());
		user.setTokenExpiredTime(Timestamp.valueOf(LocalDateTime.now()));
		userRepository.save(user);
		ResponseEntity<String> deleteUserRsp = restTemplate.exchange(TestUtility.HOST_NAME + port + "/users",
				HttpMethod.DELETE, TestUtility.createEmptyWithTokenEntityRequest(loginResponse.getToken()),
				String.class);
		Assertions.assertThat(deleteUserRsp.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		Assertions.assertThat(deleteUserRsp.getBody()).contains("token expired");
	}

	@Test
	public void deleteUserSuccess() throws Exception {
		restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("delete.user", "thisismysecret", "15/01/1985"), String.class);
		ResponseEntity<String> loginRsp = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("delete.user", "thisismysecret"), String.class);
		LoginResponse loginResponse = objectMapper.readValue(loginRsp.getBody(), LoginResponse.class);
		ResponseEntity<String> deleteUserRsp = restTemplate.exchange(TestUtility.HOST_NAME + port + "/users",
				HttpMethod.DELETE, TestUtility.createEmptyWithTokenEntityRequest(loginResponse.getToken()),
				String.class);
		Assertions.assertThat(deleteUserRsp.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(deleteUserRsp.getBody()).contains("success");
		User user = userRepository.findByUsername("delete.user");
		Assertions.assertThat(user).isNull();
	}

}
