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

	@Column(name="Supplier_Key")
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
	
	private Integer MaxDisplay;

	@Column(name="DepartmentForeignId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer DepartmentForeignId;

	public int getid() {
		return id;
	}

	public void setid(Integer Id) {
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
	
}
