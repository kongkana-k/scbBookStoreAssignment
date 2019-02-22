package com.scb.assignment.bookStoreRestApi.jsonObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalBook {
	private Long id;
	@JsonProperty("book_name")
	private String bookName;
	@JsonProperty("author_name")
	private String authorName;
	private String price;

	public ExternalBook() {
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ExternalBook [id=" + id + ", bookName=" + bookName + ", authorName=" + authorName + ", price=" + price
				+ "]";
	}

}
