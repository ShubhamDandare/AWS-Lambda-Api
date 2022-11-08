package com.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.order.entity.OrderRequest;

public class saveOrderTest {

    @Test
    public void handleRequest_shouldReturnConstantValue() {
    	saveOrder function = new saveOrder();
    	OrderRequest request=new OrderRequest();
    	function.handleRequest(request, null);
//        Object result = function.handleRequest("echo", null);
//        assertEquals("echo", result);
    }
}
