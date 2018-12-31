package com.iw.pract1c.controllers;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.iw.pract1c.models.Error;
import com.iw.pract1c.models.ExceptionResponse;
import com.iw.pract1c.models.PeliculaException;
import com.iw.pract1c.models.UserException;

@ControllerAdvice
public class ErrorHandlingController {
	
	@ExceptionHandler(Exception.class)
	public String generalException(Exception e, Model model) throws IOException{
		
		ExceptionResponse response = new ExceptionResponse();
		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setDescription(e.getMessage());
		Error error = new Error("Error 500","INTERNAL_SERVER_ERROR","","Back Home");
		model.addAttribute("error",error);
		return "aviso";
		//return new ResponseEntity<ExceptionResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(PeliculaException.class)
	public String peliculaException(PeliculaException e, Model model) throws PeliculaException{
		
		ExceptionResponse response = new ExceptionResponse();
		response.setDescription(e.getDescription());
		
		switch (e.getCode()) {
		case 1:
			response.setCode(HttpStatus.CONFLICT.value());
			Error error = new Error("Error","CONFLICT","","Back Home");
			model.addAttribute("error",error);
			return "aviso";
			//return new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
		case 2:
			response.setCode(HttpStatus.NOT_FOUND.value());
			Error error2 = new Error("Error","NOT_FOUND","","Back Home");
			model.addAttribute("error",error2);
			return "aviso";
			//return new ResponseEntity<ExceptionResponse>(response,HttpStatus.NOT_FOUND);
		default:
			response.setCode(HttpStatus.BAD_REQUEST.value());
			Error error3 = new Error("Error","BAD_REQUEST","","Back Home");
			model.addAttribute("error",error3);
			return "aviso";
			//return new ResponseEntity<ExceptionResponse>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	@ExceptionHandler(UserException.class)
	public String userException(UserException e, Model model) throws UserException{
		
		ExceptionResponse response = new ExceptionResponse();
		response.setDescription(e.getDescription());
		
		switch (e.getCode()) {
		case 1:
			response.setCode(HttpStatus.CONFLICT.value());
			Error error = new Error("Error","CONFLICT","","Back Home");
			model.addAttribute("error",error);
			return "aviso";
			//return new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
		case 2:
			response.setCode(HttpStatus.NOT_FOUND.value());
			Error error2 = new Error("Error","NOT_FOUND","","Back Home");
			model.addAttribute("error",error2);
			return "aviso";
			//return new ResponseEntity<ExceptionResponse>(response,HttpStatus.NOT_FOUND);
		default:
			response.setCode(HttpStatus.BAD_REQUEST.value());
			Error error3 = new Error("Error","BAD_REQUEST","","Back Home");
			model.addAttribute("error",error3);
			return "aviso";
			//return new ResponseEntity<ExceptionResponse>(response,HttpStatus.BAD_REQUEST);
		}
	}
}