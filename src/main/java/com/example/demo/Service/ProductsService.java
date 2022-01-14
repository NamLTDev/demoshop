package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRepository;

@Service
public class ProductsService {
	
	@Autowired
	public ProductRepository repo;
	
	public List<Product> findProByName(String productname, String categoryname ) {
		if(productname !=null || categoryname != null) {
			return repo.findProductByName(productname, categoryname);
		}
		return repo.findAll();
	}
}
