package com.scb.assignment.bookStoreRestApi.jsonObject;

import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scb.assignment.bookStoreRestApi.model.Book;

public class BookResponse {
	private Long id;
	private String name;
	private String author;
	private String price;
	@JsonProperty("is_recommended")
	private Boolean isRecommended;

	public BookResponse() {
	}

	public BookResponse(Long id, String name, String author, String price, Boolean isRecommended) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
		this.isRecommended = isRecommended;
	}

	public BookResponse(ExternalBook extBook, Boolean isRecommended) {
		super();
		this.id = extBook.getId();
		this.name = extBook.getBookName();
		this.author = extBook.getAuthorName();
		this.price = new DecimalFormat("#.00").format(Double.valueOf(extBook.getPrice()));
		this.isRecommended = isRecommended;
	}

	public BookResponse(Book book) {
		super();
		this.id = book.getId();
		this.name = book.getBookName();
		this.author = book.getAuthorName();
		this.price = new DecimalFormat("#.00").format(book.getPrice() / 100.0);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Boolean getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(Boolean isRecommended) {
		this.isRecommended = isRecommended;
	}

	@Override
	public String toString() {
		return "BookResponse [id=" + id + ", name=" + name + ", author=" + author + ", price=" + price
				+ ", isRecommended=" + isRecommended + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookResponse other = (BookResponse) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
