package com.order;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.order.entity.OrderDetails;
import com.order.service.OrderDbService;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class OrderProcess {

	private final DynamoDbClient dynamoDbClient;
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public OrderProcess() {
		dynamoDbClient = DependencyFactory.dynamoDbClient();
	}

//	public void handleRequest(OrderDetails orderDetails, Context context) {
//		OrderDbService dbService = new OrderDbService(dynamoDbClient, context);
//		dbService.processOrder(orderDetails);
//	}
}
