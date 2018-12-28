package com.iw.pract1c.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.iw.pract1c.models.User;
import com.iw.pract1c.repositories.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	@Autowired
	private UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) {
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		User user;
		try {
			user = userRepository.findByName(username);
		} catch(Exception e) {
			throw new BadCredentialsException("User not found");
		}
		
		List<User> usuarios = userRepository.findAll();
		
		for (User u : usuarios) {
			System.out.println(u.getName());
			System.out.println(u.getPassword());
			System.out.println(u.getRol());
		}
		
		System.out.println(username);
		System.out.println(password);
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		System.out.println(user.getRol());
		
		/*
		if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
			throw new BadCredentialsException("Wrong password");
		}*/
		
		if (passwordEncoder().matches(password, user.getPassword())) {
			throw new BadCredentialsException("Wrong password");
		}

		return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());

	}
	
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}