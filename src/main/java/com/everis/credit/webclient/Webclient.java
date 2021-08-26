package com.everis.credit.webclient;

import com.everis.credit.constant.Constants;
import org.springframework.web.reactive.function.client.WebClient;

public class Webclient { 
	public static final WebClient customer = WebClient.create(Constants.Path.CUSTOMERS_PATH);
	public static final WebClient logic = WebClient.create(Constants.Path.LOGIC_PATH); 
}
