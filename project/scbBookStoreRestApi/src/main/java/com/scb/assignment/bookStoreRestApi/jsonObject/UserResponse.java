package com.scb.assignment.bookStoreRestApi.jsonObject;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scb.assignment.bookStoreRestApi.model.Order;
import com.scb.assignment.bookStoreRestApi.model.OrderItem;
import com.scb.assignment.bookStoreRestApi.model.User;
import com.scb.assignment.bookStoreRestApi.utility.DateTimeUtility;

public class UserResponse {

	private String name;
	private String surname;
	@JsonProperty("date_of_birth")
	private String dateOfBirth;
	private List<Long> books = new ArrayList<>();

	public UserResponse() {
	}

	public UserResponse(String name, String surname, String dateOfBirth, List<Long> books) {
		super();
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.books = books;
	}

	public UserResponse(User user) {
		super();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.dateOfBirth = DateTimeUtility.toString(user.getDateOfBirth());
		List<Long> orderedBook = new ArrayList<>();
		for (Order order : user.getOrders()) {
			for (OrderItem orderItem : order.getOrderItems()) {
				orderedBook.add(orderItem.getBook().getId());
			}
		}
		this.books = orderedBook;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Long> getBooks() {
		return books;
	}

	public void setBooks(List<Long> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "UserResponse [name=" + name + ", surname=" + surname + ", dateOfBirth=" + dateOfBirth + ", books="
				+ books + "]";
	}

}
