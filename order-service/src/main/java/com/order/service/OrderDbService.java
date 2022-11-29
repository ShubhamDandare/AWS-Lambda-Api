package com.order.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.order.entity.Order;
import com.order.entity.OrderDetails;
import com.order.exception.OrderInitiateException;
import com.order.exception.OrderUpdateException;

import software.amazon.awssdk.core.internal.waiters.ResponseOrException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;
import software.amazon.awssdk.utils.StringUtils;

public class OrderDbService {

	private DynamoDbEnhancedClient client;
	private Context context;

	public OrderDbService(DynamoDbClient client, Context context) {
		this.client = DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();

	}

	public DynamoDbTable<Order> getOrderTable() {
		return client.table("order-detail", TableSchema.fromBean(Order.class));
	}
//	 public static  DynamoDbTable<Order> createTable(DynamoDbEnhancedClient  client) {
//	        // Create a DynamoDbTable object
//	        DynamoDbTable<Order> customerTable = client.table("order-detail", TableSchema.fromBean(Order.class));
//	        // Create the table
//	        customerTable.createTable(builder -> builder
//	                .provisionedThroughput(b -> b
//	                        .readCapacityUnits(10L)
//	                        .writeCapacityUnits(10L)
//	                        .build())
//	        );
//
//	        System.out.println("Waiting for table creation...");
//
//	        try (DynamoDbWaiter waiter = DynamoDbWaiter.create()) { // DynamoDbWaiter is Autocloseable
//	            ResponseOrException<DescribeTableResponse> response = waiter
//	                    .waitUntilTableExists(builder -> builder.tableName("order-detail").build())
//	                    .matched();
//	            DescribeTableResponse tableDescription = response.response().orElseThrow(
//	                    () -> new RuntimeException("order-detail table was not created."));
//	            // The actual error can be inspected in response.exception()
//	            System.out.println(tableDescription.table().tableName() + " was created.");
//	        }
//	        return customerTable;
//	    }

	public void saveOrder(Order order) {

		try {
			DynamoDbTable<Order> orderTable = getOrderTable();
			orderTable.putItem(order);

			// DynamoDbTable<Order> createTable = createTable(client);

			// createTable.putItem(order);

		} catch (DynamoDbException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			// context.getLogger().log(e.getMessage());
		}

	}

	public boolean checkOrderStatus(OrderDetails orderDetails) {

		// context.getLogger().log(orderDetails.getCustomerId() + "_" +
		// orderDetails.getOrderId());
		System.out.println(
				"check order status in orderDb =" + orderDetails.getCustomerId() + "_" + orderDetails.getOrderId());
		DynamoDbTable<Order> orderTable = getOrderTable();
		Key key = Key.builder().partitionValue(orderDetails.getDealerId())
				.sortValue(orderDetails.getCustomerId() + "_" + orderDetails.getOrderId()).build();
		System.out.println("key = " + key.toString());
		Order item = orderTable.getItem((GetItemEnhancedRequest.Builder request) -> request.key(key));
		// context.getLogger().log(item.getOrderStatus());
		System.out.println("Order Details item = " + item.toString());
		System.out.println("status =" + item.getOrderStatus());

		if (StringUtils.equals(item.getOrderStatus(), "Order_Received")) {
			return true;
		} else {
			return false;
		}

	}

//		public String generateOrderId() {
//		Random random = new Random();
//		int nextInt = random.nextInt(99999);
//		String orderId = String.format("%05d", nextInt);
//		return orderId;
//	}

	public List<Order> scanOrderStatus() {
		DynamoDbTable<Order> orderTable = getOrderTable();

		Map<String, String> mapName = new HashMap<>();
		mapName.put("#orderStatus", "orderStatus");

		Map<String, AttributeValue> mapValue = new HashMap<>();

		mapValue.put(":value", AttributeValue.builder().s("Order_Received").build());

		Expression expression = Expression.builder().expressionNames(mapName).expressionValues(mapValue).build();

		ScanEnhancedRequest enhancedRequest = ScanEnhancedRequest.builder().filterExpression(expression).build();

		Iterator<Order> iterator = orderTable.scan(enhancedRequest).items().iterator();
		List<Order> order = new ArrayList<>();
		while (iterator.hasNext()) {
			Order next = iterator.next();
			order.add(next);

		}
		return order;
	}

	public void updateOrder(String partitionKey, String sortKey) {
		try {
			DynamoDbTable<Order> orderTable = getOrderTable();
			Key key = Key.builder().partitionValue(partitionKey).sortValue(sortKey).build();
			Order item = orderTable.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));

			item.setOrderStatus("Initiated_Order");
			orderTable.updateItem(item);
		} catch (DynamoDbException e) {
			throw new OrderInitiateException("fail to update order status as Initiated");
		}

	}

//	public void processOrder(OrderDetails details) {
//
//		try {
//			DynamoDbTable<Order> orderTable = getOrderTable();
//			Key key = Key.builder().partitionValue(details.getDealerId())
//					.sortValue(details.getCustomerId() + "#" + details.getOrderId()).build();
//			Order item = orderTable.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));
//			item.setOrderStatus("Processed_Order");
//			item.setExpectedDeliveryDate(item.getExpectedDeliveryDate());
//			item.setPrice(item.getPrice());
//			orderTable.updateItem(item);
//		} catch (Exception e) {
//			throw new OrderUpdateException("fail to update order process initiated");
//		}
//
//	}

	public List<Order> fetchAllorder(String dealerId, String customerId, String orderId) throws IOException{
		DynamoDbTable<Order> orderTable = getOrderTable();
		Key key = Key.builder().partitionValue(dealerId).sortValue(customerId + "_" + orderId).build();
		Order item = orderTable.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));

		List list = new ArrayList<>();
		list.add(item);
		return list;

	}

	public List<OrderDetails> fetchAllorder(String dealerId) {
		DynamoDbTable<Order> orderTable = getOrderTable();
		QueryConditional conditional = QueryConditional.keyEqualTo(Key.builder().partitionValue(dealerId).build());
		// PageIterable<Order> query =
		// orderTable.query(QueryEnhancedRequest.builder().queryConditional(conditional).build());
		Iterator<Order> iterator = orderTable.query(conditional).items().iterator();
		List order = new ArrayList<>();
		while (iterator.hasNext()) {
			order.add(iterator.next());
		}
		return order;
	}

}
