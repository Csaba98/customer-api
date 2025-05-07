package com.customer.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.customer.app.entity.Customer;
import com.customer.app.exception.CustomerAlreadyExistsException;
import com.customer.app.exception.CustomerNotFoundException;
import com.customer.app.repository.CustomerRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private static final String CUSTOMER_NOT_FOUND = "Customer with given id not found!";
	private static final String CUSTOMER_EXISTS = "Customer with given id already exists!";
	private static final String CUSTOMERS_DELETED = "Customers deleted successfully!";
	private static final String CUSTOMER_DELETED = "Customer deleted successfully!";

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private EntityManager entityManager;

	private Customer getCustomer(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);

		if (customer.isPresent()) {
			return customer.get();
		}

		throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND);
	}

	public Customer addCustomer(Customer customer) {
		if (Objects.nonNull(customer.getId())) {
			if (Objects.nonNull(getCustomer(customer.getId()))) {
				throw new CustomerAlreadyExistsException(CUSTOMER_EXISTS);
			}
		}

		Customer dbCustomer = customerRepository.saveAndFlush(customer);
		entityManager.refresh(dbCustomer);
		return dbCustomer;
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

	public Customer updateCustomer(Long id, Customer customer) {
		Customer dbCustomer = getCustomer(id);

		dbCustomer.setFirstname(customer.getFirstname());
		dbCustomer.setLastname(customer.getLastname());
		dbCustomer.setAge(customer.getAge());
		dbCustomer.setEmail(customer.getEmail());
		dbCustomer.setPhone(customer.getPhone());
		dbCustomer.setUsername(customer.getUsername());
		dbCustomer.setPassword(customer.getPassword());

		dbCustomer = customerRepository.saveAndFlush(dbCustomer);
		entityManager.refresh(dbCustomer);
		return dbCustomer;
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

	public List<Customer> getCustomersAgeRange(Byte minAge, Byte maxAge) {
		List<Customer> filteredCustomers = customerRepository.findAll().stream()
				.filter(c -> (minAge <= c.getAge()) && (c.getAge() <= maxAge)).collect(Collectors.toList());

		return filteredCustomers;
	}

}
