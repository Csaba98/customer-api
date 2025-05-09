package com.customer.api.controller;

import org.junit.jupiter.api.TestMethodOrder;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.customer.api.entity.Customer;
import com.customer.api.service.CustomerService;
import com.customer.api.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CustomerControllerTests {
	
	private static final String URI = "/api/customer";

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
	public void addCustomerTest() throws Exception {
		Customer customer = CommonUtil.customerForTest(0);

		mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(customer))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.password", Matchers.not(customer.getPassword())));
	}
	
	@Test
	@Order(1)
	@WithUserDetails("test")
	public void updateCustomerTest() throws Exception {
		Customer customer = customerService.getCustomerById(6L);
		customer.setFirstname("UpdatedFistName");
		customer.setLastname("UpdatedLastName");

		mockMvc.perform(put(URI).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(objectMapper.writeValueAsString(customer))).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", Matchers.equalTo(customer.getFirstname())))
				.andExpect(jsonPath("$.lastname", Matchers.equalTo(customer.getLastname())));
	}	

}
