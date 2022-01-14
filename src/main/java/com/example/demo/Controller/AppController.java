package com.example.demo.Controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.ProductsService;

@Controller
public class AppController {
	
	@Autowired
	public ProductsService service;
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private CategoryRepository repoc;
	
	@Autowired
	private ProductRepository repop;
	
	@GetMapping("")
	public String viewHomePage() {
		return "admin/index";
	}
	
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user",new User());
		
		return "admin/signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegistration(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		repo.save(user);
		
		return "admin/register_success";
	}
	
	@GetMapping("/list_users")
		public String viewUsersList(Model model) {
		List<User> listUsers = repo.findAll();
		model.addAttribute("listUsers",listUsers);
		
		return "users";
		}
	
	@GetMapping("/product")
	public String viewProductList(Principal principal, Model model, @Param("proname") String proname,@Param("catname") String catname) {
		
		final String currentUser = principal.getName();
		User user = repo.findByUsername(currentUser);
		model.addAttribute("user", user);
		
		//find product by name or get all products
		List<Product> listProducts = service.findProByName(proname, catname);
		model.addAttribute("listProducts",listProducts);
		
		List<Category> listCat = repoc.findAll();
		model.addAttribute("listCat", listCat);
		
		return "admin/product_list";
	}
	
	@GetMapping("/category")
	public String viewCategoryList(Model model) {
		
		List<Category> listCategories = repoc.findAll();
		model.addAttribute("listCategories",listCategories);
		
		return "admin/category_list";
	}
	
	@GetMapping("/login")
	public String showLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "admin/login";
		}
		else {
			return "redirect:/product";
		}
	}
	
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
