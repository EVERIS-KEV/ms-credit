package com.everis.credit.service;

import com.everis.credit.dto.customer;
import com.everis.credit.dto.message;
import com.everis.credit.model.credit;
import com.everis.credit.repository.creditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class creditService {
  @Autowired
  creditRepository repository;

  WebClient webclient = WebClient.create("http://localhost:8081");

  private Boolean verifyCustomer(String id) {
    return webclient
      .get()
      .uri("/api/customers/verifyId/{id}", id)
      .retrieve()
      .bodyToMono(Boolean.class)
      .block();
  }

  private customer customerFind(String id) {
    return webclient
      .get()
      .uri("/api/customers/{id}", id)
      .retrieve()
      .bodyToMono(customer.class)
      .block();
  }

  public Mono<Object> save(String idAccount, double baseCreditLimit, String password) {
    String msg = "Credito registrado.";
    if (verifyCustomer(idAccount)) {
      credit obj = new credit(idAccount, baseCreditLimit, password);
      obj.setTypeAccount(customerFind(idAccount).getType());
      
      if (customerFind(idAccount).getType().equals("personal")) {
        if (!repository.existsByIdAccount(idAccount)) {
          repository.save(obj);
        } else msg = "Usted ya no puede tener mas creditos.";
      }else repository.save(obj);
      
    } else msg = "Cliente no registrado";
    return Mono.just(new message(msg));
  }
}
