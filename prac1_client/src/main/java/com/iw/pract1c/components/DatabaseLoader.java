package com.iw.pract1c.components;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.iw.pract1c.models.User;
import com.iw.pract1c.repositories.UserRepository;

@Component
public class DatabaseLoader {
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	private void initDatabase() {
        userRepository.save(new User("root", passwordEncoder().encode("root"), "ADMIN"));
        userRepository.save(new User("user", passwordEncoder().encode("password"), "VIEWER"));
	}
	
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}