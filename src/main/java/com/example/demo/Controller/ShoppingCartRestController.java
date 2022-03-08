package com.example.demo.Controller;

import java.security.Principal;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.ShoppingCartServices;

@RestController
public class ShoppingCartRestController {

	@Autowired
	private ShoppingCartServices cartServices;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/cart/add/{pid}/{qty}")
	public String addProductToCart(@PathVariable("pid") Integer productId, 
			@PathVariable("qty") Integer quantity, Principal principal) {
		
		String username = principal.getName();
		User user = userRepository.findByUsername(username);
		
		Integer addQuantity = cartServices.addProduct(productId, quantity, user);
		
		return addQuantity + "item(s) of this product has been added to your cart!";
	}
	
	@PostMapping("/cart/update/{pid}/{qty}")
	public String updateQuantity(@PathVariable("pid") Integer productId, 
			@PathVariable("qty") Integer quantity, Principal principal) {
		
		String username = principal.getName();
		User user = userRepository.findByUsername(username);
		
		float subtotal = cartServices.updateQuantity(productId, quantity, user);
		
		return String.valueOf(subtotal);
	}
	
	@PostMapping("/cart/remove/{pid}")
	public String removeProductFromCart(@PathVariable("pid") Integer productId, Principal principal) {
		
		String username = principal.getName();
		User user = userRepository.findByUsername(username);
		
		cartServices.removeProduct(productId, user);
		
		return "The product has been removed from your cart";
	}
}
