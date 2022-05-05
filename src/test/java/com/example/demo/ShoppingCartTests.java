package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Principal;
import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.Entity.Bill;
import com.example.demo.Entity.BillDetail;
import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Status;
import com.example.demo.Entity.User;
import com.example.demo.Repository.BillDetailRepo;
import com.example.demo.Repository.BillRepo;
import com.example.demo.Repository.CartItemRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.StatusRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.ShoppingCartServices;

@DataJpaTest
@AutoConfigureTestDatabase(replace =  Replace.NONE)
@Rollback(false)
public class ShoppingCartTests {

	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private BillRepo billRepo;
	
	@Autowired
	private BillDetailRepo billDetailRepo;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ShoppingCartServices cartServices;
	
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
	public void testGetCartItemByCustomer(Principal principal) {
		String howToPay = "Tiền mặt";
		String address ="HCM";
		String username = "admin";
		User user = repository.findByUsername(username);
		cartServices.checkOut(user, howToPay, address);
	}
}
