package com.codna.apis.msystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.codna.apis.msystem.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e){
		
		ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("You are doing an operation on null value.").build();
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<?> handleMissingParameterException(Exception e){
		
		ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value())
					.message(e.getMessage()).build();
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
