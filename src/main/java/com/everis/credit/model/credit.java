package com.everis.credit.model;

import com.everis.credit.dto.creditCard;
import lombok.*; 

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "credits")
public class credit {
  @Id
  private String idCredit; 
  private String idAccount;
  private String typeAccount;
  private double baseCreditLimit; //Payments  consumptions
  private creditCard creditcard;

  public credit(String idAccount, double baseCreditLimit, String password) {
    this.idAccount = idAccount;
    this.baseCreditLimit = baseCreditLimit;
    this.creditcard = new creditCard( password );
  }
}
