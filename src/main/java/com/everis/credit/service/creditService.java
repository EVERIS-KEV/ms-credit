package com.everis.credit.service;

import com.everis.credit.dto.customer;
import com.everis.credit.dto.message;
import com.everis.credit.dto.operation;
import com.everis.credit.logic.myFunctions;
import com.everis.credit.model.credit;
import com.everis.credit.repository.creditRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
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

  private Boolean existsByNumberCreditCard(String number, String passowrd) {
    List<credit> list = repository.findAll();

    for (int i = 0; i < list.size(); i++) if (
      list.get(i).getCreditcard().getNumberCard().equals(number) &&
      list.get(i).getCreditcard().getPassword().equals(myFunctions.encriptSHA1(passowrd))
    ) return true;

    return false;
  }

  private credit findByNumberCreditCard(String number, String passowrd) {
    List<credit> list = repository.findAll();

    for (int i = 0; i < list.size(); i++) if (
      list.get(i).getCreditcard().getNumberCard().equals(number) &&
      list.get(i).getCreditcard().getPassword().equals(myFunctions.encriptSHA1(passowrd))
    ) return list.get(i);

    return null;
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
        if (!repository.existsByIdAccount(idAccount)) repository.save(obj); else msg =
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
    List<credit> listB = new ArrayList<credit>();

    for (int i = 0; i < repository.findAll().size(); i++) if (
      repository.findAll().get(i).getIdAccount().equals(id)
    ) listB.add(repository.findAll().get(i));

    return Flux.fromIterable(listB);
  }
}
