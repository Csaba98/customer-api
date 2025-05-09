package com.customer.api.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.customer.api.entity.Customer;

public final class CommonUtil {

	private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	private static final String PASSWORD_1 = "Abssdsd1232*-";
	private static final String PASSWORD_2 = "Abcdef16699*-";

	public static void writeToLOG(HttpMethod httpMethod, String URI) {
		logger.info(String.format("%s %s", httpMethod, URI));
	}

	public static void writeToLOG(HttpMethod httpMethod, String URI, String queryString) {
		logger.info(String.format("%s %s?%s", httpMethod, URI, queryString));
	}

	public static String getRandomElement(String[] s) {
		Random rand = new Random();
		return s[rand.nextInt(s.length)];
	}
	
	public static Customer customerForTest(int i) {
		Customer customer = new Customer();

		customer.setFirstname("TestFirstName");
		customer.setLastname("TestLastName");
		customer.setAge((short) 20);
		customer.setEmail("tstemail@hotmail.com");
		customer.setPhone("+36201234567");

		String password = i == 0 ? PASSWORD_1 : PASSWORD_2;
		customer.setPassword(password);

		customer.setUsername("test.username");

		return customer;
	}
	
	public static String getPassword1Str() {
		return PASSWORD_1;
	}
	
	public static String getPassword2Str() {
		return PASSWORD_2;
	}		

}
