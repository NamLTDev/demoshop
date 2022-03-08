package com.example.demo;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Status;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CartItemRepository;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.StatusRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.ProductsService;

@DataJpaTest
@AutoConfigureTestDatabase(replace =Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private CartItemRepository itemRepository;
	
//	@Autowired
//	private ProductsService service;
//	
	@Autowired
	private UserRepository repo;
//	
//	@Autowired
//	private CategoryRepository repoc;
//	
//	@Autowired
//	private ProductRepository repop;
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	
	
//	@Test
//	public void testCreateUser() {
//		User user = new User();
//		user.setUsername("namc2000");
//		user.setPassword("123456");
//		user.setAddress("Hanoi");
//		user.setPhone("123456789");
//		user.setName("Le Thanh Nam");
//		
//		
//		User savedUser = repo.save(user);
//		
//		User existUser = entityManager.find(User.class, savedUser.getId());
//		
//		assertThat(existUser.getUsername()).isEqualTo(user.getUsername());
//	}
	
	@Test
	public void testFindUserByUsername() {
		String keyword = "nam";
		
//		List<User> user = repo.findCustomerByKeyword(keyword);
		
		User user = entityManager.find(User.class, 17);
		System.out.println(user.getName());
		
		assertThat(user).isNotNull();
	}
	
	/*
	 * @Test public void testFindProductByName() { String proname = "2"; String
	 * catname = "";
	 * 
	 * List<Product> product = service.findProByName(proname, catname); }
	 */
	
//	@Test
//	public void testCreateCategory() {
//		Category category = new Category();
//		category.setName("Jordan");
//		
//		Category savedCategory = repoc.save(category);
//		Category existCategory = entityManager.find(Category.class, savedCategory.getId());
//		assertThat(existCategory.getName()).isEqualTo(category.getName());
//	}

//	

//	@Test
//	public void testFindCart() {
//		int id = 1;
//		CartItem cartItem = itemRepository.getById(id);
//		assertThat(cartItem).isNotNull();
//	}
}
