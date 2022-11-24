package com.order.entity;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;
import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primarySortKey;

import java.util.Date;

import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.ListAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.StringAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;

@DynamoDbBean
public class Order {

	private String dealerId;
	private String customerIdAndOrderId;
	private String bucketName;
	private String objectKey;
	private String orderStatus;
	// @DynamoDBTyped(DynamoDBAttributeType.S)
//	private Date expectedDeliveryDate;
//	private double price;

	public static final StaticTableSchema.Builder<Order> TABLE_SCHEMA = StaticTableSchema.builder(Order.class)
			.newItemSupplier(Order::new).addAttribute(String.class, a -> a.name("dealerId").getter(Order::getDealerId)
					.setter(Order::setDealerId).tags(primaryPartitionKey()));
//			.addAttribute(String.class, a -> a.name("customerId_OrderId").getter(Order::getCustomerIdAndOrderId)
//					.setter(Order::setCustomerIdAndOrderId).tags(primarySortKey()));

//	public Order(String dealerId, String customerIdAndOrderId, String bucketName, String objectKey, String orderStatus,
//			Date expectedDeliveryDate, double price) {
//		super();
//		this.dealerId = dealerId;
//		this.customerIdAndOrderId = customerIdAndOrderId;
//		this.bucketName = bucketName;
//		this.objectKey = objectKey;
//		this.orderStatus = orderStatus;
//		this.expectedDeliveryDate = expectedDeliveryDate;
//		this.price = price;
//	}
	// @DynamoDBTyped(DynamoDBAttributeType.S)
//	 @DynamoDb
//	public Date getExpectedDeliveryDate() {
//		return expectedDeliveryDate;
//	}
//
//	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
//		this.expectedDeliveryDate = expectedDeliveryDate;
//	}
//
//	public double getPrice() {
//		return price;
//	}
//
//	public void setPrice(double price) {
//		this.price = price;
//	}

	@DynamoDbPartitionKey
	// @DynamoDbAttribute("dealerId")
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	//@DynamoDbSortKey
	//@DynamoDbAttribute("customerId_OrderId")
	public String getCustomerIdAndOrderId() {
		return customerIdAndOrderId;
	}

	public void setCustomerIdAndOrderId(String customerIdAndOrderId) {
		this.customerIdAndOrderId = customerIdAndOrderId;
	}

//	@DynamoDbAttribute("bucketName")
	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

//	@DynamoDbAttribute("objectKey")
	public String getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

//	@DynamoDbAttribute("orderStatus")
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

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Order [dealerId=" + dealerId + ", customerIdAndOrderId=" + customerIdAndOrderId + ", bucketName="
				+ bucketName + ", objectKey=" + objectKey + ", orderStatus=" + orderStatus + "]";
	}

}
