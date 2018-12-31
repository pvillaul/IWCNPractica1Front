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
	private final static String aVISO = "aviso";
	private final static String bH = "Back Home";
	private final static String eRROR = "ERROR";
	private final static String errorM = "error";
	private final static String iSE = "INTERNAL_SERVER_ERROR";
	private final static String cONFLICT = "CONFLICT";
	private final static String nOTFOUND = "NOT_FOUND";
	private final static String bADREQUEST = "BAD_REQUEST";
	
	@ExceptionHandler(Exception.class)
	public String generalException(Exception e, Model model){
		
		ExceptionResponse response = new ExceptionResponse();
		response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setDescription(e.getMessage());
		Error error = new Error(eRROR + "500",iSE,"",bH);
		model.addAttribute(errorM,error);
		return aVISO;
	}
	
	@ExceptionHandler(PeliculaException.class)
	public String peliculaException(PeliculaException e, Model model) {
		
		ExceptionResponse response = new ExceptionResponse();
		response.setDescription(e.getDescription());
		
		switch (e.getCode()) {
		case 1:
			response.setCode(HttpStatus.CONFLICT.value());
			Error error = new Error(eRROR,cONFLICT,"",bH);
			model.addAttribute(errorM,error);
			return aVISO;
		case 2:
			response.setCode(HttpStatus.NOT_FOUND.value());
			Error error2 = new Error(eRROR,nOTFOUND,"",bH);
			model.addAttribute(errorM,error2);
			return aVISO;
		default:
			response.setCode(HttpStatus.BAD_REQUEST.value());
			Error error3 = new Error(eRROR,bADREQUEST,"",bH);
			model.addAttribute(errorM,error3);
			return aVISO;
		}
	}
	
	@ExceptionHandler(UserException.class)
	public String userException(UserException e, Model model) {
		
		ExceptionResponse response = new ExceptionResponse();
		response.setDescription(e.getDescription());
		
		switch (e.getCode()) {
		case 1:
			response.setCode(HttpStatus.CONFLICT.value());
			Error error = new Error(eRROR,cONFLICT,"",bH);
			model.addAttribute(errorM,error);
			return aVISO;
		case 2:
			response.setCode(HttpStatus.NOT_FOUND.value());
			Error error2 = new Error(eRROR,nOTFOUND,"",bH);
			model.addAttribute(errorM,error2);
			return aVISO;
		default:
			response.setCode(HttpStatus.BAD_REQUEST.value());
			Error error3 = new Error(eRROR,bADREQUEST,"",bH);
			model.addAttribute(errorM,error3);
			return aVISO;
		}
	}
}