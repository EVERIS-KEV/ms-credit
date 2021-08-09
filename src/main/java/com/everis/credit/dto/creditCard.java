package com.everis.credit.dto;

import com.everis.credit.logic.myFunctions;

import lombok.*; 

@Getter
@Setter
@NoArgsConstructor
public class creditCard {
	private String numberCard;
	private String password; 
	
	public creditCard(String password) {
		this.numberCard = myFunctions.numberAccount(8);
		this.password = myFunctions.encriptSHA1(password);
	}
}
