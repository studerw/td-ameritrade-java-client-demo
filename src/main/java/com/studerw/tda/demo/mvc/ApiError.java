package com.studerw.tda.demo.mvc;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * API Error - borrowed from https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 */
public class ApiError {

	private HttpStatus status;

	private String message;

	private List<String> errors;

	private ZonedDateTime zonedDateTime;

	public ApiError(HttpStatus status, String message, List<String> errors) {
		this.status = status;
		this.message = message;
		this.errors = errors;
		this.zonedDateTime = ZonedDateTime.now();
	}

	public ApiError(HttpStatus status, String message, String error) {
		this.status = status;
		this.message = message;
		this.errors = Collections.singletonList(error);
		this.zonedDateTime = ZonedDateTime.now();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public ZonedDateTime getZonedDateTime() {
		return zonedDateTime;
	}

	public void setZonedDateTime(ZonedDateTime zonedDateTime) {
		this.zonedDateTime = zonedDateTime;
	}
}