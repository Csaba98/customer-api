package com.customer.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.customer.api.entity.Customer;
import com.customer.api.repository.base.BaseRepository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {

	@Query(value = "select avg(age) from Customer")
	Float getAvgAge();

	@Query(value = "select count(id) from Customer")
	Long getRecordCount();

}