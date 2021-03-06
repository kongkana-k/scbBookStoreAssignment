package com.scb.assignment.scbBookStoreRestApi;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Arrays;

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
import com.scb.assignment.bookStoreRestApi.model.Book;
import com.scb.assignment.bookStoreRestApi.model.BookRepository;
import com.scb.assignment.bookStoreRestApi.model.User;
import com.scb.assignment.bookStoreRestApi.model.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ScbBookStoreRestApiApplication.class)
public class CreateOrderTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void createOrderFailInvalidToken() throws Exception {
		ResponseEntity<String> response = restTemplate.exchange(TestUtility.HOST_NAME + port + "/users/orders",
				HttpMethod.POST,
				TestUtility.createOrderWithTokenEntityRequest("", Arrays.asList(new Long(1), new Long(2))),
				String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(response.getBody()).contains("invalid token");
	}

	@Test
	public void createOrderFailTokenExpired() throws Exception {
		restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("createorder.tokenexpire", "thisismysecret", "15/01/1985"),
				String.class);
		ResponseEntity<String> loginRsp = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("createorder.tokenexpire", "thisismysecret"), String.class);
		LoginResponse loginResponse = objectMapper.readValue(loginRsp.getBody(), LoginResponse.class);
		User user = userRepository.findByToken(loginResponse.getToken());
		user.setTokenExpiredTime(Timestamp.valueOf(LocalDateTime.now()));
		userRepository.save(user);
		ResponseEntity<String> createOrderRsp = restTemplate.exchange(TestUtility.HOST_NAME + port + "/users/orders",
				HttpMethod.POST, TestUtility.createOrderWithTokenEntityRequest(loginResponse.getToken(),
						Arrays.asList(new Long(1), new Long(2))),
				String.class);
		Assertions.assertThat(createOrderRsp.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		Assertions.assertThat(createOrderRsp.getBody()).contains("token expired");
	}

	@Test
	public void createOrderFailInvalidOrders() throws Exception {
		restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("createorder.invalidorders", "thisismysecret", "15/01/1985"),
				String.class);
		ResponseEntity<String> loginRsp = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("createorder.invalidorders", "thisismysecret"), String.class);
		LoginResponse loginResponse = objectMapper.readValue(loginRsp.getBody(), LoginResponse.class);
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users/orders",
				TestUtility.createOrderWithTokenEntityRequest(loginResponse.getToken(), Arrays.asList()), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(response.getBody()).contains("invalid orders");
	}

	@Test
	public void createOrderFailInvalidBook() throws Exception {
		restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("createorder.invalidbook", "thisismysecret", "15/01/1985"),
				String.class);
		ResponseEntity<String> loginRsp = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("createorder.invalidbook", "thisismysecret"), String.class);
		LoginResponse loginResponse = objectMapper.readValue(loginRsp.getBody(), LoginResponse.class);
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users/orders",
				TestUtility.createOrderWithTokenEntityRequest(loginResponse.getToken(), Arrays.asList(new Long(999))),
				String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		Assertions.assertThat(response.getBody()).contains("invalid book");
	}

	@Test
	public void createOrderSuccess() throws Exception {
		restTemplate.getForEntity(TestUtility.HOST_NAME + port + "/books", String.class);
		restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users",
				TestUtility.createUserEntityRequest("createorder.success", "thisismysecret", "15/01/1985"),
				String.class);
		ResponseEntity<String> loginRsp = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/login",
				TestUtility.createLoginEntityRequest("createorder.success", "thisismysecret"), String.class);
		LoginResponse loginResponse = objectMapper.readValue(loginRsp.getBody(), LoginResponse.class);
		ResponseEntity<String> response = restTemplate.postForEntity(TestUtility.HOST_NAME + port + "/users/orders",
				TestUtility.createOrderWithTokenEntityRequest(loginResponse.getToken(),
						Arrays.asList(new Long(1), new Long(4))),
				String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Book book1 = bookRepository.findById(1L).get();
		Book book4 = bookRepository.findById(4L).get();
		int price = book1.getPrice() + book4.getPrice();
		String priceStr = (price / 100) + "." + new DecimalFormat("00").format(price % 100);
		Assertions.assertThat(response.getBody()).contains(priceStr);
	}
}
