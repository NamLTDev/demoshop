package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u from User u where u.username = ?1")
	User findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.name = ?1")
	User findByFullName(String fullName);
	
	@Query("SELECT u FROM User u JOIN u.roles r"
			+ " WHERE r.roleName = ?1") 
	public List<User> findByRoleName(String roleName);
	
	@Query("SELECT u FROM User u JOIN u.roles r"
			+ " WHERE"
			+ " CONCAT(u.name, u.phone) LIKE %:keyword%"
			+ " AND (r.roleName = 'CUSTOMER')") 
	public List<User> findCustomerByKeyword(@Param("keyword") String keyword);
}
