package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entity.Category;
import com.example.demo.Repository.CategoryRepository;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryRepository repo;
	
	
	@GetMapping("/create")
	public String CreateCategory(Model model) {
		model.addAttribute("category",new Category());
		return "admin/category_create";
	}
	
	@PostMapping("/category/save")
	public String SaveCategoy(Category category) {
		repo.save(category);
		return "redirect:/category";
	}
	
	@GetMapping("/delete/{id}")
	public String DeleteCategory(@PathVariable(name="id") Integer id) {
		repo.deleteById(id);
		return "redirect:/category";
	}
	
	@GetMapping("/edit/{id}")
	public String EditCategory(@PathVariable("id") Integer id, Model model) {
		Category category = repo.findById(id).get();
		model.addAttribute("category",category);
		return "admin/categoryEdit";
	}
	
	@PostMapping("/category/edit_save")
	public String SaveChange(Category category, Integer id) {
		Category category1 = repo.findById(id).get();
		category1.setName(category.getName());
		repo.save(category1);
		return "redirect:/category";
	}
}
