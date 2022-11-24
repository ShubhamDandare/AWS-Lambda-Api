package com.order.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import com.order.entity.OrderDetails;

public class Test {

	public static void main(String[] args) {
		String str1 = "bWFrZSxtb2RlbCx5ZWFyLGZ1ZWxUeXBlLGN1c3Rtb2VySWQsY3VzdG1vZXJOYW1lLGNpdHkNCkZvcmQsZmlnbywyMDIyLHBldHJvbCwxMDAxLHNodWJoYW0sUHVuZQ0KTUdIZWN0b3Isc29uZXQsMjAyMixkaXNlbCwxMDAyLHByYXRpayxOYWdwdXINClhVVixYVVY3MDAsMjAyMixwZXRyb2wsMTAwMyxNaWxpbmQsUHVuZSA=";
		String str2 = "101";
		Test t = new Test();
		t.getConvertedToOrder2(str1, str2);
		// System.out.println("parse order list ="+convertedToOrder2.toString());

	}

	public List<OrderDetails> getConvertedToOrder2(String objectKey, String dealerId) {
		// context.getLogger().log(" getConvertedToOrder2 service filed ="+objectKey+"
		// ::ID ="+dealerId);

		String decodetoString = decodetoString(objectKey);
		// System.out.println("decodetoString = "+decodetoString);
		return parseJavaObjtoJson(decodetoString, dealerId);
	}

	public String decodetoString(String str) {
		// context.getLogger().log(" decodetoString service filed ="+str);
		Base64.Decoder decoder = Base64.getMimeDecoder();
		byte[] Bytes = decoder.decode(str);
		return new String(Bytes);
	}

	public List<OrderDetails> parseJavaObjtoJson(String objectKey, String dealerId) {
		List<OrderDetails> orderList = new ArrayList<>();

		String[] line = objectKey.split(System.lineSeparator());
		// System.out.println("topHeader====="+line[1].split(","));//topHeader=====[Ford,
		// figo, 2022, petrol, 1001, shubham, Pune]
		List<String> topHeader = Arrays.asList(line[0].split(","));
		// System.out.println("topHeader"+topHeader);

		for (int i = 1; i <= line.length - 1; i++) {

			List<String> content = Arrays.asList(line[i].split(","));
			// content.forEach(con->System.out.println("content = "+con));
			if (content.size() != topHeader.size()) {
				return null;

			} else {
				Random random = new Random();
				int nextInt = random.nextInt(99999);
				String orderId = String.format("%05d", nextInt);

//				OrderDetails Details = new OrderDetails(orderId, content.get(0), dealerId, content.get(1),
//						content.get(2), content.get(3), content.get(4), content.get(5), content.get(6),
//						"Order_Received");
//				orderList.add(Details);
				OrderDetails Details = new OrderDetails(orderId, content.get(4), dealerId, content.get(1),
						content.get(0), content.get(2), content.get(3), content.get(5), content.get(6), "Order_Received");
				orderList.add(Details);
			}
		}
		for (OrderDetails str : orderList) {
			System.out.println(str.getOrderId() + "," + str.getCustomerId()+"," + str.getDealerId() + "," + str.getModel()
					+ "," + str.getMake() + "," + str.getYear() + "," + str.getFuelType() + "," + str.getCustomerName()
					+ "," + str.getCity() + "," + str.getOrderStatus());
		}
		return orderList;
	}
//	"orderId": "06654",------->(orderId)
//	  "customerId": "Ford",-------------->content.get(0)
//	  "dealerId": "101",------------->dealerId, 
//	  "model": "figo",-------------->content.get(1)
//	  "make": "2022",-------------->content.get(2)
//	  "year": "petrol",-------------->content.get(3)
//	  "fuelType": "1001",-------------->content.get(4)
//	  "customerName": "shubham",-------------->content.get(5)
//	  "city": "Pune\r",-------------->content.get(6)
//	  "orderStatus": "Order_Received",
//	  "price": 0.0
}
