package com.order.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import com.amazonaws.services.lambda.runtime.Context;
import com.order.entity.OrderDetails;
import com.order.entity.OrderRequest;

public class OrderConverter {

	private Context context;
   
	
	public List<OrderDetails> getConvertedToOrder(OrderRequest input) {
		String decodetoString = decodetoString(input.getObjectKey().toString());
		return parseJavaObjtoJson(decodetoString,input.getDealerId().toString());	
		
	}
	
	public String decodetoString(String str) {
		Base64.Decoder decoder = Base64.getMimeDecoder();
		 byte[] Bytes = decoder.decode(str);
		 return new String(Bytes);
	}
	
	public List<OrderDetails> parseJavaObjtoJson(String objectKey, String dealerId) {
		List<OrderDetails> orderList = new ArrayList<>();

		String[] line = objectKey.split(System.lineSeparator());

		List<String> topHeader = Arrays.asList(line[0].split(","));

		for (int i = 1; i <= line.length-1; i++) {
			List<String> content = Arrays.asList(line[i].split(","));
			content.forEach(con->System.out.println("content  = "+con));
			
			
			
			if (content.size() != topHeader.size()) {
				return null;

			} else {
				Random random = new Random();
				int nextInt = random.nextInt(99999);
				String orderId = String.format("%05d", nextInt);

				OrderDetails Details = new OrderDetails(orderId, content.get(4), dealerId, content.get(1),
						content.get(0), content.get(2), content.get(3), content.get(5), content.get(6), "Order_Received");
				orderList.add(Details);
			}
		}
		return orderList;
	}


}
