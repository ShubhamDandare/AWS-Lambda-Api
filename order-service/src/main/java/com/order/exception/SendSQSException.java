package com.order.exception;

public class SendSQSException extends RuntimeException{

	public SendSQSException(String msg) {
		super(msg);
	}
}
