package com.both.gamified_habit_tracker_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
	}

	// Handle validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setTitle("Validation Failed");

		// Collect field validation errors
		Map<String, String> errors = new HashMap<>();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		// Add errors to ProblemDetail as extra properties
		problemDetail.setProperty("errors", errors);
		return problemDetail;
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ProblemDetail handleMethodValidationException(HandlerMethodValidationException e) {
		Map<String, String> errors = new HashMap<>();

		// Loop through each invalid parameter validation result
		e.getParameterValidationResults().forEach(parameterError -> {
			String paramName = parameterError.getMethodParameter().getParameterName(); // Get parameter name

			// Loop through each validation error message for this parameter
			for (var messageError : parameterError.getResolvableErrors()) {
				errors.put(paramName, messageError.getDefaultMessage()); // Store error message
			}
		});

		// Create structured ProblemDetail response
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setTitle("Method Parameter Validation Failed");
		problemDetail.setProperties(Map.of(
						"timestamp", LocalDateTime.now(),
						"errors", errors // Attach validation errors
		));


		return problemDetail;
	}
}
