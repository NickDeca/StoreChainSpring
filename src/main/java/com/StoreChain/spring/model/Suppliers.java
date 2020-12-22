package com.StoreChain.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Suppliers")
public class Suppliers {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@Column(name = "Payment_Due",nullable = true)
    public Double Payment_Due;

	@Column(nullable = true)
	public Integer Category;

	@Column(nullable = true)
    public String Description;

	@Column(nullable = true)
    public String Name; 

    public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public Double getPayment_Due() {
		return Payment_Due;
	}

	public void setPayment_Due(Double payment_Due) {
		Payment_Due = payment_Due;
	}

	public Integer getCategory() {
		return Category;
	}

	public void setCategory(Integer category) {
		Category = category;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
