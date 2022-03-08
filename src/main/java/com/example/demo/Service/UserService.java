package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;



@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	
	public List<User> findUserByRoleName(String roleName){
		return repository.findByRoleName(roleName);
	}
	
	public List<User> findCustomerByKeyword(String keyWord){
		if(keyWord == null){
			return repository.findByRoleName("CUSTOMER");
		}
		return repository.findCustomerByKeyword(keyWord);
	}
	
//	public User getCurrentlyLoggedInUser (Authentication authentication) {
//		if(authentication == null)
//			return null;
//		User user = null;
//		Object principal = authentication.getPrincipal();
//		
//		if(principal instanceof CustomUserDetails) {
//			user = ((CustomUserDetails) principal).getUsername();			
//		}
//		else if() {
//			
//		}
//	}
}