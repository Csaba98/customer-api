package com.customer.api.converter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class PasswordConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return dbData;
	}

}
