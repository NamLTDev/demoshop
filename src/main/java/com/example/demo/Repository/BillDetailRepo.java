package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Bill;
import com.example.demo.Entity.BillDetail;

public interface BillDetailRepo extends JpaRepository<BillDetail, Integer>{
	
	List<BillDetail> findByBill(Bill bill);
}
