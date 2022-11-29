package com.order.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.order.entity.Order;
import com.order.entity.OrderDetails;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.utils.IoUtils;

public class S3Service {

	private Context context;
	private S3Client s3Client;
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public S3Service(S3Client s3Client, Context context) {
		this.context = context;
		this.s3Client = s3Client;
	}

//file uploade
	public Order saveObjToS3(OrderDetails orderDetails) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String fileName = orderDetails.getDealerId() + "_" + orderDetails.getCustomerId() + "_"
				+ orderDetails.getOrderId();
		String customerIdAndOrderId = orderDetails.getCustomerId() + "_" + orderDetails.getOrderId();
		try {
			PutObjectRequest objectRequest = PutObjectRequest.builder().bucket("sgd-orderbucket").key(fileName).build();

			s3Client.putObject(objectRequest, RequestBody.fromString(gson.toJson(orderDetails)));
			Order order = new Order(orderDetails.getDealerId(), customerIdAndOrderId, "sgd-orderbucket", fileName,
					orderDetails.getOrderStatus());
			return order;

		} catch (Exception e) {
			context.getLogger().log("Exception in S3 : " + e.getMessage());
			throw new RuntimeException("File Upload failed");
		}
	}

	// fetch file
	public OrderDetails fetchOrderfromS3(String bucketname, String objectKey) throws IOException {
		System.out.println("BUCKETNAME =" + bucketname + " OBJECTKEY =" + objectKey);

		try {
			GetObjectRequest getObjectRequest = GetObjectRequest.builder().key(objectKey).bucket(bucketname).build();
			ResponseInputStream<GetObjectResponse> object = s3Client.getObject(getObjectRequest);

			InputStream stream = new ByteArrayInputStream(object.readAllBytes());

//			 System.out.println("Content :" + new String(object.readAllBytes(),
//			 StandardCharsets.UTF_8));
//			 System.out.println("stream = " + stream.toString());
			String utf8String = IoUtils.toUtf8String(stream);
		//	System.out.println("utf8String = " + utf8String.toString());
			return gson.fromJson(utf8String.toString(), OrderDetails.class);

		} catch (IOException e) {

			e.printStackTrace();
			context.getLogger().log("Exception while reading order from s3: " + e.getMessage());
			throw new IOException("fail to fetch s3 object");
		}

	}

}
