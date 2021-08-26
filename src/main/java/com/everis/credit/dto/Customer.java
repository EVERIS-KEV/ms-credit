package com.everis.credit.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
  @Id 
  private String idclient;

  private String dni;
  private String firstname;
  private String lastname;
  private String type;
}
