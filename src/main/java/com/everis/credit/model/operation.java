package com.everis.credit.model;

import java.util.Date;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class operation {
  private double amount;

  private Date dateCreated = new Date(); // Deposito Retiro Transferencia ComisiÃƒÂ³n
  private String type; 

  public operation(double amount, String type) {
    this.type = type;
    this.amount = amount; 
  }
}
