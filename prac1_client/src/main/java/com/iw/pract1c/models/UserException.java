package com.iw.pract1c.models;

import org.springframework.stereotype.Component;

@Component
public class UserException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private int code;
	private String description;
	
	public UserException(int code, String description) {
		super();
		this.code = code;
		this.description = description;
	}
	
	public UserException() {
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
