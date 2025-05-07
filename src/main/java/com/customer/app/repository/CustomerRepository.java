package com.customer.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.customer.app.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value = "select avg(age) from Customer")
	Float getAvgAge();
	
	@Query(value = "select count(id) from Customer")
	Long getRecordCount();

}