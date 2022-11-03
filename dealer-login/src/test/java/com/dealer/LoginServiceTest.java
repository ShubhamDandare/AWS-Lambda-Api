package com.dealer;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.mockito.AdditionalMatchers.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;

import com.amazonaws.services.lambda.runtime.Context;
import com.dealer.entity.Dealer;
import com.dealer.service.LoginService;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

//XchjnnhluQHfX6Bozn6uAQ==
//FyHfgWLBdambgdNoTWK7XA==
class LoginServiceTest {

	@Test
	void testLogin() throws NoSuchAlgorithmException, InvalidKeySpecException {
		Dealer dealer = new Dealer();
		dealer.setUsername("shubham");
		dealer.setPassword("12345asd");
		dealer.setId(1L);

		Dealer dealer2 = new Dealer();
		dealer2.setUsername("shubham");
		dealer2.setPassword("XchjnnhluQHfX6Bozn6uAQ==");
		dealer2.setId(1L);

		DynamoDbClient mockclient = mock(DynamoDbClient.class);
		Context mockcontext = mock(Context.class);
		DynamoDbTable dynamoDbTable = mock(DynamoDbTable.class);

		LoginService service = spy(new LoginService(mockclient, mockcontext));
		when(service.getDealerTable()).thenReturn(dynamoDbTable);
		when(dynamoDbTable.getItem(any(Consumer.class))).thenReturn(dealer2);
		service.hashpassword(dealer.getPassword(), "FyHfgWLBdambgdNoTWK7XA==");

		Dealer login = service.login(dealer);
		assertEquals(1L, login.getId());
	}

}
