package com.StoreChain.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "CentralStoreCapital")
public class Store {
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@Column(nullable = true)
    public Double Capital;

	@Column(nullable = true)
    public Integer TransactionKey;

	public Store( Double capital, Integer transactionKey) {
		super();
		Capital = capital;
		TransactionKey = transactionKey;
	}

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

	public Integer getTransactionKey() {
		return TransactionKey;
	}

	public void setTransactionKey(Integer transactionKey) {
		TransactionKey = transactionKey;
	}
    
    
}
