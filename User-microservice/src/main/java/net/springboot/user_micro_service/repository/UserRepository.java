package net.springboot.user_micro_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.springboot.user_micro_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	void deleteByEmail(String email);
	User findById(long id);
}
