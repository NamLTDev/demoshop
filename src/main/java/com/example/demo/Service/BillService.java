package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Bill;
import com.example.demo.Entity.User;

@Service
public interface BillService {
	
	List<Bill> getAll();
	
	List<Bill> findByUser(User user);
	
	Bill editBill(int id, Bill bill);
	
	Bill cancelBill(int id);
	
	int totalMoney(int month, int year);
	
	List<Bill> getAllByMonthAndYear(int month, int year);
	
	Bill receiveBill(int id);
}
