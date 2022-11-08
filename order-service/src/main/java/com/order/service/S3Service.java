package com.order.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.order.entity.Order;
import com.order.entity.OrderDetails;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3Service {

	private Context context;
	private S3Client s3Client;

	public S3Service(S3Client s3Client, Context context) {
		this.context = context;
		this.s3Client = s3Client;
	}

//file uploade
	public Order saveObjToS3(OrderDetails orderDetails) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String fileName = orderDetails.getDealerId() + "_" + orderDetails.getCustomerId() + "_"
				+ orderDetails.getOrderId();

		PutObjectRequest objectRequest = PutObjectRequest.builder().bucket("dealer-orders").key(fileName).build();

		s3Client.putObject(objectRequest, RequestBody.fromString(gson.toJson(orderDetails)));
		Order order = new Order(orderDetails.getDealerId(),
				orderDetails.getCustomerId() + "#" + orderDetails.getOrderId(), "dealer-orders", fileName,
				orderDetails.getOrderStatus());
		return order;
	}
}
