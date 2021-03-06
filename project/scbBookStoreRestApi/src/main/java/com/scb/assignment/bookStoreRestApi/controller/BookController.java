package com.scb.assignment.bookStoreRestApi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.scb.assignment.bookStoreRestApi.jsonObject.BookResponse;
import com.scb.assignment.bookStoreRestApi.jsonObject.ExternalBook;
import com.scb.assignment.bookStoreRestApi.model.Book;
import com.scb.assignment.bookStoreRestApi.model.BookRepository;

@RestController
public class BookController {
	@Autowired
	BookRepository bookRepository;

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ResponseEntity<Object> getBooks() {
		RestTemplate restTemplate = new RestTemplate();

		// get recommended book from publisher
		ResponseEntity<List<ExternalBook>> extRecommendedBooksResponse = restTemplate.exchange(
				"https://scb-test-book-publisher.herokuapp.com/books/recommendation", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ExternalBook>>() {
				});
		List<ExternalBook> extRecommendedBooks = extRecommendedBooksResponse.getBody();

		// get books from publisher
		ResponseEntity<List<ExternalBook>> extBooksResponse = restTemplate.exchange(
				"https://scb-test-book-publisher.herokuapp.com/books", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ExternalBook>>() {
				});
		List<ExternalBook> extBooks = extBooksResponse.getBody();

		extRecommendedBooks.sort((a, b) -> a.getBookName().compareTo(b.getBookName()));
		extBooks.sort((a, b) -> a.getBookName().compareTo(b.getBookName()));

		// merge all books
		List<BookResponse> rspBooks = new ArrayList<>();
		for (ExternalBook book : extRecommendedBooks) {
			rspBooks.add(new BookResponse(book, true));
		}
		for (ExternalBook book : extBooks) {
			BookResponse bookResp = new BookResponse(book, false);
			if (!rspBooks.contains(bookResp)) {
				rspBooks.add(bookResp);
			}
		}

		// exclude existing books in the database
		List<Book> existingBooks = (List<Book>) bookRepository.findAll();
		List<BookResponse> newBooks = new ArrayList<BookResponse>();
		newBooks.addAll(rspBooks);
		for (Book book : existingBooks) {
			BookResponse existingBook = new BookResponse(book);
			if (rspBooks.contains(existingBook)) {
				newBooks.remove(existingBook);
			}
		}

		// add new books into the database
		List<Book> saveBooks = new ArrayList<>();
		for (BookResponse book : newBooks) {
			saveBooks.add(new Book(book));
		}
		bookRepository.saveAll(saveBooks);
		return new ResponseEntity<>(rspBooks, HttpStatus.OK);
	}
}
