package com.customer.api.controller;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.customer.api.entity.Customer;
import com.customer.api.service.CustomerService;
import com.customer.api.util.CommonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CustomersControllerTests {

	private static final String URI = "/api/customers";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CustomerService customerService;

	@Before(value = "")
	public void init() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
	}

	@Test
	@Order(1)
	@WithUserDetails("test")
	public void getAllCustomersTest() throws Exception {
		mockMvc.perform(get(URI)).andExpect(status().isOk());
	}

	@Test
	@Order(2)
	@WithUserDetails("test")
	public void getCustomerByIdTest() throws Exception {
		mockMvc.perform(get(URI + "/{id}", "param")).andExpect(status().isBadRequest());

		mockMvc.perform(get(URI + "/{id}", "1.16")).andExpect(status().isBadRequest());

		mockMvc.perform(get(URI + "/{id}", 10000L)).andExpect(status().isNotFound());

		mockMvc.perform(get(URI + "/{id}", 1L)).andExpect(status().isOk());
	}

	@Test
	@Order(3)
	@WithUserDetails("test")
	public void getCustomersCountTest() throws Exception {
		mockMvc.perform(get(URI + "/count")).andExpect(status().isOk()).andExpect(jsonPath("$.count", Matchers.not(0)));
	}

	@Test
	@Order(4)
	@WithUserDetails("test")
	public void getCustomersAgeRangeTest() throws Exception {
		mockMvc.perform(get(URI + "/filterbyage").queryParam("minAge", "badparam").queryParam("maxAge", "6"))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get(URI + "/filterbyage").queryParam("minAge", "6").queryParam("maxAge", "badparam"))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get(URI + "/filterbyage").queryParam("minAge", "1.1").queryParam("maxAge", "6"))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get(URI + "/filterbyage").queryParam("minAge", "6").queryParam("maxAge", "1.1"))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get(URI + "/filterbyage").queryParam("minAge", "10").queryParam("maxAge", "6"))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get(URI + "/filterbyage").queryParam("minAge", "6").queryParam("maxAge", "4"))
				.andExpect(status().isBadRequest());

		MvcResult result = mockMvc
				.perform(get(URI + "/filterbyage").queryParam("minAge", "10").queryParam("maxAge", "100"))
				.andExpect(status().isOk()).andReturn();

		String json = result.getResponse().getContentAsString();
		List<Customer> customers = objectMapper.readValue(json, new TypeReference<List<Customer>>() {
		});

		assertTrue(customers.size() > 0);
	}

	@Test
	@Order(5)
	@WithUserDetails("test")
	public void getCustomersAvgAgeTest() throws Exception {
		mockMvc.perform(get(URI + "/avgage")).andExpect(status().isOk())
				.andExpect(jsonPath("$.avgage", Matchers.notNullValue()));
	}

	@Test
	@Order(6)
	@WithUserDetails("test")
	public void addCustomersTest() throws Exception {
		List<Customer> customers = new ArrayList<Customer>();

		for (int i = 0; i < 3; i++) {
			Customer customer = CommonUtil.customerForTest(i);
			customers.add(customer);
		}

		MvcResult result = mockMvc
				.perform(post(URI).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
						.content(objectMapper.writeValueAsString(customers)))
				.andExpect(status().isCreated()).andReturn();

		String json = result.getResponse().getContentAsString();
		customers = objectMapper.readValue(json, new TypeReference<List<Customer>>() {
		});

		for (int i = 0; i < 3; i++) {
			assertNotNull(customers.get(i).getId());
			assertNotEquals(i == 0 ? CommonUtil.getPassword1Str() : CommonUtil.getPassword2Str(),
					customers.get(i).getPassword());
		}
	}

	@Test
	@Order(7)
	@WithUserDetails("test")
	public void updateCustomersTest() throws Exception {
		List<Customer> customers = new ArrayList<Customer>();
		LongAdder longAdder = new LongAdder();

		for (int i = 0; i < 3; i++) {
			longAdder.increment();

			Customer customer = customerService.getCustomerById(longAdder.longValue());
			customer.setPassword(i == 0 ? CommonUtil.getPassword1Str() : CommonUtil.getPassword2Str());

			customers.add(customer);
		}

		MvcResult result = mockMvc.perform(put(URI).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(customers))).andExpect(status().isOk()).andReturn();

		String json = result.getResponse().getContentAsString();
		customers = objectMapper.readValue(json, new TypeReference<List<Customer>>() {
		});

		assertTrue(customers.size() > 0);

		for (int i = 0; i < 3; i++) {
			assertNotEquals(i == 0 ? CommonUtil.getPassword1Str() : CommonUtil.getPassword2Str(),
					customers.get(i).getPassword());
		}
	}

	@Test
	@Order(8)
	@WithUserDetails("test")
	public void deleteCustomerByIdTest() throws Exception {
		mockMvc.perform(delete(URI + "/{id}", "badparam")).andExpect(status().isBadRequest());

		mockMvc.perform(delete(URI + "/{id}", "1.16")).andExpect(status().isBadRequest());

		mockMvc.perform(delete(URI + "/{id}", 100000L)).andExpect(status().isNotFound());

		mockMvc.perform(delete(URI + "/{id}", 6L)).andExpect(status().isOk());
	}

	@Test
	@Order(9)
	@WithUserDetails("test")
	public void deleteAllCustomersTest() throws Exception {
		mockMvc.perform(delete(URI)).andExpect(status().isOk());
	}

}
