package com.order.entity;



import java.util.Date;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Order {

	private String dealerId;
	private String customerIdAndOrderId;
	private String bucketName;
	private String objectKey;
	private String orderStatus;
	private Date expectedDeliveryDate;
	private double price;
	
	

	public Order(String dealerId, String customerIdAndOrderId, String bucketName, String objectKey, String orderStatus,
			Date expectedDeliveryDate, double price) {
		super();
		this.dealerId = dealerId;
		this.customerIdAndOrderId = customerIdAndOrderId;
		this.bucketName = bucketName;
		this.objectKey = objectKey;
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

	@DynamoDbPartitionKey
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	@DynamoDbSortKey
	public String getCustomerIdAndOrderId() {
		return customerIdAndOrderId;
	}

	public void setCustomerIdAndOrderId(String customerIdAndOrderId) {
		this.customerIdAndOrderId = customerIdAndOrderId;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Order(String dealerId, String customerIdAndOrderId, String bucketName, String objectKey,
			String orderStatus) {
		super();
		this.dealerId = dealerId;
		this.customerIdAndOrderId = customerIdAndOrderId;
		this.bucketName = bucketName;
		this.objectKey = objectKey;
		this.orderStatus = orderStatus;
	}

	
}
