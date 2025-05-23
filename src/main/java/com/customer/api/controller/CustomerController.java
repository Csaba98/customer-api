package com.customer.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.api.dto.OnCreate;
import com.customer.api.dto.OnUpdate;
import com.customer.api.entity.Customer;
import com.customer.api.service.CustomerService;
import com.customer.api.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;
	
	@PostMapping
	public ResponseEntity<Customer> addCustomer(HttpServletRequest request,
			@RequestBody @Validated(OnCreate.class) Customer customer) {
		CommonUtil.writeToLOG(HttpMethod.POST, request.getRequestURI());
		Customer dbCustomer = customerService.addCustomer(customer);

		return ResponseEntity.status(HttpStatus.CREATED).body(dbCustomer);
	}
	
	@PutMapping
	public ResponseEntity<Customer> updateCustomer(HttpServletRequest request,
			@RequestBody @Validated(OnUpdate.class) Customer customer) {
		CommonUtil.writeToLOG(HttpMethod.PUT, request.getRequestURI());
		Customer dbCustomer = customerService.updateCustomer(customer);

		return ResponseEntity.ok(dbCustomer);
	}	

}
