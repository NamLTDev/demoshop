package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.BillDetail;
import com.example.demo.Service.BillDetailService;

@RestController
public class BillDetailController {

	@Autowired
	private BillDetailService billDetailService;
	
	@GetMapping("/listBillDetail/{id}")
	public List<BillDetail> getAll(@PathVariable int id){
		List<BillDetail> billDetails = billDetailService.findByBill(id);
		return billDetails;
	}
}
