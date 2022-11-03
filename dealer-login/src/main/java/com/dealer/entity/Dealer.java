package com.dealer.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Dealer {

	private Long id;
	private String username;
	private String password;
	private String message;
	
	
	
	 
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	 @DynamoDbPartitionKey
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Dealer(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public Dealer() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
