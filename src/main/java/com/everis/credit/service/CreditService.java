package com.everis.credit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.everis.credit.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.everis.credit.dto.Customer;
import com.everis.credit.dto.Message;
import com.everis.credit.model.Credit;
import com.everis.credit.model.CreditCard;
import com.everis.credit.model.Operation;
import com.everis.credit.repository.CreditRepository;
import com.everis.credit.webclient.Webclient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class CreditService {
	@Autowired
	CreditRepository repository; //

	private Boolean verifyCustomer(String id) {
		return Webclient.customer.get().uri("/verifyId/{id}", id).retrieve().bodyToMono(Boolean.class).block();
	}

	private Customer customerFind(String id) {
		return Webclient.customer.get().uri("/{id}", id).retrieve().bodyToMono(Customer.class).block();
	}

	private Boolean existsByNumberCreditCard(String number, String passowrd) {
		boolean x = false;
		String code = Webclient.logic.get().uri("/encriptBySha1/" + passowrd).retrieve().bodyToMono(String.class)
				.block();

		for (int i = 0; i < repository.findAll().size(); i++) {
			CreditCard card = repository.findAll().get(i).getCreditcard();
			if (card.getNumberCard().equals(number) && card.getPassword().equals(code)) {
				x = true;
			}
		}

		return x;
	}

	private Credit findByNumberCreditCard(String number, String passowrd) {
		String code = Webclient.logic.get().uri("/encriptBySha1/" + passowrd).retrieve().bodyToMono(String.class)
				.block();
		for (int i = 0; i < repository.findAll().size(); i++) {
			if (repository.findAll().get(i).getCreditcard().getNumberCard().equals(number)
					&& repository.findAll().get(i).getCreditcard().getPassword().equals(code)) {
				return repository.findAll().get(i);
			}
		}
		return null;

	}

	private String addOperations(Credit creditObj, String type, Operation obj, Double amount) {
		double base = creditObj.getBaseCreditLimit();
		double baseB = creditObj.getAmount();
		String msg = "Monto pagado.";

		if (type.equals("consumo")) {
			if (amount > baseB) {
				msg = "El monto sobre pasa el credito base.";
			} else {
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
		String msg = Constants.Messages.REGISTERED_CREDIT;
		if (Boolean.TRUE.equals(verifyCustomer(idAccount))) {
			Credit obj = new Credit(idAccount, baseCreditLimit, password);
			obj.setTypeAccount(customerFind(idAccount).getType());

			if (!customerFind(idAccount).getType().equals("personal") || !repository.existsByIdCustomer(idAccount)) {
				repository.save(obj);
			} else {
				msg = Constants.Messages.CLIENT_NO_MORE_CREDITS;
			}
		} else {
			msg = Constants.Messages.CLIENT_NOT_REGISTERED;
		}
		return Mono.just(new Message(msg));
	}

	public Mono<Object> saveOperations(String numberCard, String password, Double amount, String type) {
		String msg;

		if (Boolean.TRUE.equals(existsByNumberCreditCard(numberCard, password))) {
			if (type.equals("consumo") || type.equals("pago")) {
				msg = addOperations(Objects.requireNonNull(findByNumberCreditCard(numberCard, password)), type, new Operation(amount, type),
						amount);
			} else {
				msg = Constants.Messages.INCORRECT_OPERATION;
			}
		} else {
			msg = Constants.Messages.INCORRECT_DATA;
		}

		return Mono.just(new Message(msg));
	}

	public Flux<Credit> getByCustomer(String id) {

		List<Credit> lista = repository.findAll();
		List<Credit> listb = new ArrayList<>();

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getIdCustomer().equals(id)) {
				listb.add(lista.get(i));
			}
		}

		return Flux.fromIterable(listb);
	}

	public Flux<Credit> getAll() {
		return Flux.fromIterable(repository.findAll());
	}

	public Mono<Boolean> verifyCustomerId(String id) {

		for (int i = 0; i < repository.findAll().size(); i++) {
			if (repository.findAll().get(i).getIdCustomer().equals(id)) {
				return Mono.just(true);
			}
		}

		return Mono.just(false);
	}

	public Mono<Boolean> verifyNumber(String number) {
		return Mono.just(!repository.findAll().stream().filter(c -> c.getCreditcard().getNumberCard().equals(number))
				.collect(Collectors.toList()).isEmpty());
	}

	public Mono<Object> byNumberCard(String number) {

		return Mono.just(repository.findAll().stream().filter(c -> c.getCreditcard().getNumberCard().equals(number))
				.findAny().get());
	}
}
