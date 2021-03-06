package com.StoreChain.spring.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Department")
public class Department {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	@Column(nullable = true)
	private String Description;
	private int DepartmentKey;
	@Column(nullable = true)
	private Integer Number;
	private int State;
	private int Prod_Id;
	
	public Department() {}
	
	public Department( String description, int departmentKey, Integer number, int state, int prod_Id) {
		super();
		Description = description;
		DepartmentKey = departmentKey;
		Number = number;
		State = state;
		Prod_Id = prod_Id;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public Integer getNumber() {
		return Number;
	}
	public void setNumber(Integer number) {
		Number = number;
	}
	public int getState() {
		return State;
	}
	public void setState(int state) {
		State = state;
	}
	public int getProd_Id() {
		return Prod_Id;
	}
	public void setProd_Id(int prod_Id) {
		Prod_Id = prod_Id;
	}
	public int getDepartmentKey() {
		return DepartmentKey;
	}
	public void setDepartmentKey(int departmentKey) {
		DepartmentKey = departmentKey;
	}	
}
