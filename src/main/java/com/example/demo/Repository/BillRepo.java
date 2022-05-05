package com.example.demo.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Bill;
import com.example.demo.Entity.User;

@Repository
public interface BillRepo extends JpaRepository<Bill, Integer> {

	public List<Bill> findByUser(User user);
	
	Bill findBillById(int id);
	
	@Query("DELETE FROM Bill WHERE id = ?1")
	@Transactional
	@Modifying
	void deleteBillById(int id);
}
