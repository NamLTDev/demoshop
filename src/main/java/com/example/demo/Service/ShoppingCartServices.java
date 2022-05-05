package com.example.demo.Service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
@Transactional
public class ShoppingCartServices {

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
	private UserRepository userRepository;
	
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
	
	
	//BỎ HÀNG RA KHỎI GIỎ
	public void removeProduct(Integer productId, User user) {
		cartItemRepository.deleteByUserAndProduct(user.getId(), productId);
	}
	
	
	//CHECK OUT 
	public void checkOut(User user, String howToPay, String address) {
		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		Bill bill = new Bill();
		bill.setBuyDate(date);
		bill.setPayment(howToPay);
		bill.setAddress(address);
		
		Status status = statusRepository.findStatusByStatusName("Đang xử lý");
		bill.setUser(user);
		bill.setStatus(status);
		bill.setPhone(user.getPhone());
		billRepo.save(bill);
		int total = 0;
		for(CartItem cartItem : cartItems) {
			BillDetail billDetail = new BillDetail();
			billDetail.setQuantity(cartItem.getQuantity());
			billDetail.setProductPrice(cartItem.getProduct().getPrice());
			billDetail.setProduct(cartItem.getProduct());
			billDetail.setBill(bill);
			
			Product product = cartItem.getProduct();
			product.setQuantity(product.getQuantity() - cartItem.getQuantity());
			billDetail.setMoney(billDetail.getQuantity() * cartItem.getProduct().getPrice());
			billDetailRepo.save(billDetail);
			total += billDetail.getMoney();
			productRepository.save(product);
		}
		bill.setTotal(total);
		billRepo.save(bill);
		cartItemRepository.deleteByUser(user.getId());
	}
	
}
