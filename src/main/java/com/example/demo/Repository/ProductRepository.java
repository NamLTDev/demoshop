package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("SELECT p FROM Product p JOIN p.category c"
			+ " WHERE"
			+ " CONCAT(p.name, p.id, p.brand)"
			+ " LIKE %?1%"
			+ " AND c.name LIKE %?2%")
	public List<Product> findProductByName(String productname, String categoryname);
	
}
