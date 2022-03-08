package com.example.demo.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.ProductsService;
import com.example.demo.UserDetails.CustomUserDetailsService;


@Controller
public class CustomerController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private ProductRepository productRepository;

	//SIGN_UP
	@PostMapping("/process_register")
	public String SignUpCustomer(User user ) {
		
		customUserDetailsService.saveUserToDB(user);
		
		return "redirect:/shoppage";
	}
	
	@GetMapping("/homepage")
	public String homepage() {
		return "customer/shoppage";
	}
	
	
	@GetMapping("/shoppage")
	public String shoppage2(Model model, @Param("proname") String proname, @Param("catname") String catname,
			Pageable pageable) {
		return productListByPage2(model, 1, proname, catname, pageable);
	}

	@GetMapping("/page-{pageNumber}")
	public String productListByPage2(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("proname") String proname, @Param("catname") String catname, Pageable pageable) {

		Page<Product> proPage = productsService.findProByName(proname, catname, pageable, currentPage);
		List<Product> listProduct = proPage.getContent();
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("currentPage", currentPage);

		int pageTotal = proPage.getTotalPages();
		model.addAttribute("pageTotal", pageTotal);

		return "customer/shoppage2";
	}
	
	
	//Tat ca san pham
	
	@GetMapping("/shoppage_customer")
	public String shoppage(Model model, @Param("proname") String proname, @Param("catname") String catname,
			Pageable pageable) {
		return productListByPage(model, 1, proname, catname, pageable);
	}

	@GetMapping("/page{pageNumber}")
	public String productListByPage(Model model, @PathVariable("pageNumber") int currentPage,
			@Param("proname") String proname, @Param("catname") String catname, Pageable pageable) {

		Page<Product> proPage = productsService.findProByName(proname, catname, pageable, currentPage);
		List<Product> listProduct = proPage.getContent();
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("currentPage", currentPage);

		int pageTotal = proPage.getTotalPages();
		model.addAttribute("pageTotal", pageTotal);

		return "customer/shoppage";
	}

	//Tai Nghe
	
	@GetMapping("/tai-nghe-sony") 
	public String listTaiNgheSony(Model model, Pageable pageable) { 
		return productListByPage(model,1,"Sony", "Tai nghe", pageable); 
		}
	 
	@GetMapping("/tai-nghe-lg") 
	public String listTaiNgheLG(Model model, Pageable pageable) { 
		return productListByPage(model,1,"LG", "Tai nghe", pageable); 
		}
	
	@GetMapping("/tai-nghe-jbl") 
	public String listTaiNgheJBL(Model model, Pageable pageable) { 
		return productListByPage(model,1,"JBL", "Tai nghe", pageable); 
		}
	
	@GetMapping("/tai-nghe-apple") 
	public String listTaiNgheApple(Model model, Pageable pageable) { 
		return productListByPage(model,1,"Apple", "Tai nghe", pageable); 
		}
	
	@GetMapping("/tai-nghe") 
	public String listTaiNghe(Model model, Pageable pageable) { 
		return productListByPage(model,1,"", "Tai nghe", pageable); 
		}
	
	
	//Sac du phong
	
	@GetMapping("/sac-du-phong-enegizer") 
	public String listSacDuPhongEnegizer(Model model, Pageable pageable) { 
		return productListByPage(model,1,"Enegizer", "Sạc dự phòng", pageable); 
		}
	
	@GetMapping("/sac-du-phong-innostyle") 
	public String listSacDuPhongInnostyle(Model model, Pageable pageable) { 
		return productListByPage(model,1,"Innostyle", "Sạc dự phòng", pageable); 
		}
	
	@GetMapping("/sac-du-phong") 
	public String listSacDuPhong(Model model, Pageable pageable) { 
		return productListByPage(model,1,"", "Sạc dự phòng", pageable); 
		}
	
	//Sac cap
	
	@GetMapping("/sac-cap-pisen") 
	public String listSacDuPhongPisen(Model model, Pageable pageable) { 
		return productListByPage(model,1,"Pisen", "Sạc cáp", pageable); 
		}
	
	@GetMapping("/sac-cap-mophie") 
	public String listSacCapMophie(Model model, Pageable pageable) { 
		return productListByPage(model,1,"Mophie", "Sạc cáp", pageable); 
		}
	
	@GetMapping("/sac-cap-anker") 
	public String listSacCapAnker(Model model, Pageable pageable) { 
		return productListByPage(model,1,"Anker", "Sạc cáp", pageable); 
		}
	
	@GetMapping("/sac-cap-enegizer") 
	public String listSacCapEnegizer(Model model, Pageable pageable) { 
		return productListByPage(model,1,"Enegizer", "Sạc cáp", pageable); 
		}
	
	@GetMapping("/sac-cap") 
	public String listSacCap(Model model, Pageable pageable) { 
		return productListByPage(model,1,"", "Sạc cáp", pageable); 
		}
	
	//Xem san pham
	@GetMapping("/{proname}")
	public String productDetail(Model model, @PathVariable("proname") String proname, Pageable pageable) {
		
		Product product = productRepository.getProductDetail(proname);
		model.addAttribute("product", product);
		
		return "customer/productDetails";
	}
	
}