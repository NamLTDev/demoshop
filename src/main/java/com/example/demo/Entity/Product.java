 package com.example.demo.Entity;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Product")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128, nullable = false, unique = true)
	private String name;
	
	@Column(length = 45, nullable = true)
	private String image;
	
	@Column
	private int price;
	
	@Column
	private String description;
	
	@Column
	private int quantity;
	
	@Column
	private String brand;
	
	@Column 
	private int insurance;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@Column
	private int number;
	
	//Duong dan den file luu anh de hien thi len file HTML
	@Transient
	public String getProductImagePath() {
		if(image == null||id==null)
			return null;
		return "/product-image/" + id + "/" + image;
	}
}
