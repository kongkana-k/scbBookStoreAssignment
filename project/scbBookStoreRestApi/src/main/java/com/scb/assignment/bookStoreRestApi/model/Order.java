package com.scb.assignment.bookStoreRestApi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	@Column(nullable = false)
	private Integer totalPrice;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	public Order() {
		this.id = 0L;
	}

	public Order(Integer totalPrice, User user, List<OrderItem> orderItems) {
		super();
		this.id = 0L;
		this.totalPrice = totalPrice;
		this.user = user;
		this.orderItems = orderItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public void addOrderItem(OrderItem orderItem) {
		this.orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", totalPrice=" + totalPrice + ", user=" + user + ", orderItems=" + orderItems + "]";
	}

}
