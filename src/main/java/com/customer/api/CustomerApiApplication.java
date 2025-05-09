package com.customer.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.customer.api.repository.base.BaseRepositoryImpl;

@SpringBootApplication
@ComponentScan({"com.customer.api"})
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class CustomerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApiApplication.class, args);
	}

}
