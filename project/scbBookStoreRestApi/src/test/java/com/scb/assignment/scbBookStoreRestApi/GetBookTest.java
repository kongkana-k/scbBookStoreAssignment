package com.scb.assignment.scbBookStoreRestApi;

import java.util.List;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.assignment.bookStoreRestApi.ScbBookStoreRestApiApplication;
import com.scb.assignment.bookStoreRestApi.jsonObject.BookResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ScbBookStoreRestApiApplication.class)
public class GetBookTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getBookSuccess() throws Exception {
		ResponseEntity<String> getBooksRsp = restTemplate.getForEntity(TestUtility.HOST_NAME + port + "/books",
				String.class);
		Assertions.assertThat(getBooksRsp.getStatusCode()).isEqualTo(HttpStatus.OK);
		List<BookResponse> bookResponses = objectMapper.readValue(getBooksRsp.getBody(),
				new TypeReference<List<BookResponse>>() {
				});
		BookResponse lastBook = null;
		boolean orderByName = true;
		for (BookResponse book : bookResponses) {
			if (lastBook != null) {
				if (lastBook.getIsRecommended() == book.getIsRecommended()
						&& lastBook.getName().compareTo(book.getName()) >= 0) {
					orderByName = false;
					break;
				}
			}
			lastBook = book;
		}
		Assertions.assertThat(orderByName).isTrue();
	}

}
