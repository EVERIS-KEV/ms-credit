package com.everis.credit.webclient;

import org.springframework.web.reactive.function.client.WebClient;

public class webclient {
	private static String gateway = "44.196.6.42:8090";
	public static WebClient customer = WebClient.create("http://" + gateway + "/service/customers");
	public static WebClient logic = WebClient.create("http://" + gateway + "/service/logic");
}
