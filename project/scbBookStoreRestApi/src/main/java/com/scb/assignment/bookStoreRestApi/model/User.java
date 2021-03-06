package com.scb.assignment.bookStoreRestApi.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	@Column(nullable = false)
	private String username;
	private String name;
	private String surname;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private int salt;
	private Date dateOfBirth;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<Order>();
	private String token;
	private Integer randomKey;
	private Timestamp tokenExpiredTime;

	protected User() {
	}

	public User(String username, String password, Date dateOfBirth) {
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		int index = username.indexOf(".");
		if (index > 0) {
			name = username.substring(0, index);
			surname = username.substring(index + 1);
		} else {
			name = username;
			surname = "";
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSalt() {
		return salt;
	}

	public void setSalt(int salt) {
		this.salt = salt;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getRandomKey() {
		return randomKey;
	}

	public void setRandomKey(Integer randomKey) {
		this.randomKey = randomKey;
	}

	public Timestamp getTokenExpiredTime() {
		return tokenExpiredTime;
	}

	public void setTokenExpiredTime(Timestamp tokenExpiredTime) {
		this.tokenExpiredTime = tokenExpiredTime;
	}

	public void addOrder(Order order) {
		this.orders.add(order);
		order.setUser(this);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", name=" + name + ", surname=" + surname + ", password="
				+ password + ", salt=" + salt + ", dateOfBirth=" + dateOfBirth + ", orders=" + orders + ", token="
				+ token + ", randomKey=" + randomKey + ", tokenExpiredTime=" + tokenExpiredTime + "]";
	}

}
