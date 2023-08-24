package com.mathcsant.course.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id: " + id);
	}
	
	public ResourceNotFoundException(String name) {
		super("Resource not found. Name: " + name);
	}

}
