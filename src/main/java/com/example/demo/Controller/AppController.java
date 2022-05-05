package com.example.demo.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Entity.Bill;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Status;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.StatusRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.BillService;
import com.example.demo.Service.ProductsService;
import com.example.demo.Service.UserService;

@Controller
public class AppController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductsService service;
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private CategoryRepository repoc;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private BillService billService;
	
	
	@GetMapping("/")
	public String viewHomePage(Model model,Principal principal) {
		String username = principal.getName();
		User user = repo.findByUsername(username);
		model.addAttribute("user",user);
		if(user.getRoles().getRoleName().equalsIgnoreCase("admin")) {
			model.addAttribute("role",user.getRoles().getRoleName());
			return"redirect:/product";
		}
		return "redirect:/shoppage_customer";
	}
	
	@GetMapping("/register")
	public String showSignUpForm(Model model) {
		model.addAttribute("user",new User());
		
		return "admin/signup_form";
	}
	
//	@PostMapping("/process_register")
//	public String processRegistration(User user) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String encodedPassword = encoder.encode(user.getPassword());
//		user.setPassword(encodedPassword);
//		
//		repo.save(user);
//		
//		return "admin/register_success";
//	}
	
	@RequestMapping("/statistic")
	public String statistic(Principal principal, Model model) {
		String username = principal.getName();
		User user = repo.findByUsername(username);
		model.addAttribute("user", user);
		if (user.getRoles().getRoleName().equalsIgnoreCase("ADMIN")) {
			model.addAttribute("role", user.getRoles().getRoleName());
		}
		return "admin/statistic";
	}
	
	@GetMapping("/customer_list")
		public String viewUsersList(Model model, @Param("keyword") String keyword) {
		List<User> listUsers =userService.findCustomerByKeyword(keyword);
		model.addAttribute("listUsers",listUsers);
		
		return "admin/customerList";
		}
	
	@GetMapping("/product")
	public String viewProductList(Model model,Principal principal, @Param("proname") String proname,@Param("catname") String catname, Pageable pageable) {
		
		return listByPage(model, 1, principal, proname , catname, pageable);
	}
	
	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
		Principal principal, @Param("proname") String proname,@Param("catname") String catname, Pageable pageable) {
		
		final String currentUser = principal.getName();
		User user = repo.findByUsername(currentUser);
		model.addAttribute("user", user);
		
		//find product by name or get all products
		Page<Product> page = service.findProByName(proname, catname,pageable, currentPage);
		List<Product> listProducts = page.getContent();
		model.addAttribute("listProducts",listProducts);
		
		long totalProduct = page.getTotalElements();
		int totalPage = page.getTotalPages();
		model.addAttribute("totalProduct",totalProduct);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("currentPage", currentPage);
		
		List<Category> listCat = repoc.findAll();
		model.addAttribute("listCat", listCat);
		
		return "admin/productList";
	}
	
	@GetMapping("/category")
	public String viewCategoryList(Model model) {
		
		List<Category> listCategories = repoc.findAll();
		model.addAttribute("listCategories",listCategories);
		
		return "admin/category_list";
	}
	
	//LIST BILL
		@GetMapping("/bill_list")
		public String listBill(Principal principal, Model model) {
			String username = principal.getName();
			User user = repo.findByUsername(username);
			model.addAttribute("user", user);
			List<Status> status1 = statusRepository.findAll();
			List<Status> status2 = new ArrayList<Status>();
			for(Status status : status1) {
				if(status.getStatusName().equals("Hủy đơn") == false && status.getStatusName().equals("Đã nhận hàng") == false) {
					status2.add(status);
				}
			}
			if (user.getRoles().getRoleName().equalsIgnoreCase("ADMIN")) {
				List<Bill> bills = billService.getAll();
				model.addAttribute("bills", bills);
				model.addAttribute("role", user.getRoles().getRoleName());
				model.addAttribute("status2", status2);
				model.addAttribute("user", user);
				return "admin/bill";
			}
			else {
				List<Bill> bills = billService.findByUser(user);
				model.addAttribute("bills", bills);
				return "customer/billCustomer";
			}
		}
	
	@GetMapping("/login")
	public String showLoginPage(User user,HttpServletRequest request, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken || request.getSession() == null) {
			return "common/loginCustomer";
		}
		else {
			model.addAttribute("user",user);
			request.getSession().setAttribute("MY_SESSION_MESSAGE", user);
			return "redirect:/";
		}
	}
	
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
