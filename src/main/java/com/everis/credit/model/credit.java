package com.everis.credit.model;

import com.everis.credit.dto.creditCard;
import com.everis.credit.dto.operation;
import java.util.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "credits")
public class credit {
  @Id
  private String idCredit;

  private String idAccount; //ID DEL CLIENTE
  private String typeAccount;
  private double baseCreditLimit;
  private double amount;

  private creditCard creditcard;
  private List<operation> operation;

  public credit() {
    this.operation = new ArrayList<operation>();
  }

  public credit(String idAccount, double baseCreditLimit, String password) {
    this.idAccount = idAccount;
    this.baseCreditLimit = baseCreditLimit;
    this.amount = baseCreditLimit;
    
    this.creditcard = new creditCard(password);

    this.operation = new ArrayList<operation>();
  }
}
