package com.example.demo.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;


@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private CategoryRepository repo1;
	
	@GetMapping("/product/create")
	public String CreateProduct(Model model) {
		
		List<Category> listCategory = repo1.findAll();
		model.addAttribute("listCategory",listCategory);
		model.addAttribute("product",new Product());
		
		return "admin/productCreate";
	}
	
	@PostMapping("/product/save")
	public String SaveNewPro(Product product, @RequestParam("fileImage") MultipartFile file) throws IOException {
		
		//clean unnecessary information in the file name 
		String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
		product.setImage(fileName);
		
		Product savedProduct = repo.save(product);
		String uploadDirectory = "./product-image/" + savedProduct.getId();
		Path upPath = Paths.get(uploadDirectory);
		
		if(!Files.exists(upPath)) {
			Files.createDirectories(upPath);
		}
		try(InputStream inputStream = file.getInputStream()){
			Path filePath  = upPath.resolve(fileName);
			
			//check where image being saved
			System.out.println(filePath.toFile().getAbsolutePath());
			Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);	
		}
		catch (Exception e) {
			throw new IOException("Could not save file: " + fileName);// TODO: handle exception
		}
		
		return"redirect:/product";
	}
	
	@GetMapping("/product/delete/{id}")
	public String DeletePro(@PathVariable("id") Integer id) {
		repo.deleteById(id);
		return "redirect:/product";
	}
	
	@GetMapping("/product/edit/{id}")
	public String EditProPage(@PathVariable("id") Integer id, Model model) {
		Product product= repo.findById(id).get();
		model.addAttribute("product", product);
		
		List<Category> listCategory = repo1.findAll();
		model.addAttribute("listCategory",listCategory);
		return"admin/productEdit";
	}
	

	public void EditProImage(Product product, Integer id, MultipartFile file) throws IOException {
		product = repo.findById(id).get();
		
		String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
		product.setImage(fileName);
		
		Product savedProduct = repo.save(product);
		String uploadDirectory = "./product-image/" + savedProduct.getId();
		Path upPath = Paths.get(uploadDirectory);
		
		if(!Files.exists(upPath)) {
			Files.createDirectories(upPath);
		}
		try(InputStream inputStream = file.getInputStream()){
			Path filePath  = upPath.resolve(fileName);
			Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);	
		}
		catch (Exception e) {
			throw new IOException("Could not save file: " + fileName);// TODO: handle exception
		}
		
		repo.save(product);
	}
	
	@PostMapping("/product/save_changes")
	public String SaveProChanges(Product product, Integer id, @RequestParam("fileImage") MultipartFile file) throws IOException {
		
		Product product2 = repo.findById(id).get();
		
		product2.setCategory(product.getCategory());
		product2.setName(product.getName());
		product2.setPrice(product.getPrice());
		product2.setQuantity(product.getQuantity());
		product2.setDescription(product.getDescription());
		product2.setBrand(product.getBrand());
		product2.setInsurance(product.getInsurance());
		
		EditProImage(product2,id,file);
		
		repo.save(product2);
		
		
		return "redirect:/product";
	}

	
}
