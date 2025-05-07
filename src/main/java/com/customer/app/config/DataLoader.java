package com.customer.app.config;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.customer.app.entity.Customer;
import com.customer.app.repository.CustomerRepository;

import com.customer.app.util.CommonUtil;

@Component
public class DataLoader {

	@Autowired
	private CustomerRepository customerRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		String[] firstNames = { "Csaba", "Gábor", "Eszter", "Péter", "Dénes", "Géza", "Barbara", "József", "András",
				"Bence", "Márton", "Marcell", "Jácint", "Jázmin", "Dávid", "Iván", "Ábel", "Ferenc", "Sándor",
				"Balázs" };
		String[] lastNames = { "Horváth", "Kiss", "Bodnár", "Gémes", "Jakab", "Tóth", "Sipos", "Faragó", "Sárközi",
				"Vörös", "Szűcs", "Nagy", "Csontos", "Orosz", "Vasteleki" };
		String[] phonePrefixes = { "+3620", "+3630", "+3631", "+3650", "+3670" };

		Random rand = new Random();

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

			customerRepository.saveAndFlush(customer);
		}
	}
}
