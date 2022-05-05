package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Entity.Bill;
import com.example.demo.Entity.BillDetail;
import com.example.demo.Repository.BillDetailRepo;
import com.example.demo.Repository.BillRepo;

@Component
public class BillDetailServiceImp implements BillDetailService {

	@Autowired
	private BillRepo billRepo;
	
	@Autowired
	private BillDetailRepo billDetailRepo;
	
	@Override
	public List<BillDetail> findByBill(Integer id) {
		
		Bill bill = billRepo.findBillById(id);
		List<BillDetail> billDetails = billDetailRepo.findByBill(bill);
		return billDetails;
	}

}
