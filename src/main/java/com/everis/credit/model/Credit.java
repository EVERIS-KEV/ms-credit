package com.everis.credit.model;

import java.time.LocalDateTime;
import java.util.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "credits")
public class Credit {
  @Id
  private String idCredit;

  private String idCustomer; 
  private String typeAccount;
  private double baseCreditLimit;
  private double amount;

  private CreditCard creditcard;

  private LocalDateTime dateCreated = LocalDateTime.now();
  private List<Operation> operation = new ArrayList<>();

  public Credit(String idCustomer, double baseCreditLimit, String password) {
      this.idCustomer = idCustomer;
      this.baseCreditLimit = baseCreditLimit;
      this.amount = baseCreditLimit;
      this.creditcard = new CreditCard(password);
  }
}
