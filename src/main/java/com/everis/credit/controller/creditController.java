package com.everis.credit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.everis.credit.dto.AuthFrom;
import com.everis.credit.dto.creditFrom;
import com.everis.credit.dto.message;
import com.everis.credit.model.credit;
import com.everis.credit.service.creditService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping()
public class creditController {
	@Autowired
	creditService service;

	@GetMapping("/")
	public Flux<credit> findAll() {
		return service.getAll();
	}

	@GetMapping("/byCustomer/{id}")
	public Flux<credit> getById(@PathVariable("id") String id) {
		return service.getByCustomer(id);
	}

	@GetMapping("/byNumberCard/{number}")
	public Mono<Object> byNumberCard(@PathVariable("number") String number) {
		return service.byNumberCard(number);
	}

	@GetMapping("/verifyNumber/{number}")
	public Mono<Boolean> verifyNumber(@PathVariable("number") String number) {
		return service._verifyNumber(number);
	}

	@GetMapping("/verifyCustomer/{id}")
	public Mono<Boolean> verifyCustomer(@PathVariable("id") String id) {
		return service._verifyCustomer(id);
	}

	@PostMapping("/save")
	public Mono<Object> created(@RequestBody @Valid creditFrom model, BindingResult bindinResult) {
		String msg = "";

		if (bindinResult.hasErrors()) {
			for (int i = 0; i < bindinResult.getAllErrors().size(); i++) {
				msg = bindinResult.getAllErrors().get(0).getDefaultMessage();
			}
			return Mono.just(new message(msg));
		}

		return service.save(model.getIdCustomer(), model.getBaseCreditLimit(), model.getPassword());
	}

	@PostMapping("/operations")
	public Mono<Object> consumptions(@RequestBody @Valid AuthFrom model, BindingResult bindinResult) {
		String msg = "";

		if (bindinResult.hasErrors()) {
			for (int i = 0; i < bindinResult.getAllErrors().size(); i++) {
				msg = bindinResult.getAllErrors().get(0).getDefaultMessage();
			}
			return Mono.just(new message(msg));
		}

		return service.saveOperations(model.getCreditCardNumber(), model.getPassword(), model.getAmount(),
				model.getType());
	}
}
