package com.scb.assignment.bookStoreRestApi.jsonObject;

import java.util.List;

public class OrderRequest {
	private List<Long> orders;

	public OrderRequest() {
	}

	public OrderRequest(List<Long> orders) {
		super();
		this.orders = orders;
	}

	public List<Long> getOrders() {
		return orders;
	}

	public void setOrders(List<Long> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "OrderRequest [orders=" + orders + "]";
	}

}
