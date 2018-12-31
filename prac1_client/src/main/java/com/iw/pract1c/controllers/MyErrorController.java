package com.iw.pract1c.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iw.pract1c.models.Error;

public class MyErrorController implements ErrorController {
	private final static String aVISO = "aviso";
	private final static String bH = "Back Home";
	private final static String eRROR = "ERROR";
	private final static String errorM = "error";
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	     
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	Error error = new Error(eRROR + "404","Sorry Element Not Found","",bH);
				model.addAttribute(errorM,error);
				return aVISO;
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	Error error = new Error(eRROR + "500","Sorry Internal Server Error","",bH);
				model.addAttribute(errorM,error);
				return aVISO;
	        }
	    }
	    
	    Error error = new Error(eRROR,"Something Went Wrong","",bH);
		model.addAttribute(errorM,error);
		return aVISO;
	}
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
}