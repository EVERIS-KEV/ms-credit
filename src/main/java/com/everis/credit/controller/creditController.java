package com.everis.credit.controller;

import com.everis.credit.dto.*;
import com.everis.credit.model.credit;
import com.everis.credit.service.creditService;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
@CrossOrigin(
  origins = "*",
  methods = {
    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE
  }
)
@RequestMapping()
public class creditController {
  @Autowired
  creditService service;
  
  @GetMapping("/")
  public Flux<credit> findAll(){
	  return service.getAll();
  }

  @GetMapping("/byCustomer/{id}")
  public Flux<credit> getById(@PathVariable("id") String id) {
    return service.getByCustomer(id);
  }
  
  @GetMapping("/verifyCustomer/{id}")
  public Mono<Boolean> verify(@PathVariable("id") String id) {
	  return service._verifyCustomer(id);
  }

  @PostMapping("/save")
  public Mono<Object> created(
    @RequestBody @Valid creditFrom model,
    BindingResult bindinResult
  ) {
    String msg = "";

    if (bindinResult.hasErrors()) {
      for (int i = 0; i < bindinResult.getAllErrors().size(); i++) msg =
        bindinResult.getAllErrors().get(0).getDefaultMessage();
      return Mono.just(new message(msg));
    }

    return service.save(
      model.getIdCustomer(),
      model.getBaseCreditLimit(),
      model.getPassword()
    );
  }

  @PostMapping("/consumptions")
  public Mono<Object> consumptions(
    @RequestBody @Valid AuthFrom model,
    BindingResult bindinResult
  ) {
    String msg = "";

    if (bindinResult.hasErrors()) {
      for (int i = 0; i < bindinResult.getAllErrors().size(); i++) msg =
        bindinResult.getAllErrors().get(0).getDefaultMessage();
      return Mono.just(new message(msg));
    }

    return service.saveOperations(
      model.getCreditCardNumber(),
      model.getPassword(),
      model.getAmount(),
      model.getType()
    );
  }
}
