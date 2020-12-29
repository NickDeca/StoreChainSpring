package com.StoreChain.spring.Helper;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.StoreChain.spring.Model.Transactions;
import com.StoreChain.spring.Repository.TransactionsRepository;

public class TransactionManager {

	public Transactions AddTransaction(Transactions transaction, TransactionsRepository tContext) {
		tContext.save(transaction);
		//Transactions saved = GetTransaction(transaction, tContext); 
		return transaction;
	}

	public Transactions GetTransaction(Transactions transaction, TransactionsRepository tContext) {
		return tContext.getTransaction(transaction.getRecipientKey(), transaction.getProviderKey(), transaction.getProductKey(), transaction.getDateOfTransaction());
	}

}
