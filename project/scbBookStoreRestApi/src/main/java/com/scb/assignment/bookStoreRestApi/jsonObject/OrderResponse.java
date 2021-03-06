package com.scb.assignment.bookStoreRestApi.jsonObject;

import java.text.DecimalFormat;

public class OrderResponse {
	private String price;

	public OrderResponse() {
	}

	public OrderResponse(String price) {
		super();
		this.price = price;
	}

	public OrderResponse(Integer price) {
		super();
		this.price = (price / 100) + "." + new DecimalFormat("00").format(price % 100);
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderResponse [price=" + price + "]";
	}
}
