package com.order.entity;

import java.util.Date;

public class OrderDetails {

	private String orderId;
	private String customerId;
	private String dealerId;
	private String model;
	private String make;
	private String year;
	private String fuelType;
	private String customerName;
	private String city;
	private String orderStatus;
	private Date expectedDeliveryDate;
	private double price;

	public OrderDetails(String orderId, String customerId, String dealerId, String model, String make, String year,
			String fuelType, String customerName, String city, String orderStatus, Date expectedDeliveryDate,
			double price) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.dealerId = dealerId;
		this.model = model;
		this.make = make;
		this.year = year;
		this.fuelType = fuelType;
		this.customerName = customerName;
		this.city = city;
		this.orderStatus = orderStatus;
		this.expectedDeliveryDate = expectedDeliveryDate;
		this.price = price;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public OrderDetails(String orderId, String customerId, String dealerId, String model, String make, String year,
			String fuelType, String customerName, String city, String orderStatus) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.dealerId = dealerId;
		this.model = model;
		this.make = make;
		this.year = year;
		this.fuelType = fuelType;
		this.customerName = customerName;
		this.city = city;
		this.orderStatus = orderStatus;
	}

	public OrderDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
