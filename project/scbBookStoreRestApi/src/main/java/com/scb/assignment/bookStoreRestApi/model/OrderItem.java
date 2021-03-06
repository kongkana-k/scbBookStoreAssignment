package com.scb.assignment.bookStoreRestApi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OrderItems")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	private Integer amount;
	@ManyToOne
	@JoinColumn(name = "order_id", nullable = true)
	private Order order;
	@ManyToOne
	@JoinColumn(name = "book_id", nullable = true)
	private Book book;

	public OrderItem() {
	}

	public OrderItem(Integer amount, Book book) {
		super();
		this.id = 0L;
		this.amount = amount;
		this.book = book;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", amount=" + amount + ", order=" + order + ", book=" + book + "]";
	}

}
