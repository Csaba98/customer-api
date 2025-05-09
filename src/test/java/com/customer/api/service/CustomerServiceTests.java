package com.customer.api.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.customer.api.entity.Customer;
import com.customer.api.exception.CustomerNotFoundException;
import com.customer.api.repository.CustomerRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

	@Autowired
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	private Customer customerForTest(Long id, int i) {
		Customer customer = new Customer();

		if (Objects.nonNull(id)) {
			customer.setId(id);
		}

		customer.setFirstname("TestFirstName");
		customer.setLastname("TestLastName");
		customer.setAge((short) 20);
		customer.setEmail("tstemail@hotmail.com");
		customer.setPhone("+36201234567");

		String password = i == 0 ? "Abssdsd1232*-" : "Abssdsd6699*-";
		customer.setPassword(password);

		customer.setUsername("test.username");

		return customer;
	}

	@Test
	public void addCustomerTest() {
		Customer customer = customerForTest(null, 0);

		customerService.addCustomer(customer);

		assertNotNull(customer.getId());
		assertNotEquals("Abssdsd1232*-", customer.getPassword());
	}

	@Test
	public void addCustomersTest() {
		List<Customer> customers = new ArrayList<Customer>();

		for (int i = 0; i < 3; i++) {
			Customer customer = customerForTest(null, i);
			customers.add(customer);
		}

		customerService.addCustomers(customers);

		customers.forEach(customer -> assertNotNull(customer.getId()));

		assertNotEquals("Abssdsd1232*-", customers.get(0).getPassword());
		assertNotEquals("Abssdsd6699*-", customers.get(1).getPassword());
		assertNotEquals("Abssdsd6699*-", customers.get(2).getPassword());
	}

	@Test
	public void getAllCustomersTest() {
		List<Customer> customers = customerService.getAllCustomers();

		assertTrue(customers.size() > 0);
	}

	@Test
	public void getCustomersCountTest() {
		Map<String, Long> response = customerService.getCustomersCount();

		assertTrue(response.get("count") > 0);
	}

	@Test
	public void getCustomerByIdTest() {
		Long id = 100000L;
		assertThrows(CustomerNotFoundException.class, () -> {
			customerService.getCustomerById(id);
		});
	}

	@Test
	public void updateCustomerTest() {
		Long id = 2L;

		Customer beforeCustomer = customerService.getCustomerById(id);
		Customer afterCustomer = customerService.updateCustomer(customerForTest(id, 0));

		assertNotEquals(beforeCustomer.getFirstname(), afterCustomer.getFirstname());
		assertNotEquals(beforeCustomer.getLastname(), afterCustomer.getLastname());
	}

	@Test
	public void updateCustomersTest() {
		List<Customer> customers = new ArrayList<Customer>();

		for (int i = 0; i < 3; i++) {
			Long id = Long.parseLong(String.valueOf(i + 1));

			Customer customer = customerForTest(id, i);
			customers.add(customer);
		}

		customers = customerService.updateCustomers(customers);

		assertNotEquals("Abssdsd1232*-", customers.get(0).getPassword());
		assertNotEquals("Abssdsd6699*-", customers.get(1).getPassword());
		assertNotEquals("Abssdsd6699*-", customers.get(2).getPassword());
	}

	@Test
	public void deleteAllCustomersTest() {
		String response = customerService.deleteAllCustomers();

		assertTrue(response.equals("Customers deleted successfully!"));
		assertTrue(customerService.getAllCustomers().size() == 0);
	}

	@Test
	public void deleteCustomerByIdTest() {
		assertThrows(CustomerNotFoundException.class, () -> {
			Long id = 100000L;
			customerService.deleteCustomerById(id);
		});

		Long id = 6L;
		String response = customerService.deleteCustomerById(id);
		assertTrue(response.equals("Customer deleted successfully!"));
	}

	@Test
	public void getCustomersAvgAgeTest() {
		Map<String, Float> response = customerService.getCustomersAvgAge();
		assertTrue(response.get("avgage") > 0);
	}

	@Test
	public void getCustomersAgeRangeTest() {
		List<Customer> customer = customerService.getCustomersAgeRange((short) 1000, (short) 2000);
		assertTrue(customer.size() == 0);

		customer = customerService.getCustomersAgeRange((short) 10, (short) 100);
		assertTrue(customer.size() > 0);
	}
}
