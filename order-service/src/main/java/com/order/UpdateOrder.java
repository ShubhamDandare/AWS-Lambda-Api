package com.order;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.order.entity.OrderDetails;
import com.order.exception.OrderUpdateException;
import com.order.service.OrderDbService;
import com.order.service.S3Service;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

public class UpdateOrder implements RequestHandler<OrderDetails, OrderDetails>{

	private final DynamoDbClient dynamoDbClient;
	private final S3Client s3Client;
	
	
	public UpdateOrder() {
		dynamoDbClient = DependencyFactory.dynamoDbClient();
		s3Client = DependencyFactory.s3Client();
	}
	
	
	@Override
	public OrderDetails handleRequest(OrderDetails input, Context context) {
		OrderDbService service = new OrderDbService(dynamoDbClient, context);
		S3Service s3Service = new S3Service(s3Client, context);
		context.getLogger().log("order update data deatils ="+input);
		boolean checkOrderStatus = service.checkOrderStatus(input);
		if(checkOrderStatus) {
			s3Service.saveObjToS3(input);
		}
		else {
			throw new OrderUpdateException("fail to update order");
		}
		return input;
	}

}
