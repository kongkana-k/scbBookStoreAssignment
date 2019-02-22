package com.scb.assignment.scbBookStoreRestApi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.scb.assignment.bookStoreRestApi.ScbBookStoreRestApiApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ScbBookStoreRestApiApplication.class)
public class ScbBookStoreRestApiApplicationTests {

	@Test
	public void contextLoads() throws Exception {
	}

}
