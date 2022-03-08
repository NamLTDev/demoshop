package com.example.demo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRepository;

@Service
public class ProductsService {
	
	@Autowired
	private ProductRepository repo;
	
	public Page<Product> listAll(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber - 1, 12);
		return repo.findAll(pageable);
	}
	
	public Page<Product> findProByName(String productname, String categoryname, Pageable pageable, int pageNumber ) {
		if(productname != null || categoryname != null) {
			return repo.findProductByName(productname, categoryname, pageable);
		}
		return listAll(pageNumber);
	}
	
	public Page<Product> taingheApple(Pageable pageable, int pageNumber){
		return repo.findProductByName("Apple", "Tai nghe", pageable);
	}

}
