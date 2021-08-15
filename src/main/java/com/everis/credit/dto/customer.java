package com.everis.credit.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class customer {
  @Id
  private String idclient;

  private String dni;
  private String firstname;
  private String lastname;
  private String type;
}
