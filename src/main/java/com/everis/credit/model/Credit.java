package com.everis.credit.model;

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

  private Date dateCreated = new Date(); // Deposito Retiro Transferencia ComisiÃƒÆ’Ã‚Â³n
  private List<Operation> operation = new ArrayList<>();

  public Credit(String idCustomer, double baseCreditLimit, String password) {
    this.idCustomer = idCustomer;
    this.baseCreditLimit = baseCreditLimit;
    this.amount = baseCreditLimit;
    this.creditcard = new CreditCard(password);
  }
}
