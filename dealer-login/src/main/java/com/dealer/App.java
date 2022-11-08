package com.dealer;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
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
public class App implements RequestHandler<Dealer, Dealer> {

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
		context.getLogger().log("Lambda function start......");
		LoginService loginService = new LoginService(dynamoDbClient, context);
	
		context.getLogger().log(input.getUsername());
		//context.getLogger().log(input.getUsername());
		if (StringUtils.isEmpty(input.getUsername()) || StringUtils.isEmpty(input.getPassword())) {
			throw new InvalidCredentialsException("username or password is empty");

		}

		
			Dealer login = loginService.login(input);
		
			return login;
		
		
	}

	
}
