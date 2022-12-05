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

public class FetchAllOrder implements RequestHandler<OrderRequest, List<OrderDetails>> {

	private final DynamoDbClient dynamoDbClient;
	private final S3Client s3Client;

	public FetchAllOrder() {
		dynamoDbClient = DependencyFactory.dynamoDbClient();
		s3Client = DependencyFactory.s3Client();
	}

	@Override
	public List<OrderDetails> handleRequest(OrderRequest input, Context context) {

		OrderDbService dbService = new OrderDbService(dynamoDbClient, context);
		S3Service s3Service = new S3Service(s3Client, context);
		context.getLogger().log("fetching order =:" + input.toString());
		List<Order> fetchAllorder;
		List<OrderDetails> orderdetail = new ArrayList<>();
		try {
			if(input.isShowAll()) {
				fetchAllorder=dbService.fetchAllorder(input.getDealerId());
			}else {
			fetchAllorder = dbService.fetchAllorder(input.getDealerId(), input.getCustomerId(), input.getOrderId());
			}
			//System.out.println("ALL ORDER =" + fetchAllorder.toString());
			for (Order order : fetchAllorder) {

				OrderDetails fetchOrderfromS3 = s3Service.fetchOrderfromS3(order.getBucketName(), order.getObjectKey());
			//	System.out.println("fetchOrderfromS3 = " + fetchOrderfromS3.toString());
				orderdetail.add(fetchOrderfromS3);

			}
			System.out.println("ALL LIST ="+orderdetail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("exception occour =" + e.getMessage());
		}
		return orderdetail;
	}
}
