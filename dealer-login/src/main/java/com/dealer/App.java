package com.dealer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.dealer.entity.Dealer;
import com.dealer.exception.InvalidCredentialsException;
import com.dealer.service.LoginService;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.utils.StringUtils;

/**
 * Lambda function entry point. You can change to use other pojo type or
 * implement a different RequestHandler.
 *
 * @see <a
 *      href=https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html>Lambda
 *      Java Handler</a> for more information
 */
public class App implements RequestHandler<Dealer, Dealer>, RequestStreamHandler {

	private final DynamoDbClient dynamoDbClient;

	public App() {
		// Initialize the SDK client outside of the handler method so that it can be
		// reused for subsequent invocations.
		// It is initialized when the class is loaded.
		dynamoDbClient = DependencyFactory.dynamoDbClient();
		// dynamoDbClient.get

		// Consider invoking a simple api here to pre-warm up the application, eg:
		// dynamodb#listTables
	}

	@Override
	public Dealer handleRequest(final Dealer input, final Context context) {
		// TODO: invoking the api call using dynamoDbClient.
		// context.getClientContext().getEnvironment();
		LoginService loginService = new LoginService(dynamoDbClient, context);
		
		if (StringUtils.isEmpty(input.getUsername()) || StringUtils.isEmpty(input.getPassword())) {
			throw new InvalidCredentialsException("username or password is empty");

		}

		try {
			Dealer login = loginService.login(input);
			return login;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		// TODO Auto-generated method stub

	}
}
