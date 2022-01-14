package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u from User u where u.username = ?1")
	User findByUsername(String username);
}
