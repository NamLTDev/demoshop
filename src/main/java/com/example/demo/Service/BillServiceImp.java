package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Entity.Bill;
import com.example.demo.Entity.BillDetail;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.Status;
import com.example.demo.Entity.User;
import com.example.demo.Repository.BillDetailRepo;
import com.example.demo.Repository.BillRepo;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.StatusRepository;


@Component
@Transactional
public class BillServiceImp implements BillService {
	
	@Autowired
	private BillRepo billRepo;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BillDetailRepo billDetailRepo;

	@Override
	public List<Bill> getAll() {
		// TODO Auto-generated method stub
		return billRepo.findAll();
	}

	@Override
	public List<Bill> findByUser(User user) {
		// TODO Auto-generated method stub
		return billRepo.findByUser(user);
	}

	@Override
	public Bill editBill(int id, Bill bill) {
		Bill bill1 = billRepo.findBillById(id);
		bill1.setStatus(bill.getStatus());
		billRepo.save(bill1);
		return null;
	}

	@Override
	public Bill cancelBill(int id) {
		
		Bill bill = billRepo.findBillById(id);
		Status status = statusRepository.findStatusById(4);
		bill.setStatus(status);
		List<BillDetail> billDetails = billDetailRepo.findByBill(bill);
		for(BillDetail billDetail : billDetails) {
			Product product = billDetail.getProduct();
			product.setNumber(product.getNumber() + billDetail.getQuantity());
			productRepository.save(product);
		}
		billRepo.save(bill);
		return bill;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int totalMoney(int month, int year) {
		List<Bill>bills = billRepo.findAll();
		int totalMoney = 0;
		for(Bill bill : bills) {
			if(bill.getStatus().getStatusName().equalsIgnoreCase("Đã nhận hàng")
					&& bill.getBuyDate().getMonth() - month + 1 == 0
					&& bill.getBuyDate().getYear() - year + 1900 == 0) {
				totalMoney = totalMoney + bill.getTotal();
			}
		}
		return totalMoney;
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public List<Bill> getAllByMonthAndYear(int month, int year) {
		List<Bill> bills = billRepo.findAll();
		List<Bill> billsByMonthAndYear = new ArrayList<Bill>();
		for(Bill bill : bills) {
			if(bill.getStatus().getStatusName().equalsIgnoreCase("Đã nhận hàng")
					&& bill.getBuyDate().getMonth() - month + 1 == 0
					&& bill.getBuyDate().getYear() - year + 1900 == 0) {
				billsByMonthAndYear.add(bill);
			}
		}
		return billsByMonthAndYear;
	}

	@Override
	public Bill receiveBill(int id) {
		Bill bill = billRepo.findBillById(id);
		Status status = statusRepository.findStatusById(3);
		bill.setStatus(status);
		billRepo.save(bill);
		
		return bill;
	}

}
