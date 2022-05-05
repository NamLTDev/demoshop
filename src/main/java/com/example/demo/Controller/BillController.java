package com.example.demo.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Bill;
import com.example.demo.Entity.Status;
import com.example.demo.Entity.User;
import com.example.demo.Repository.StatusRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.BillService;

@RestController
public class BillController {
	
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	
	//EDIT BILL
	@PutMapping("/editBill/{id}")
	public ResponseEntity<Bill> editBill(@PathVariable int id, @RequestBody Bill bill){
		Bill bill1 = billService.editBill(id, bill);
		return new ResponseEntity<Bill>(bill1, HttpStatus.OK);
	}
	
	//CANCLE BILL
	@PutMapping("/cancelBill/{id}")
	public ResponseEntity<Bill> cancelBill(@PathVariable int id) {
		return new ResponseEntity<Bill>(billService.cancelBill(id), HttpStatus.OK);
	}
	
	/* RECEIVE BILL */
	@PutMapping("/receiveBill/{id}")
	public ResponseEntity<Bill> receiveBill(@PathVariable int id) {
		return new ResponseEntity<Bill>(billService.receiveBill(id), HttpStatus.OK);
	}

	/* STATISTICS */
	@GetMapping("/statistic/{month}/{year}")
	public int statistic(@PathVariable int month, @PathVariable int year) {
		int totalMoney = billService.totalMoney(month, year);
		return totalMoney;
	}

	/* BILLS BY MONTH AND YEAR */
	@GetMapping("/billsByMonthAndYear/{month}/{year}")
	public List<Bill> billsByMonthAndYear(@PathVariable int month, @PathVariable int year) {
		List<Bill> billsByMonthAndYear = billService.getAllByMonthAndYear(month, year);
		return billsByMonthAndYear;
	}
}
