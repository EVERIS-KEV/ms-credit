package com.everis.credit.dto;

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
