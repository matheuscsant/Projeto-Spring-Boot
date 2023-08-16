package com.mathcsant.course.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mathcsant.course.services.exceptions.DataBaseException;
import com.mathcsant.course.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {

		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> dataBaseException(DataBaseException e, HttpServletRequest request) {

//		String error = "DataBase error";
//		HttpStatus status = HttpStatus.BAD_REQUEST;
//		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
//				request.getRequestURI());
//		return ResponseEntity.status(status).body(err);
		// Meio alternativo de retornar o erro
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardError(Instant.now(),
				HttpStatus.BAD_REQUEST.value(), "DataBase error", e.getMessage(), request.getRequestURI()));

	}

}