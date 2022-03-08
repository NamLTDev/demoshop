package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CartItemRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace =  Replace.NONE)
@Rollback(false)
public class ShoppingCartTests {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
//	@Test
//	public void testAddOneCartItem() {
//		Product product = entityManager.find(Product.class, 23);
//		User user = entityManager.find(User.class, 17);
//		System.out.println(user.getName());
//		CartItem newItem = new CartItem();
//		newItem.setUser(user);
//		newItem.setProduct(product);
//		newItem.setQuantity(1);
//		
//		CartItem saveCartItem = cartItemRepository.save(newItem);
//		assertTrue(saveCartItem.getId() > 0);
//	}
//	
	@Test
	public void testGetCartItemByCustomer() {
		User user = new User();
		user.setId(17);
		
		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		
	}
}
