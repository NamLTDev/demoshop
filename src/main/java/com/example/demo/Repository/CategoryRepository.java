package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("SELECT c from Category c where c.id = ?1")
	Category findByCategoryname(String categoryname);
	
	Category findCategoryById(Long id);
}


