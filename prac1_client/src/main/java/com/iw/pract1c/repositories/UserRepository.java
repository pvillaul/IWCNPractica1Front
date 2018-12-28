package com.iw.pract1c.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.iw.pract1c.models.User;

public interface UserRepository extends CrudRepository<User, String> {
	List<User> findAll();
	User findByName(String name);
}