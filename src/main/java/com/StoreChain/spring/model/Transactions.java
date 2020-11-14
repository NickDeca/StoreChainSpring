package com.StoreChain.spring.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Transactions")
public class Transactions {

    public int Id;
    
    public int RecipientKey ;
    
    public int ProviderKey ;
    
    public Double Capital ;
    
    public Integer ProductKey ;
    
	public Date DateOfTransaction ;
	
    public Integer ProductQuantity ;
    
    public Integer State ;
    
    public String ErrorText ;
    
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
}
