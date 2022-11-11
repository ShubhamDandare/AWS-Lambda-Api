package com.order.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.amazonaws.services.lambda.runtime.Context;
import com.order.entity.Order;
import com.order.entity.OrderDetails;
import com.order.entity.OrderRequest;
import com.order.exception.OrderInitiateException;
import com.order.exception.OrderUpdateException;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.utils.StringUtils;

public class OrderDbService {

	private DynamoDbEnhancedClient client;
	private Context context;

	public OrderDbService(DynamoDbClient client, Context context) {
		this.client = DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();
	}

	public DynamoDbTable<Order> getOrderTable() {
		return client.table("order", TableSchema.fromClass(Order.class));
	}

	public void saveOrder(Order order) {

		try {
			DynamoDbTable<Order> orderTable = getOrderTable();
			orderTable.putItem(order);

		} catch (DynamoDbException e) {
			context.getLogger().log(e.getMessage());
		}

	}

	public boolean checkOrderStatus(OrderDetails orderDetails) {

		context.getLogger().log(orderDetails.getCustomerId() + " " + orderDetails.getOrderId());
		DynamoDbTable<Order> orderTable = getOrderTable();
		Key key = Key.builder().partitionValue(orderDetails.getDealerId())
				.sortValue(orderDetails.getCustomerId() + "#" + orderDetails.getOrderId()).build();

		Order item = orderTable.getItem((GetItemEnhancedRequest.Builder request) -> request.key(key));
		context.getLogger().log(item.getOrderStatus());

		if (StringUtils.equals(item.getOrderStatus(), "Received")) {
			return true;
		} else {
			return false;
		}

	}

	public List<OrderDetails> parseJavaObjtoJson(String fileinput, String dealerId) {
		List<OrderDetails> orderList = new ArrayList<>();

		String[] line = fileinput.split(System.lineSeparator());

		List<String> topHeader = Arrays.asList(line[0].split(","));

		for (int i = 1; i <= line.length; i++) {
			List<String> content = Arrays.asList(line[i].split(","));
			if (content.size() != topHeader.size()) {
				return null;

			} else {
				Random random = new Random();
				int nextInt = random.nextInt(99999);
				String orderId = String.format("%05d", nextInt);

				OrderDetails Details = new OrderDetails(orderId, content.get(0), dealerId, content.get(1),
						content.get(2), content.get(3), content.get(4), content.get(5), content.get(6), "Received");
				orderList.add(Details);
			}
		}
		return orderList;
	}

	public String generateOrderId() {
		Random random = new Random();
		int nextInt = random.nextInt(99999);
		String orderId = String.format("%05d", nextInt);
		return orderId;
	}

	public List<OrderDetails> getConvertedToOrder(OrderRequest request) {
		Base64.Decoder decoder = Base64.getMimeDecoder();
		byte[] decodedBytes = decoder.decode(request.getObjectKey());
		String str = new String(decodedBytes);
		return parseJavaObjtoJson(str, request.getDealerId());
	}

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

	public void processOrder(OrderDetails details) {

		try {
			DynamoDbTable<Order> orderTable = getOrderTable();
			Key key = Key.builder().partitionValue(details.getDealerId())
					.sortValue(details.getCustomerId() + "#" + details.getOrderId()).build();
			Order item = orderTable.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));
			item.setOrderStatus("Processed_Order");
			item.setExpectedDeliveryDate(item.getExpectedDeliveryDate());
			item.setPrice(item.getPrice());
			orderTable.updateItem(item);
		} catch (Exception e) {
			throw new OrderUpdateException("fail to update order process initiated");
		}

	}

	public List<Order> fetchAllorder(String dealerId, String customerId, String orderId) {
		DynamoDbTable<Order> orderTable = getOrderTable();
		Key key = Key.builder().partitionValue(dealerId).sortValue(customerId + "#" + orderId).build();
		Order item = orderTable.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));

		List<Order> list = new ArrayList<>();
		list.add(item);
		return list;

	}

	public List<Order> fetchAllorder(String dealerId) {
		DynamoDbTable<Order> orderTable = getOrderTable();
		QueryConditional conditional = QueryConditional.keyEqualTo(Key.builder().partitionValue(dealerId).build());
		// PageIterable<Order> query =
		// orderTable.query(QueryEnhancedRequest.builder().queryConditional(conditional).build());
		Iterator<Order> iterator = orderTable.query(conditional).items().iterator();
		List<Order> order = new ArrayList<>();
		while (iterator.hasNext()) {
			order.add(iterator.next());
		}
		return order;
	}

}
