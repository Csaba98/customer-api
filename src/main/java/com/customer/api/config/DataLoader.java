package com.customer.api.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.customer.api.entity.Customer;
import com.customer.api.service.CustomerService;
import com.customer.api.util.CommonUtil;

@Component
public class DataLoader {

	@Autowired
	private CustomerService customerService;

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		String[] firstNames = { "Csaba", "Gábor", "Eszter", "Péter", "Dénes", "Géza", "Barbara", "József", "András",
				"Bence", "Márton", "Marcell", "Jácint", "Jázmin", "Dávid", "Iván", "Ábel", "Ferenc", "Sándor",
				"Balázs" };
		String[] lastNames = { "Horváth", "Kiss", "Bodnár", "Gémes", "Jakab", "Tóth", "Sipos", "Faragó", "Sárközi",
				"Vörös", "Szűcs", "Nagy", "Csontos", "Orosz", "Vasteleki" };
		String[] phonePrefixes = { "+3620", "+3630", "+3631", "+3650", "+3670" };

		Random rand = new Random();
		List<Customer> customers = new ArrayList<Customer>();

		for (int i = 1; i <= 200; i++) {
			Customer customer = new Customer();

			customer.setFirstname(CommonUtil.getRandomElement(firstNames));
			customer.setLastname(CommonUtil.getRandomElement(lastNames));
			customer.setAge(Integer.valueOf(rand.nextInt(1, 127)).shortValue());
			customer.setPhone(
					String.format("%s%s", CommonUtil.getRandomElement(phonePrefixes), rand.nextInt(1000000, 9999999)));
			customer.setUsername((String.format("%s.%s", customer.getFirstname().toLowerCase(),
					customer.getLastname().toLowerCase())));
			customer.setEmail(String.format("%s@exampleemail.com", customer.getUsername()));
			customer.setPassword(String.format("PwdAbcd%s", i + 200));
			
			customers.add(customer);
		}
		
		customerService.addCustomers(customers);
	}
}
