package com.order.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.order.exception.SendSQSException;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class SQSService {

	private final SqsClient sqsClient;
	private final Context context;
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	//todo update sqs_url url 
	private final String SQS_URL = "";

	public SQSService(SqsClient sqsClient, Context context) {
		this.sqsClient = sqsClient;
		this.context = context;
	}

	public void sendToSQS(String dealerId, String customerId, String orderId, String bucketName, String objectKey) {

		try {

			JsonObject obj = new JsonObject();
			obj.addProperty("dealerId", dealerId);
			obj.addProperty("customerId", customerId);
			obj.addProperty("orderId", orderId);
			obj.addProperty("bucketName", bucketName);
			obj.addProperty("objectKey", objectKey);

			sqsClient.sendMessage(SendMessageRequest.builder().queueUrl(SQS_URL).messageBody(gson.toJson(obj))
					.messageGroupId("ProcessOrder").build());

		} catch (SqsException e) {
			throw new SendSQSException("SQS message fail to send");
		}
	}

}
