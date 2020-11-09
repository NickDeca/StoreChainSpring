package com.StoreChain.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Products")
public class Products {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Integer Supplier_Key;
	
	private Integer Category;
	
	private Integer Department;
	
	private String Description;
	
	private Boolean isDisplay;
	
	@Column(name="CostSold")
	private Double CostSold;

	@Column(name="CostBought")   
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Double CostBought;

	@Column(name="QuantityInStorage")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer QuantityInStorage;

	@Column(name="QuantityInDisplay")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer QuantityInDisplay;

	@Column(name="TransactionQuantity")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer TransactionQuantity;

	@Column(name="DepartmentForeignId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer DepartmentForeignId;

	public int getid() {
		return id;
	}

	public void setid(int Id) {
		id = Id;
	}
	
	
	public Integer getSupplier_Key() {
		return Supplier_Key;
	}

	public void setSupplier_Key(int supplier_Key) {
		Supplier_Key = supplier_Key;
	}

	public Integer getCategory() {
		return Category;
	}

	public void setCategory(int category) {
		Category = category;
	}

	public Integer getDepartment() {
		return Department;
	}

	public void setDepartment(int department) {
		Department = department;
	}

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Double getCostSold() {
		return CostSold;
	}

	public void setCostSold(double costSold) {
		CostSold = costSold;
	}

	public Double getCostBought() {
		return CostBought;
	}

	public void setCostBought(double costBought) {
		CostBought = costBought;
	}

	public Integer getQuantityInStorage() {
		return QuantityInStorage;
	}

	public void setQuantityInStorage(int quantityInStorage) {
		QuantityInStorage = quantityInStorage;
	}

	public Integer getQuantityInDisplay() {
		return QuantityInDisplay;
	}

	public void setQuantityInDisplay(int quantityInDisplay) {
		QuantityInDisplay = quantityInDisplay;
	}

	public Integer getTransactionQuantity() {
		return TransactionQuantity;
	}

	public void setTransactionQuantity(int transactionQuantity) {
		TransactionQuantity = transactionQuantity;
	}

	public Integer getDepartmentForeignId() {
		return DepartmentForeignId;
	}

	public void setDepartmentForeignId(int departmentForeignId) {
		DepartmentForeignId = departmentForeignId;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
}
