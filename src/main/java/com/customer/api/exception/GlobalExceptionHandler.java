package com.customer.api.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.customer.api.dto.ErrorResponse;
import com.customer.api.dto.ErrorsResponse;
import com.customer.api.dto.ListErrorsResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ InvalidParameterException.class })
	public ResponseEntity<ErrorResponse> handleInvalidParameterException(InvalidParameterException exception,
			HttpServletRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
				exception.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler({ CustomerNotFoundException.class })
	public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException exception,
			HttpServletRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(),
				exception.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorsResponse> handleValidationErrors(MethodArgumentNotValidException exception,
			HttpServletRequest request) {

		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getFieldErrors()
				.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

		ErrorsResponse errorsResponse = new ErrorsResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), errors,
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsResponse);
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ListErrorsResponse> handleValidationErrors(HandlerMethodValidationException exception,
			HttpServletRequest request) {

		List<Map<String, String>> errors = new ArrayList<Map<String, String>>();
		exception.getBeanResults().forEach(beanResult -> {
			Map<String, String> error = new HashMap<>();

			beanResult.getFieldErrors().forEach(fieldError -> {
				error.put(fieldError.getField(), fieldError.getDefaultMessage());
			});

			errors.add(error);
		});

		ListErrorsResponse errorsResponse = new ListErrorsResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
				errors, request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpServletRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
				"Invalid request body!", request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
