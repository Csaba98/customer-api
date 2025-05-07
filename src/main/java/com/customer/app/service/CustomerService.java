package com.customer.app.service;

import java.util.List;
import java.util.Map;

import com.customer.app.entity.Customer;

public interface CustomerService {
	
	public Customer addCustomer(Customer customer);
	
	public List<Customer> getAllCustomers();
	
	public Map<String, Long> getCustomersCount();
	
	public Customer getCustomerById(Long id);
	
	public Customer updateCustomer(Long id, Customer customer);
	
	public String deleteAllCustomers();
	
	public String deleteCustomerById(Long id);
	
	public Map<String, Float> getCustomersAvgAge();
	
	public List<Customer> getCustomersAgeRange(Byte minAge, Byte maxAge);
}
