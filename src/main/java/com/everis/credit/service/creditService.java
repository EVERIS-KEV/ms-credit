package com.everis.credit.service;

import com.everis.credit.dto.*; 
import com.everis.credit.model.credit;
import com.everis.credit.webclient.webclient;
import com.everis.credit.repository.creditRepository;

import java.util.*; 
import reactor.core.publisher.*; 
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional; 

@Service
@Transactional
public class creditService {
  @Autowired
  creditRepository repository; 

  private Boolean verifyCustomer(String id) {
    return webclient.customer
      .get()
      .uri("/verifyId/{id}", id)
      .retrieve()
      .bodyToMono(Boolean.class)
      .block();
  }

  private customer customerFind(String id) {
    return webclient.customer
      .get()
      .uri("/{id}", id)
      .retrieve()
      .bodyToMono(customer.class)
      .block();
  }

  private Boolean existsByNumberCreditCard(String number, String passowrd) {
	return repository.findAll().stream().filter( c ->  c.getCreditcard().getNumberCard().equals(number) && c.getCreditcard().getPassword().equals(
			webclient.logic
	        .get()
	        .uri("/encriptBySha1/" + passowrd)
	        .retrieve()
	        .bodyToMono(String.class)
	        .block() ) ).toList().isEmpty(); 
  }

  private credit findByNumberCreditCard(String number, String passowrd) { 
	return repository.findAll().stream().filter( c ->  c.getCreditcard().getNumberCard().equals(number) && c.getCreditcard().getPassword().equals(  
  	        webclient.logic
	        .get()
	        .uri("/encriptBySha1/" + passowrd)
	        .retrieve()
	        .bodyToMono(String.class)
	        .block() ) ).toList().get(0);  
  }

  private String addOperations(
    credit creditObj,
    String type,
    operation obj,
    Double amount
  ) {
    double base = creditObj.getBaseCreditLimit();
    double baseB = creditObj.getAmount();
    String msg = "Monto pagado.";

    if (type.equals("consumo")) {
      if (amount > baseB) msg = "El monto sobre pasa el credito base."; else {
        creditObj.setAmount(baseB - amount);
        creditObj.getOperation().add(obj);
        repository.save(creditObj);
        msg = "Consumo añadido.";
      }
    } else {
      creditObj.setAmount(base);
      obj.setAmount(0);
      creditObj.getOperation().add(obj);
      repository.save(creditObj);
    }

    return msg;
  }

  public Mono<Object> save(String idAccount, double baseCreditLimit, String password) {
    String msg = "Credito registrado.";
    if (verifyCustomer(idAccount)) {
      credit obj = new credit(idAccount, baseCreditLimit, password);
      obj.setTypeAccount(customerFind(idAccount).getType());

      if (customerFind(idAccount).getType().equals("personal")) {
        if ( !repository.existsByIdCustomer(idAccount) ) repository.save(obj); else msg =
          "Usted ya no puede tener mas creditos.";
      } else repository.save(obj);
    } else msg = "Cliente no registrado";
    return Mono.just(new message(msg));
  }

  public Mono<Object> saveOperations(
    String numberCard,
    String password,
    Double amount,
    String type
  ) {
    String msg = "Operacion realizada.";

    if (existsByNumberCreditCard(numberCard, password)) {
      if (type.equals("consumo") || type.equals("pago")) msg =
        addOperations(
          findByNumberCreditCard(numberCard, password),
          type,
          new operation(amount, type),
          amount
        ); else msg = "Operacion incorrecta.";
    } else msg = "Datos incorrectos.";

    return Mono.just(new message(msg));
  }

  public Flux<credit> getByCustomer(String id) {  
	return Flux.fromIterable(repository.findAll().stream().filter( c -> c.getIdCustomer().equals(id) ).toList());
  }
  
  public Flux<credit> getAll(){
	return Flux.fromIterable( repository.findAll() );
  }
  
  public Mono<Boolean> _verifyCustomer(String id){
	return Mono.just(!repository.findAll().stream().filter( c  -> c.getIdCustomer().equals(id) ).toList().isEmpty());
  }
}
