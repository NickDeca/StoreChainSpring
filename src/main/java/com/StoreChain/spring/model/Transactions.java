package com.StoreChain.spring.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Transactions")
public class Transactions {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public int Id;
    
    public int RecipientKey ;
    
    public int ProviderKey ;

	@Column(nullable = true)
    public Double Capital ;

	@Column(nullable = true)
    public Integer ProductKey ;

	@Column(nullable = true)
	public Date DateOfTransaction ;

	@Column(nullable = true)
    public Integer ProductQuantity ;

	@Column(nullable = true)
    public Integer State ;

	@Column(nullable = true)
    public String ErrorText ;
	
	@Column(nullable = true)
    public String Type ;
    
    public Transactions(int recipientKey, int providerKey, Double capital, Integer productKey,
			Date dateOfTransaction, Integer productQuantity, Integer state, String errorText, String type) {
		super();
		RecipientKey = recipientKey;
		ProviderKey = providerKey;
		Capital = capital;
		ProductKey = productKey;
		DateOfTransaction = dateOfTransaction;
		ProductQuantity = productQuantity;
		State = state;
		ErrorText = errorText;
		Type = type;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getRecipientKey() {
		return RecipientKey;
	}
	public void setRecipientKey(int recipientKey) {
		RecipientKey = recipientKey;
	}
	public int getProviderKey() {
		return ProviderKey;
	}
	public void setProviderKey(int providerKey) {
		ProviderKey = providerKey;
	}
	public Double getCapital() {
		return Capital;
	}
	public void setCapital(Double capital) {
		Capital = capital;
	}
	public Integer getProductKey() {
		return ProductKey;
	}
	public void setProductKey(Integer productKey) {
		ProductKey = productKey;
	}
	public Date getDateOfTransaction() {
		return DateOfTransaction;
	}
	public void setDateOfTransaction(Date dateOfTransaction) {
		DateOfTransaction = dateOfTransaction;
	}
	public Integer getProductQuantity() {
		return ProductQuantity;
	}
	public void setProductQuantity(Integer productQuantity) {
		ProductQuantity = productQuantity;
	}
	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	public String getErrorText() {
		return ErrorText;
	}
	public void setErrorText(String errorText) {
		ErrorText = errorText;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
}
