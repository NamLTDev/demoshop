package com.example.demo.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.ShoppingCartServices;

@Controller
public class ShoppingCartController {

	@Autowired
	private ShoppingCartServices cartServices;
	
	
	@Autowired
	private UserRepository userRepository;
	
	static List<String> howToPays = new ArrayList<String>();
	static {
		howToPays.add("Thanh toán khi nhận hàng");
	}
	
	@GetMapping("/cart")
	public String showShoppingCart(Model model, Principal principal) {
		
		model.addAttribute("howToPays", howToPays);
		
		String username = principal.getName();
		User user = userRepository.findByUsername(username);
		model.addAttribute("user",user);
		
		List<CartItem> listCartItemts = cartServices.listCartItems(user);
		model.addAttribute("listCartItemts", listCartItemts);
		model.addAttribute("pageTitle", "Shopping Cart");
		
		
		return "customer/shopping_cart";
	}
	
	//CHECK OUT
	@PostMapping("/checkOut")
	public String checkOut(String howToPay, String address, Principal principal) {
		String username = principal.getName();
		User user = userRepository.findByUsername(username);
		
		cartServices.checkOut(user, howToPay, address);
		return "redirect:/bill_list";
	}
	
	
}
