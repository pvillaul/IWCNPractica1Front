package com.iw.pract1c.controllers;

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
	private final String AVISO = "aviso";
	private final String BH = "Back Home";
	private final String ERROR = "ERROR";
	private final String errorM = "error";
	private final String ISE = "INTERNAL_SERVER_ERROR";
	private final String CONFLICT = "CONFLICT";
	private final String NOTFOUND = "NOT_FOUND";
	private final String BADREQUEST = "BAD_REQUEST";
	
	@ExceptionHandler(Exception.class)
	public String generalException(Exception e, Model model){
		
		ExceptionResponse response = new ExceptionResponse();
		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setDescription(e.getMessage());
		Error error = new Error(ERROR + "500",ISE,"",BH);
		model.addAttribute(errorM,error);
		return AVISO;
	}
	
	@ExceptionHandler(PeliculaException.class)
	public String peliculaException(PeliculaException e, Model model) {
		
		ExceptionResponse response = new ExceptionResponse();
		response.setDescription(e.getDescription());
		
		switch (e.getCode()) {
		case 1:
			response.setCode(HttpStatus.CONFLICT.value());
			Error error = new Error(ERROR,CONFLICT,"",BH);
			model.addAttribute(errorM,error);
			return AVISO;
		case 2:
			response.setCode(HttpStatus.NOT_FOUND.value());
			Error error2 = new Error(ERROR,NOTFOUND,"",BH);
			model.addAttribute(errorM,error2);
			return AVISO;
		default:
			response.setCode(HttpStatus.BAD_REQUEST.value());
			Error error3 = new Error(ERROR,BADREQUEST,"",BH);
			model.addAttribute(errorM,error3);
			return AVISO;
		}
	}
	
	@ExceptionHandler(UserException.class)
	public String userException(UserException e, Model model) {
		
		ExceptionResponse response = new ExceptionResponse();
		response.setDescription(e.getDescription());
		
		switch (e.getCode()) {
		case 1:
			response.setCode(HttpStatus.CONFLICT.value());
			Error error = new Error(ERROR,CONFLICT,"",BH);
			model.addAttribute(errorM,error);
			return AVISO;
		case 2:
			response.setCode(HttpStatus.NOT_FOUND.value());
			Error error2 = new Error(ERROR,NOTFOUND,"",BH);
			model.addAttribute(errorM,error2);
			return AVISO;
		default:
			response.setCode(HttpStatus.BAD_REQUEST.value());
			Error error3 = new Error(ERROR,BADREQUEST,"",BH);
			model.addAttribute(errorM,error3);
			return AVISO;
		}
	}
}