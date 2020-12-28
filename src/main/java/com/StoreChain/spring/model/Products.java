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

	@Column(name="Supplier_Key",nullable = true)
	private Integer Supplier_Key;

	@Column(nullable = true)
	private Integer Category;

	@Column(nullable = true)
	private Integer Department;

	@Column(nullable = true)
	private String Description;

	@Column(nullable = true)
	private Boolean isDisplay;
	
	@Column(name="CostSold",nullable = true)
	private Double CostSold;

	@Column(name="CostBought",nullable = true)   
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Double CostBought;

	@Column(name="QuantityInStorage",nullable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer QuantityInStorage;

	@Column(name="QuantityInDisplay",nullable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer QuantityInDisplay;

	@Column(name="TransactionQuantity",nullable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer TransactionQuantity;

	@Column(nullable = true)
	private Integer MaxDisplay;
	
	@Column(nullable = true)
	private Integer MinStorage;

	@Column(name="DepartmentForeignId",nullable = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer DepartmentForeignId;

	public Products() {}
	
	public Products(int id, Integer supplier_Key, Integer category, Integer department, String description,
			Boolean isDisplay, Double costSold, Double costBought, Integer quantityInStorage, Integer quantityInDisplay,
			Integer transactionQuantity, Integer maxDisplay, Integer minStorage, Integer departmentForeignId) {
		super();
		this.id = id;
		Supplier_Key = supplier_Key;
		Category = category;
		Department = department;
		Description = description;
		this.isDisplay = isDisplay;
		CostSold = costSold;
		CostBought = costBought;
		QuantityInStorage = quantityInStorage;
		QuantityInDisplay = quantityInDisplay;
		TransactionQuantity = transactionQuantity;
		MaxDisplay = maxDisplay;
		MinStorage = minStorage;
		DepartmentForeignId = departmentForeignId;
	}

	public int getid() {
		return id;
	}

	public void setid(int Id) {
		id = Id;
	}
	
	
	public Integer getSupplier_Key() {
		return Supplier_Key;
	}

	public void setSupplier_Key(Integer supplier_Key) {
		Supplier_Key = supplier_Key;
	}

	public Integer getCategory() {
		return Category;
	}

	public void setCategory(Integer category) {
		Category = category;
	}

	public Integer getDepartment() {
		return Department;
	}

	public void setDepartment(Integer department) {
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

	public void setCostSold(Double costSold) {
		CostSold = costSold;
	}

	public Double getCostBought() {
		return CostBought;
	}

	public void setCostBought(Double costBought) {
		CostBought = costBought;
	}

	public Integer getQuantityInStorage() {
		return QuantityInStorage;
	}

	public void setQuantityInStorage(Integer quantityInStorage) {
		QuantityInStorage = quantityInStorage;
	}

	public Integer getQuantityInDisplay() {
		return QuantityInDisplay;
	}

	public void setQuantityInDisplay(Integer quantityInDisplay) {
		QuantityInDisplay = quantityInDisplay;
	}

	public Integer getTransactionQuantity() {
		return TransactionQuantity;
	}

	public void setTransactionQuantity(Integer transactionQuantity) {
		TransactionQuantity = transactionQuantity;
	}

	public Integer getDepartmentForeignId() {
		return DepartmentForeignId;
	}

	public void setDepartmentForeignId(Integer departmentForeignId) {
		DepartmentForeignId = departmentForeignId;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Integer getMaxDisplay() {
		return MaxDisplay;
	}

	public void setMaxDisplay(Integer maxDisplay) {
		MaxDisplay = maxDisplay;
	}

	public Integer getMinStorage() {
		return MinStorage;
	}

	public void setMinStorage(Integer minStorage) {
		MinStorage = minStorage;
	}
	
}
