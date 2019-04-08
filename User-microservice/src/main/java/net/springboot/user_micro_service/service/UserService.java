package net.springboot.user_micro_service.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.springboot.user_micro_service.model.User;
import net.springboot.user_micro_service.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
    
    List<User> findAll();

	void deleteByEmail(String email);
	
//	void mergeWithExistingAndUpdate(final User personFromPost);

	User findById(long id);

}
