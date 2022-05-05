package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.BillDetail;

@Service
public interface BillDetailService {
	List<BillDetail> findByBill(Integer id);
}
