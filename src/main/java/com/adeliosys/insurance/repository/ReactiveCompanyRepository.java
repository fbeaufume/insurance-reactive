package com.adeliosys.insurance.repository;

import com.adeliosys.insurance.model.Company;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveCompanyRepository extends ReactiveMongoRepository<Company, String> {}
