package com.customer.api.util;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

public final class CommonUtil {

	private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

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

}
