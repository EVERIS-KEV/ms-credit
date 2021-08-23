package com.everis.credit.model;

import java.util.Date;

import com.everis.credit.webclient.Webclient;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CreditCard {
  private String numberCard = Webclient.logic
    .get()
    .uri("/generatedNumberLong/6")
    .retrieve()
    .bodyToMono(String.class)
    .block();

  private Date dateCreated = new Date(); // Deposito Retiro Transferencia ComisiÃƒÂ³n
  private String password;

  public CreditCard(String password) {
    this.password =
      Webclient.logic
        .get()
        .uri("/encriptBySha1/" + password)
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}
