package com.example.demo.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

	@Query("SELECT p FROM Product p JOIN p.category c"
			+ " WHERE"
			+ " CONCAT(p.name, p.brand)"
			+ " LIKE %:productname%"
			+ " AND (c.name LIKE %:categoryname% OR :categoryname IS NULL) ")
	public Page<Product> findProductByName(@Param("productname") String productname,@Param("categoryname") String categoryname, Pageable pageable);
	
	
	@Query("SELECT p FROM Product p WHERE p.name = ?1")
	Product getProductDetail(String proname);
}
