package com.order.entity;

public class OrderRequest {

	private String dealerId;
	private String orderId;
	private String customerId;
	private String objectKey;
	private boolean isshowAll;
	
	
	
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
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
	public String getObjectKey() {
		return objectKey;
	}
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}
	public boolean isIsshowAll() {
		return isshowAll;
	}
	public void setIsshowAll(boolean isshowAll) {
		this.isshowAll = isshowAll;
	}
	
	
	
}
