package com.customer.api.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.customer.api.entity.Customer;
import com.customer.api.util.CommonUtil;

@DataJpaTest
public class CustomerRepositoryTests {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	@Order(1)
	public void addCustomerTest() {
		Customer insertedCustomer = customerRepository.saveAndFlush(CommonUtil.customerForTest(0));

		entityManager.refresh(insertedCustomer);

		assertNotNull(insertedCustomer.getId());
		assertNotEquals(CommonUtil.getPassword1Str(), insertedCustomer.getPassword());
	}

	@Test
	@Order(2)
	public void addCustomersTest() {
		List<Customer> insertedCustomers = new ArrayList<Customer>();

		for (int i = 0; i < 3; i++) {
			insertedCustomers.add(CommonUtil.customerForTest(0));
		}

		customerRepository.saveAllAndFlush(insertedCustomers);

		insertedCustomers.forEach(insertedCustomer -> {
			entityManager.refresh(insertedCustomer);

			assertNotNull(insertedCustomer.getId());
			assertNotEquals(CommonUtil.getPassword1Str(), insertedCustomer.getPassword());
		});
	}

	@Test
	@Order(3)
	public void getCustomerByIdTest() {
		Optional<Customer> customer = customerRepository.findById(2000L);
		assertTrue(customer.isEmpty());

		customer = customerRepository.findById(1L);
		assertTrue(customer.isPresent());
	}

	@Test
	@Order(4)
	public void getAllCustomersTest() {
		List<Customer> customers = customerRepository.findAll();
		assertTrue(customers.size() > 0);
	}

	@Test
	@Order(5)
	public void getCustomersCountTest() {
		Long recordCount = customerRepository.getRecordCount();
		assertTrue(recordCount > 0L);
	}

	@Test
	@Order(6)
	public void getCustomersAvgAgeTest() {
		Float avgage = customerRepository.getAvgAge();
		assertTrue(avgage > 0F);
	}

	@Test
	@Order(7)
	public void updateCustomerTest() {
		assertDoesNotThrow(() -> {
			Customer customer = customerRepository.findById(1L).get();
			String oldFirstName = customer.getFirstname();
			String oldLastName = customer.getLastname();

			customer.setFirstname("UpdatedFirstName");
			customer.setLastname("UpdatedLastName");

			customerRepository.saveAndFlush(customer);

			assertNotEquals(customer.getFirstname(), oldFirstName);
			assertNotEquals(customer.getLastname(), oldLastName);
		});
	}

	@Test
	@Order(8)
	public void updateCustomersTest() {
		assertDoesNotThrow(() -> {
			LongAdder longAdder = new LongAdder();
			List<Customer> customers = new ArrayList<Customer>();

			for (int i = 0; i < 3; i++) {
				longAdder.increment();

				Customer customer = customerRepository.findById(longAdder.longValue()).get();
				customer.setFirstname("UpdatedFirstName");
				customer.setLastname("UpdatedLastName");

				customers.add(customer);
			}

			customerRepository.saveAllAndFlush(customers);

			customers.forEach(customer -> {
				assertEquals(customer.getFirstname(), "UpdatedFirstName");
				assertEquals(customer.getLastname(), "UpdatedLastName");
			});
		});
	}

	@Test
	@Order(9)
	public void deleteCustomerByIdTest() {
		assertDoesNotThrow(() -> {
			customerRepository.deleteById(10000L);
			customerRepository.deleteById(10L);
		});
	}

	@Test
	@Order(10)
	public void deleteAllCustomersTest() {
		customerRepository.deleteAll();
		assertTrue(customerRepository.count() == 0L);
	}

}
