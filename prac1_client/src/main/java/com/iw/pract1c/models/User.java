package com.iw.pract1c.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "test")
public class User {
	String ROLEPREFIX = "ROLE_";
	
	@Id
	private String name;
	private String password;
	private String rol;
	
	public User() {}
	
	public User(String name, String password, String rol) {
		this.name = name;
		this.password = passwordEncoder().encode(password);
		this.rol = rol;
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
		this.password = passwordEncoder().encode(password);
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority(ROLEPREFIX + rol));

        return list;
    }
	
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}