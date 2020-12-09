package com.StoreChain.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Customers")
public class Customers {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@Column(nullable = true)
    private Double Capital;

	@Column(nullable = true)
    private String Description;
    
    @Column(name = "First_Name", nullable = true)
    private String FirstName ;
    
    @Column(name = "Last_Name", nullable = true)
    private String LastName;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Double getCapital() {
		return Capital;
	}

	public void setCapital(Double capital) {
		Capital = capital;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}
    
}
