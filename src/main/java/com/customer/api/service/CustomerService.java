package com.customer.api.service;

import java.util.List;
import java.util.Map;

import com.customer.api.entity.Customer;

public interface CustomerService {

	public Customer addCustomer(Customer customer);
	
	public List<Customer> addCustomers(List<Customer> customer);

	public List<Customer> getAllCustomers();

	public Map<String, Long> getCustomersCount();

	public Customer getCustomerById(Long id);

	public Customer updateCustomer(Customer customer);
	
	public List<Customer> updateCustomers(List<Customer> customer);

	public String deleteAllCustomers();

	public String deleteCustomerById(Long id);

	public Map<String, Float> getCustomersAvgAge();

	public List<Customer> getCustomersAgeRange(Short minAge, Short maxAge);
}
