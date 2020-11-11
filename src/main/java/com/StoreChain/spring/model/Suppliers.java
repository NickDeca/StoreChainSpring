package com.StoreChain.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Suppliers")
public class Suppliers {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

    public Double PaymentDue;
    
	public Integer Category;
    
    public String Description;
    
    public String Name; 

    public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public Double getPaymentDue() {
		return PaymentDue;
	}

	public void setPaymentDue(Double paymentDue) {
		PaymentDue = paymentDue;
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
