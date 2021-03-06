package com.scb.assignment.bookStoreRestApi.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scb.assignment.bookStoreRestApi.jsonObject.BookResponse;
import com.scb.assignment.bookStoreRestApi.jsonObject.ExternalBook;

@Entity
@Table(name = "Books")
public class Book {
	@Id
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	private String bookName;
	private String authorName;
	private Integer price;
//	@OneToMany(mappedBy = "book")
//	private List<OrderItem> orderItems = new ArrayList<>();

	protected Book() {
	}

	public Book(Long id, String bookName, String authorName, Integer price) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.authorName = authorName;
		this.price = price;
	}

	public Book(ExternalBook extBook) {
		super();
		this.id = extBook.getId();
		this.bookName = extBook.getBookName();
		this.authorName = extBook.getAuthorName();
		this.price = (int) (Double.parseDouble(extBook.getPrice()) * 100);
	}

	public Book(BookResponse book) {
		super();
		this.id = book.getId();
		this.bookName = book.getName();
		this.authorName = book.getAuthor();
		this.price = (int) (Double.parseDouble(book.getPrice()) * 100);
	}

//	public List<OrderItem> getOrderItems() {
//		return orderItems;
//	}
//
//	public void setOrderItems(List<OrderItem> orderItems) {
//		this.orderItems = orderItems;
//	}

	public Book(Long id, String bookName, String authorName, Integer price, List<OrderItem> orderItems) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.authorName = authorName;
		this.price = price;
//		this.orderItems = orderItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", bookName=" + bookName + ", authorName=" + authorName + ", price=" + price
				+ /* ", orderItems=" + orderItems + */ "]";
	}

}
