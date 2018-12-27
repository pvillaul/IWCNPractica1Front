package com.iw.pract1c.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Entity
public class User implements UserDetailsService{
	String ROLE_PREFIX = "ROLE_";
	
	@Id
	private String code;
	private String name;
	private String password;
	private String rol;
	
	public User() {}
	
	public User(String code, String name, String password, String rol) {
		super();
		this.code = code;
		this.name = name;
		this.password = password;
		this.rol = rol;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		return null;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + rol));

        return list;
    }
}