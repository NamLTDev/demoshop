package com.example.demo.Entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column
	private String phone;
	
	@Column
	private String address;
	
	@Column
	private int total;
	
	@Column
	private String payment;
	
	@Column 
	private Date buyDate;
	
	
	@ManyToOne
	@JoinColumn(name="status_id")
	private Status status;
	
}
