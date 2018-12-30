package com.iw.pract1c.controllers;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.iw.pract1c.models.ExceptionResponse;
import com.iw.pract1c.models.PeliculaException;
import com.iw.pract1c.models.UserException;

@ControllerAdvice
public class ErrorHandlingController {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> generalException(Exception e) throws IOException{
		
		ExceptionResponse response = new ExceptionResponse();
		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setDescription(e.getMessage());
		return new ResponseEntity<ExceptionResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(PeliculaException.class)
	public ResponseEntity<ExceptionResponse> peliculaException(PeliculaException e) throws PeliculaException{
		
		ExceptionResponse response = new ExceptionResponse();
		response.setDescription(e.getDescription());
		
		switch (e.getCode()) {
		case 1:
			response.setCode(HttpStatus.CONFLICT.value());
			return new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
		case 2:
			response.setCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ExceptionResponse>(response,HttpStatus.NOT_FOUND);
		default:
			response.setCode(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<ExceptionResponse>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ExceptionResponse> userException(UserException e) throws UserException{
		
		ExceptionResponse response = new ExceptionResponse();
		response.setDescription(e.getDescription());
		
		switch (e.getCode()) {
		case 1:
			response.setCode(HttpStatus.CONFLICT.value());
			return new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
		case 2:
			response.setCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ExceptionResponse>(response,HttpStatus.NOT_FOUND);
		default:
			response.setCode(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<ExceptionResponse>(response,HttpStatus.BAD_REQUEST);
		}
	}
}