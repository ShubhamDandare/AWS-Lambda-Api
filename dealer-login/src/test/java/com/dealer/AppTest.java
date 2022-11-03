package com.dealer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.dealer.entity.Dealer;

public class AppTest {

	@Test
	public void handleRequest_shouldReturnConstantValue() {
		App function = new App();
		Dealer dealer = new Dealer();
		Context mockcontex = mock(Context.class);
		// LoginService service = mock(LoginService.class);
		Dealer result = function.handleRequest(dealer, mockcontex);
		assertNotNull(result);
	}
}
