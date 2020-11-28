package com.StoreChain.spring.Helper;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.StoreChain.spring.Repository.TransactionsRepository;
import com.StoreChain.spring.model.Transactions;

public class TransactionManager {
	@Autowired
	private TransactionsRepository tContext;

	public Transactions AddTransaction(Transactions transaction) {
		tContext.save(transaction);
		
		return GetTransaction(transaction); 
	}

	public Transactions GetTransaction(Transactions transaction) {
		return tContext.getTransaction(transaction.getRecipientKey(), transaction.getProviderKey(), transaction.getProductKey(), transaction.getDateOfTransaction());
	}

}
