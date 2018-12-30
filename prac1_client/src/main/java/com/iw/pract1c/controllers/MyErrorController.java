package com.iw.pract1c.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iw.pract1c.models.Error;

public class MyErrorController implements ErrorController {
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	     
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	Error error = new Error("Error 404","Sorry Element Not Found","","Back Home");
				model.addAttribute("error",error);
				return "aviso";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	Error error = new Error("Error 500","Sorry Internal Server Error","","Back Home");
				model.addAttribute("error",error);
				return "aviso";
	        }
	    }
	    
	    Error error = new Error("Error!!","Something Went Wrong","","Back Home");
		model.addAttribute("error",error);
		return "aviso";
	}
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
}