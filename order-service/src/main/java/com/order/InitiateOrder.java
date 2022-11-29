package com.order;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.order.entity.Order;
import com.order.service.OrderDbService;
import com.order.service.SQSService;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.sqs.SqsClient;

public class InitiateOrder {

	private final DynamoDbClient dynamoDbClient;
	private final SqsClient sqsClient;

	public InitiateOrder() {
		dynamoDbClient = DependencyFactory.dynamoDbClient();
		sqsClient = DependencyFactory.sqsClient();
	}

	public void handler(Context context) {
		OrderDbService service = new OrderDbService(dynamoDbClient, context);
		SQSService sqsService = new SQSService(sqsClient, context);
		List<Order> scanOrderStatus = service.scanOrderStatus();

		scanOrderStatus.forEach((order) -> {
			String customerId = order.getCustomerIdAndOrderId().split("_")[0];
			String orderId = order.getCustomerIdAndOrderId().split("_")[1];
			sqsService.sendToSQS(order.getDealerId(), customerId, orderId, order.getBucketName(), order.getObjectKey());
			service.updateOrder(order.getDealerId(), order.getCustomerIdAndOrderId());
		});
	}
}
