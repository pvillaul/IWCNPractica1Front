package com.iw.pract1c.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iw.pract1c.models.Error;

public class MyErrorController implements ErrorController {
	private final String AVISO = "aviso";
	private final String BH = "Back Home";
	private final String ERROR = "ERROR";
	private final String errorM = "error";
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	     
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	Error error = new Error(ERROR + "404","Sorry Element Not Found","",BH);
				model.addAttribute(errorM,error);
				return AVISO;
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	Error error = new Error(ERROR + "500","Sorry Internal Server Error","",BH);
				model.addAttribute(errorM,error);
				return AVISO;
	        }
	    }
	    
	    Error error = new Error(ERROR,"Something Went Wrong","",BH);
		model.addAttribute(errorM,error);
		return AVISO;
	}
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
}