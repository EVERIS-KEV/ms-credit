package com.everis.credit.repository;

import com.everis.credit.model.Credit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends MongoRepository<Credit, String> {
	boolean existsByIdCustomer(String id); 
}
