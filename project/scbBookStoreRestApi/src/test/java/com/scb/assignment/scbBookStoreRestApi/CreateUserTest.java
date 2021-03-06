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

import com.scb.assignment.bookStoreRestApi.ScbBookStoreRestApiApplication;
import com.scb.assignment.bookStoreRestApi.model.UserRepository;

//@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ScbBookStoreRestApiApplication.class)
public class CreateUserTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void createUserFailInvalidUsername() throws Exception {
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("", "thisismysecret", "15/01/1985"), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(response.getBody()).contains("invalid username");
	}

	@Test
	public void createUserFailInvalidPassword() throws Exception {
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("john.doe", "", "15/01/1985"), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(response.getBody()).contains("invalid password");
	}

	@Test
	public void createUserFailInvalidDateOfBirth() throws Exception {
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("john.doe", "thisismysecret", ""), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(response.getBody()).contains("invalid date of birth");
	}

	@Test
	public void createUserFailDuplicateUser() throws Exception {
		restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("duplicate.user", "thisismysecret", "15/01/1985"), String.class);
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("duplicate.user", "thisismysecret", "15/01/1985"), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		Assertions.assertThat(response.getBody()).contains("duplicate username");
	}

	@Test
	public void createUserSuccess() throws Exception {
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("john.doe", "thisismysecret", "15/01/1985"), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).contains("success");
		Assertions.assertThat(userRepository.findByUsername("john.doe")).isNotNull();
	}
}
