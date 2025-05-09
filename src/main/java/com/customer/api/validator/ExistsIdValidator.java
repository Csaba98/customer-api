package com.customer.api.validator;

import java.util.Objects;
import java.util.Optional;

import com.customer.api.annotation.ExistsId;
import com.customer.api.config.ContextProvider;
import com.customer.api.entity.Customer;
import com.customer.api.repository.CustomerRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Long> {
	
	private CustomerRepository customerRepository;
	
    @Override
    public void initialize(ExistsId constraintAnnotation) {
         this.customerRepository = (CustomerRepository) ContextProvider.getBean(CustomerRepository.class);
    }

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if(Objects.isNull(value)) {
			return false;
		}
		
		Optional<Customer> dbCustomer = customerRepository.findById(value);
		return dbCustomer.isPresent();
	}

}
