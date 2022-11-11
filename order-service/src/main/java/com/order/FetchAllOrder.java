package com.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.order.entity.Order;
import com.order.entity.OrderDetails;
import com.order.entity.OrderRequest;
import com.order.service.OrderDbService;
import com.order.service.S3Service;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

public class FetchAllOrder implements RequestHandler<OrderRequest, List<Order>> {

	private final DynamoDbClient dynamoDbClient;
	private final S3Client s3Client;
	

	public FetchAllOrder() {
		dynamoDbClient = DependencyFactory.dynamoDbClient();
		s3Client = DependencyFactory.s3Client();
	}

	@Override
	public List<Order> handleRequest(OrderRequest input, Context context) {

		OrderDbService dbService = new OrderDbService(dynamoDbClient, context);
		S3Service s3Service = new S3Service(s3Client, context);
		List<Order> fetchAllorder;
		List<Order> list = new ArrayList<>();
		if (input.isShowAll()) {
			fetchAllorder = dbService.fetchAllorder(input.getDealerId());
		} else {
			fetchAllorder = dbService.fetchAllorder(input.getDealerId(), input.getCustomerId(), input.getOrderId());
		}
		fetchAllorder.forEach((order) -> {
			try {

				Order fetchOrderfromS3 = s3Service.fetchOrderfromS3(order.getBucketName(), order.getObjectKey());
				list.add(fetchOrderfromS3);
			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return list;
	}

}
