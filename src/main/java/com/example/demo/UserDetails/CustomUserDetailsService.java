package com.example.demo.UserDetails;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Roles;
import com.example.demo.Entity.User;
import com.example.demo.Repository.RolesRepository;
import com.example.demo.Repository.UserRepository;

@Service
@Component
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}
	
	public List<User> findAll(){
		return repo.findAll();
	}
	
	
	//SIGN-UP
	public void saveUserToDB(User user) {
		Roles roles = rolesRepository.findRoleByRoleName("CUSTOMER");
		BCryptPasswordEncoder endEncoder = new BCryptPasswordEncoder();
		String encodedPassword = endEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setRoles(roles);
		repo.save(user);
	}

}
 