package com.customer.api.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.customer.api.entity.Customer;
import com.customer.api.exception.CustomerNotFoundException;
import com.customer.api.repository.CustomerRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CustomerServiceTests {

	private final String PASSWORD_1 = "Abssdsd1232*-";
	private final String PASSWORD_2 = "Abcdef16699*-";

	@Autowired
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	private Customer customerForTest(int i) {
		Customer customer = new Customer();

		customer.setFirstname("TestFirstName");
		customer.setLastname("TestLastName");
		customer.setAge((short) 20);
		customer.setEmail("tstemail@hotmail.com");
		customer.setPhone("+36201234567");

		String password = i == 0 ? PASSWORD_1 : PASSWORD_2;
		customer.setPassword(password);

		customer.setUsername("test.username");

		return customer;
	}

	@Test
	@Order(1)
	public void getCustomerByIdTest() {
		assertThrows(CustomerNotFoundException.class, () -> {
			Long id = 10000L;
			customerService.getCustomerById(id);
		});

		assertDoesNotThrow(() -> {
			Long id = 1L;
			customerService.getCustomerById(id);
		});
	}

	@Test
	@Order(2)
	public void getAllCustomersTest() {
		List<Customer> customers = customerService.getAllCustomers();

		assertTrue(customers.size() > 0);
	}

	@Test
	@Order(3)
	public void getCustomersCountTest() {
		Map<String, Long> response = customerService.getCustomersCount();

		assertTrue(response.get("count") > 0);
	}

	@Test
	@Order(4)
	public void getCustomersAgeRangeTest() {
		List<Customer> customer = customerService.getCustomersAgeRange((short) 1000, (short) 2000);
		assertTrue(customer.size() == 0);

		customer = customerService.getCustomersAgeRange((short) 10, (short) 100);
		assertTrue(customer.size() > 0);
	}

	@Test
	@Order(5)
	public void getCustomersAvgAgeTest() {
		Map<String, Float> response = customerService.getCustomersAvgAge();
		assertTrue(Objects.nonNull((response.get("avgage"))));
	}

	@Test
	@Order(6)
	public void addCustomerTest() {
		Customer customer = customerForTest(0);

		customerService.addCustomer(customer);

		assertNotNull(customer.getId());
		assertNotEquals(PASSWORD_1, customer.getPassword());
	}

	@Test
	@Order(7)
	public void addCustomersTest() {
		List<Customer> customers = new ArrayList<Customer>();

		for (int i = 0; i < 3; i++) {
			Customer customer = customerForTest(i);
			customers.add(customer);
		}

		customerService.addCustomers(customers);

		customers.forEach(customer -> assertNotNull(customer.getId()));

		assertNotEquals(PASSWORD_1, customers.get(0).getPassword());
		assertNotEquals(PASSWORD_2, customers.get(1).getPassword());
		assertNotEquals(PASSWORD_2, customers.get(2).getPassword());
	}

	@Test
	@Order(8)
	public void updateCustomerTest() {
		assertDoesNotThrow(() -> {
			Long id = 6L;
			Customer beforeCustomer = customerService.getCustomerById(id);

			beforeCustomer.setFirstname("UpdatedFistName");
			beforeCustomer.setLastname("UpdatedLastName");

			Customer afterCustomer = customerService.updateCustomer(customerService.getCustomerById(id));

			assertNotEquals(beforeCustomer.getFirstname(), afterCustomer.getFirstname());
			assertNotEquals(beforeCustomer.getLastname(), afterCustomer.getLastname());
		});
	}

	@Test
	@Order(9)
	public void updateCustomersTest() {
		assertDoesNotThrow(() -> {
			List<Customer> customers = new ArrayList<Customer>();
			LongAdder longAdder = new LongAdder();

			for (int i = 0; i < 3; i++) {
				longAdder.increment();

				Customer customer = customerService.getCustomerById(longAdder.longValue());
				customer.setPassword(i == 0 ? PASSWORD_1 : PASSWORD_2);

				customers.add(customer);
			}

			customers = customerService.updateCustomers(customers);

			assertTrue(customers.size() > 0);

			for (int i = 0; i < 3; i++) {
				assertNotEquals(i == 0 ? PASSWORD_1 : PASSWORD_2, customers.get(i).getPassword());
			}
		});
	}

	@Test
	@Order(10)
	public void deleteCustomerByIdTest() {
		assertThrows(CustomerNotFoundException.class, () -> {
			Long id = 100000L;
			customerService.deleteCustomerById(id);
		});

		assertDoesNotThrow(() -> {
			Long id = 6L;
			customerService.deleteCustomerById(id);
		});
	}

	@Test
	@Order(11)
	public void deleteAllCustomersTest() {
		assertDoesNotThrow(() -> customerService.deleteAllCustomers());
		assertTrue(customerService.getAllCustomers().size() == 0);
	}
}
