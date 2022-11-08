package com.order;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.order.entity.Order;
import com.order.entity.OrderDetails;
import com.order.entity.OrderRequest;
import com.order.service.OrderDbService;
import com.order.service.S3Service;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Lambda function entry point. You can change to use other pojo type or
 * implement a different RequestHandler.
 *
 * @see <a
 *      href=https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html>Lambda
 *      Java Handler</a> for more information
 */
public class saveOrder implements RequestHandler<OrderRequest, List<OrderDetails>> {
	private final DynamoDbClient dynamoDbClient;
	private final S3Client s3Client;

	public saveOrder() {
		// Initialize the SDK client outside of the handler method so that it can be
		// reused for subsequent invocations.
		// It is initialized when the class is loaded.
		dynamoDbClient = DependencyFactory.dynamoDbClient();
		// Consider invoking a simple api here to pre-warm up the application, eg:
		// dynamodb#listTables
		s3Client = DependencyFactory.s3Client();
	}

	@Override
	public List<OrderDetails> handleRequest(OrderRequest input, Context context) {

		OrderDbService service = new OrderDbService(dynamoDbClient, context);
		List<OrderDetails> convertedToOrder = service.getConvertedToOrder(input);
		S3Service s3Service = new S3Service(s3Client, context);
		convertedToOrder.forEach((order) -> {
			Order saveObjToS3 = s3Service.saveObjToS3(order);
			service.saveOrder(saveObjToS3);
		});

		return convertedToOrder;
	}
}
