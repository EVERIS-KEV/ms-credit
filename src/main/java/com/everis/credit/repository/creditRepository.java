package com.everis.credit.repository;

import com.everis.credit.model.credit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface creditRepository extends MongoRepository<credit, String> {
	boolean existsByIdAccount(String id);
}
