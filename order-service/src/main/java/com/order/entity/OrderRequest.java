package com.order.entity;

public class OrderRequest {

	private String dealerId;
	private String orderId;
	private String customerId;
	private String objectKey;
	private boolean showAll;
	
	
	
	public OrderRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderRequest(String dealerId, String orderId, String customerId, String objectKey, boolean showAll) {
		super();
		this.dealerId = dealerId;
		this.orderId = orderId;
		this.customerId = customerId;
		this.objectKey = objectKey;
		this.showAll = showAll;
	}
	public boolean isShowAll() {
		return showAll;
	}
	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}
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
	
	
	
}
