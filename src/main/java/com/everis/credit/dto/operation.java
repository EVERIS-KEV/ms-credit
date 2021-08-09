package com.everis.credit.dto;

import java.util.Date;
import lombok.*;

@Getter
@Setter
public class operation {
  private double amount;
  private String date;
  private String type;

  public operation() {
    this.date = new Date().toString();
  }

  public operation(double amount, String type) {
    this.type = type;
    this.amount = amount;
    this.date = new Date().toString();
  }
}
