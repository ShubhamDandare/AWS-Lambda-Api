package com.dealer.service;

import java.awt.event.ItemEvent;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.amazonaws.services.lambda.runtime.Context;
import com.dealer.entity.Dealer;
import com.dealer.exception.InvalidCredentialsException;

import software.amazon.awssdk.core.Response;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class LoginService {
//creating dynamoDb Enhanced client
	private DynamoDbEnhancedClient client;
	private String tableName;

	public LoginService(DynamoDbClient client, Context context) {
		this.client = DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();
		this.tableName =System.getenv("tableName");
		//this.tableName = context.getClientContext().getEnvironment().get("tableName");
	}

	// Login Table Created
//	static final TableSchema<Dealer> DEALER_LOGIN_TABLE = TableSchema.fromClass(Dealer.class);
//	DynamoDbTable<Dealer> LoginTable = client.table("Dealer_2022", DEALER_LOGIN_TABLE);

	public Dealer login(Dealer dealerInput) {

		Dealer dealer = new Dealer();
		// Login Table Created
		DynamoDbTable<Dealer> table = getDealerTable();

		Dealer item = table.getItem(Key.builder().partitionValue(dealerInput.getUsername()).build());
		if (item != null) {

			String encodePassword = item.getPassword();
			String hashpassword = hashpassword(dealerInput.getPassword(), item.getSalt());

			if (encodePassword.equals(hashpassword)) {
				// System.out.println("login successfull");

				dealer.setMessage("login successfull");
				dealer.setId(item.getId());
				return dealer;
			} 
			
		}
		throw new InvalidCredentialsException("Invalid username & password . .");

		
	}

	public DynamoDbTable<Dealer> getDealerTable() {
		return client.table(this.tableName, TableSchema.fromClass(Dealer.class));
	}

	public String hashpassword(String password, String hashSalt) {
		byte[] salt = null;

		if (hashSalt == null) {
			SecureRandom random = new SecureRandom();
			salt = new byte[16];
			random.nextBytes(salt);

		} else {
			salt = Base64.getDecoder().decode(hashSalt);
		}

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory;
		String hashpassword = "empty";
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = factory.generateSecret(spec).getEncoded();
			hashpassword = Base64.getEncoder().encodeToString(hash);
			System.out.println(Base64.getEncoder().encodeToString(hash));
			System.out.println(Base64.getEncoder().encodeToString(salt));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hashpassword;
	}

	public String AddandUpdateDealer(Dealer dealer) {

		DynamoDbTable<Dealer> table = getDealerTable();
		table.putItem(dealer);

		if (Objects.nonNull(table)) {
			return "Dealer Added successfuly";
		} else {
			return "Failed to add user";
		}

	}

	public Dealer getDealerDeatils(String key, Dealer dealer) {

		DynamoDbTable<Dealer> table = getDealerTable();
		// fetching data from db with the help of key pattern
		Key usernameKey = Key.builder().partitionValue(dealer.getUsername()).build();

		Dealer item = table.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(usernameKey));
		// Map<K, V>
		// table.scan((t)->t.addAttributeToProject(key).filterExpression(Expression.builder().expressionNames("username")));

		if (Objects.nonNull(item)) {
			dealer.setId(item.getId());
			dealer.setUsername(item.getUsername());
			dealer.setPassword(item.getPassword());
			return dealer;
		}
		return null;

	}

}
