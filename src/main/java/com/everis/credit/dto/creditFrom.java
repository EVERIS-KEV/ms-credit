package com.everis.credit.dto;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class creditFrom {
  @NotBlank(message = "Debe seleccionar un cliente.")
  private String idAccount; 
  private double baseCreditLimit;
  @NotBlank(message = "Debe ingresar una contraseña para su tarjeta.")
  private String password;
}
