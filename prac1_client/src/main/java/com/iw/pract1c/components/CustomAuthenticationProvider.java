package com.iw.pract1c.components;

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
		user = userRepository.findByName(username);
		if(user != null){
			if (passwordEncoder().matches(password, user.getPassword())) {
				return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
			}
			else{
				throw new BadCredentialsException("Wrong password");
			}
		}
		else{
			throw new BadCredentialsException("User not found");
		}
	}
	
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}