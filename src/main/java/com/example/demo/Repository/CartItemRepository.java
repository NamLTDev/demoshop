package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	
	public List<CartItem> findByUser(User user);
	
	public CartItem findByUserAndProduct(User user, Product product);
	
	@Query("UPDATE CartItem c SET c.quantity =?1 WHERE c.product.id = ?2"
			+ " AND c.user.id = ?3")
	@Modifying
	public void updateQuantity(Integer quantity, Integer productId, Integer customerId); 
	
	@Query("DELETE FROM CartItem c WHERE c.user.id = ?1 AND c.product.id = ?2")
	@Modifying
	public void deleteByUserAndProduct(Integer userId, Integer productId);

}
