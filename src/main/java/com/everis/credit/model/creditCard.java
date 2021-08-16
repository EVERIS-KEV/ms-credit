package com.everis.credit.model;

import java.util.Date;

import com.everis.credit.webclient.webclient;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class creditCard {
  private String numberCard = webclient.logic
    .get()
    .uri("/generatedNumberLong/6")
    .retrieve()
    .bodyToMono(String.class)
    .block();

  private Date dateCreated = new Date(); // Deposito Retiro Transferencia ComisiÃƒÂ³n
  private String password;

  public creditCard(String password) {
    this.password =
      webclient.logic
        .get()
        .uri("/encriptBySha1/" + password)
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}
