package com.everis.credit.model;

import com.everis.credit.dto.creditCard;
import com.everis.credit.dto.operation;
import java.util.*;
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

  private String idCustomer;  
  private String typeAccount;
  private double baseCreditLimit;
  private double amount;

  private creditCard creditcard;
  private List<operation> operation = new ArrayList<operation>(); 

  public credit(String idCustomer, double baseCreditLimit, String password) {
    this.idCustomer = idCustomer;
    this.baseCreditLimit = baseCreditLimit;
    this.amount = baseCreditLimit; 
    this.creditcard = new creditCard(password); 
  }
}
