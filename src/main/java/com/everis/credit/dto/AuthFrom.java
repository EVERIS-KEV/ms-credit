package com.everis.credit.dto;

import javax.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthFrom {
  @NotBlank(message = "Debe ingresar su tarjeta.")
  private String creditCardNumber;

  @NotBlank(message = "Debe ingresar su contrasena.")
  private String password;   
  private double amount;  

  @NotBlank(message = "Debe elejir un tipo de operación.")
  private String type;   
  
}
