package com.scb.assignment.bookStoreRestApi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scb.assignment.bookStoreRestApi.exception.InvalidParameterException;
import com.scb.assignment.bookStoreRestApi.exception.InvalidTokenException;
import com.scb.assignment.bookStoreRestApi.exception.ItemNotFoundException;
import com.scb.assignment.bookStoreRestApi.exception.TokenExpiredException;
import com.scb.assignment.bookStoreRestApi.jsonObject.OrderRequest;
import com.scb.assignment.bookStoreRestApi.jsonObject.OrderResponse;
import com.scb.assignment.bookStoreRestApi.model.Book;
import com.scb.assignment.bookStoreRestApi.model.BookRepository;
import com.scb.assignment.bookStoreRestApi.model.Order;
import com.scb.assignment.bookStoreRestApi.model.OrderItem;
import com.scb.assignment.bookStoreRestApi.model.User;
import com.scb.assignment.bookStoreRestApi.model.UserRepository;
import com.scb.assignment.bookStoreRestApi.utility.InputValidationUtility;

@RestController
public class OrderController {
	@Autowired
	UserRepository userRepository;

	@Autowired
	BookRepository bookRepository;

	@RequestMapping(value = "/users/orders", method = RequestMethod.POST)
	public ResponseEntity<Object> createOrder(@RequestHeader("token") String token, @RequestBody OrderRequest orderReq)
			throws InvalidTokenException, TokenExpiredException, ItemNotFoundException, InvalidParameterException {
		// validate input
		InputValidationUtility.validateToken(token);
		if (orderReq.getOrders().size() <= 0) {
			throw new InvalidParameterException("invalid orders.");
		}

		User user = userRepository.findByToken(token);
		InputValidationUtility.validateUser(user);
		Integer totalPrice = 0;
		Order order = new Order();
		for (Long bookId : orderReq.getOrders()) {
			Optional<Book> book = bookRepository.findById(bookId);
			if (book.isPresent()) {
				order.addOrderItem(new OrderItem(1, book.get()));
				totalPrice += book.get().getPrice();
			} else {
				throw new ItemNotFoundException("invalid book: " + bookId + ".");
			}
		}
		order.setTotalPrice(totalPrice);
		user.addOrder(order);
		userRepository.save(user);
		return new ResponseEntity<>(new OrderResponse(totalPrice), HttpStatus.OK);
	}
}
