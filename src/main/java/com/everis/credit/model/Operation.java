package com.everis.credit.model;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor 
public class Operation {
  private double amount;

  private LocalDateTime dateCreated = LocalDateTime.now();
  private String type;

  public Operation(double amount, String type) {
    this.type = type;
    this.amount = amount;
  }
}
