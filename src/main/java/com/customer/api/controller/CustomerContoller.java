package com.customer.api.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customer.api.entity.Customer;
import com.customer.api.exception.InvalidParameterException;
import com.customer.api.exception.InvalidRangeException;
import com.customer.api.service.CustomerServiceImpl;
import com.customer.api.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerContoller {

	Logger logger = LoggerFactory.getLogger(CustomerContoller.class);

	@Autowired
	private CustomerServiceImpl customerService;

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers(HttpServletRequest request) {
		CommonUtil.writeToLOG(HttpMethod.GET, request.getRequestURI());
		List<Customer> customers = customerService.getAllCustomers();

		return ResponseEntity.ok(customers);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(HttpServletRequest request, @PathVariable("id") String reqId) {
		Long id;
		CommonUtil.writeToLOG(HttpMethod.GET, request.getRequestURI());

		try {
			id = Long.valueOf(reqId);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("id");
		}

		Customer customer = customerService.getCustomerById(id);
		return ResponseEntity.ok(customer);
	}

	@GetMapping("/count")
	public ResponseEntity<Map<String, Long>> getCustomersCount(HttpServletRequest request) {
		CommonUtil.writeToLOG(HttpMethod.GET, request.getRequestURI());
		Map<String, Long> count = customerService.getCustomersCount();

		return ResponseEntity.ok(count);
	}

	@GetMapping("/avgage")
	public ResponseEntity<Map<String, Float>> getCustomersAvgAge(HttpServletRequest request) {
		CommonUtil.writeToLOG(HttpMethod.GET, request.getRequestURI());
		Map<String, Float> avgage = customerService.getCustomersAvgAge();

		return ResponseEntity.ok(avgage);
	}

	@GetMapping("/filterbyage")
	public ResponseEntity<List<Customer>> getCustomersAgeRange(HttpServletRequest request,
			@RequestParam("minAge") String reqMinAge, @RequestParam("maxAge") String reqMaxAge) {
		Short minAge, maxAge;
		CommonUtil.writeToLOG(HttpMethod.GET, request.getRequestURI(), request.getQueryString());

		try {
			minAge = Short.valueOf(reqMinAge);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("minAge");
		}

		try {
			maxAge = Short.valueOf(reqMaxAge);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("maxAge");
		}

		if (minAge > maxAge || maxAge < minAge) {
			throw new InvalidRangeException("minAge", "maxAge");
		}

		List<Customer> customers = customerService.getCustomersAgeRange(minAge, maxAge);
		return ResponseEntity.ok(customers);
	}

	@PostMapping
	public ResponseEntity<Customer> addCustomer(HttpServletRequest request, @RequestBody @Valid Customer customer) {
		CommonUtil.writeToLOG(HttpMethod.POST, request.getRequestURI());
		Customer dbCustomer = customerService.addCustomer(customer);

		return ResponseEntity.status(HttpStatus.CREATED).body(dbCustomer);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(HttpServletRequest request, @PathVariable("id") String reqId,
			@RequestBody @Valid Customer customer) {
		Long id;
		CommonUtil.writeToLOG(HttpMethod.PUT, request.getRequestURI());

		try {
			id = Long.valueOf(reqId);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("id");
		}

		Customer dbCustomer = customerService.updateCustomer(id, customer);

		return ResponseEntity.ok(dbCustomer);
	}

	@DeleteMapping
	public ResponseEntity<String> deleteAllCustomers(HttpServletRequest request) {
		CommonUtil.writeToLOG(HttpMethod.DELETE, request.getRequestURI());
		String msg = customerService.deleteAllCustomers();

		return ResponseEntity.ok(msg);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomerById(HttpServletRequest request, @PathVariable("id") String reqId) {
		Long id;
		CommonUtil.writeToLOG(HttpMethod.DELETE, request.getRequestURI());

		try {
			id = Long.valueOf(reqId);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("id");
		}

		String msg = customerService.deleteCustomerById(id);
		return ResponseEntity.ok(msg);
	}
}
