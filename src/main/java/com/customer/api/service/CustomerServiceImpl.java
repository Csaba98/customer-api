package com.customer.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.customer.api.entity.Customer;
import com.customer.api.exception.CustomerNotFoundException;
import com.customer.api.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private static final String CUSTOMER_NOT_FOUND = "Customer with given id not found!";
	private static final String CUSTOMERS_DELETED = "Customers deleted successfully!";
	private static final String CUSTOMER_DELETED = "Customer deleted successfully!";
	
	@Autowired
	private CustomerRepository customerRepository;

	private Customer getCustomer(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);

		if (customer.isPresent()) {
			return customer.get();
		}

		throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND);
	}

	public Customer addCustomer(Customer customer) {
		customer.setId(null);	
		
		Customer dbCustomer = customerRepository.saveAndFlush(customer);
		customerRepository.refresh(dbCustomer);
		return dbCustomer;
	}

	public List<Customer> addCustomers(List<Customer> customers) {
		customers.forEach(customer -> customer.setId(null));
		
		List<Customer> dbCustomers = customerRepository.saveAllAndFlush(customers);
		
		dbCustomers.forEach(dbCustomer -> customerRepository.refresh(dbCustomer));
		return dbCustomers;
	}	

	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return customers;
	}

	public Map<String, Long> getCustomersCount() {
		Long count = customerRepository.getRecordCount();
		Map<String, Long> response = new HashMap<String, Long>();
		response.put("count", count);

		return response;
	}

	public Customer getCustomerById(Long id) {
		Customer dbCustomer = getCustomer(id);

		return dbCustomer;
	}

	public Customer updateCustomer(Customer customer) {
		Customer dbCustomer = customerRepository.saveAndFlush(customer);
		customerRepository.refresh(dbCustomer);
		return dbCustomer;
	}
	
	public List<Customer> updateCustomers(List<Customer> customers) {
		List<Customer> dbCustomers = customerRepository.saveAllAndFlush(customers);
		
		dbCustomers.forEach(dbCustomer -> customerRepository.refresh(dbCustomer));
		return dbCustomers;
	}		

	public String deleteAllCustomers() {
		customerRepository.deleteAll();
		return CUSTOMERS_DELETED;
	}

	public String deleteCustomerById(Long id) {
		Customer dbCustomer = getCustomer(id);
		customerRepository.deleteById(dbCustomer.getId());

		return CUSTOMER_DELETED;
	}

	public Map<String, Float> getCustomersAvgAge() {
		Float avgage = customerRepository.getAvgAge();
		Map<String, Float> response = new HashMap<String, Float>();
		response.put("avgage", avgage);

		return response;
	}

	public List<Customer> getCustomersAgeRange(Short minAge, Short maxAge) {
		List<Customer> filteredCustomers = customerRepository.findAll().stream()
				.filter(c -> (minAge <= c.getAge()) && (c.getAge() <= maxAge)).collect(Collectors.toList());

		return filteredCustomers;
	}

}
