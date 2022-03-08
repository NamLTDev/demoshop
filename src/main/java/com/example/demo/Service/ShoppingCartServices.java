package com.example.demo.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CartItemRepository;
import com.example.demo.Repository.ProductRepository;

@Service
@Transactional
public class ShoppingCartServices {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<CartItem> listCartItems(User user){
		return cartItemRepository.findByUser(user);
	}
	
	//THÊM SẢN PHẨM VÀO GIỎ HÀNG
	public Integer addProduct(Integer productId, Integer quantity, User user) {
		
		Integer addQuantity = quantity;
		
		Product product = productRepository.findById(productId).get();
		
		CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
		
		if(cartItem != null) {
			addQuantity = cartItem.getQuantity() + quantity;
			cartItem.setQuantity(addQuantity);
		}
		else {
			cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setUser(user);
			cartItem.setProduct(product);
		}
		
		cartItemRepository.save(cartItem);
		
		return addQuantity;
	}
	
	
	//HIỂN THỊ SỐ TIỀN TRONG GIỎ HÀNG
	public float updateQuantity(Integer productId, Integer quantity, User user) {
		System.out.println("updateQuantity: " + productId +" " + quantity);
		
		cartItemRepository.updateQuantity(quantity, productId, user.getId());
		Product product = productRepository.findById(productId).get();
		
		float subtotal = product.getPrice()*quantity;
		return subtotal;
	}
	
	public void removeProduct(Integer productId, User user) {
		cartItemRepository.deleteByUserAndProduct(user.getId(), productId);
	}
	
}
