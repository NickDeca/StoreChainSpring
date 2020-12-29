package com.StoreChain.spring.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.StoreChain.spring.Model.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {

	@Query(value = "SELECT TOP 1 * FROM Transactions where RecipientKey = ?1 AND [ProviderKey] = ?2 AND [ProductKey] = ?3 AND [DateOfTransaction] = ?4", nativeQuery = true)
	Transactions getTransaction(int recipient, int provider, Integer product, Date dateTransaction);

	@Query(value = "SELECT TOP 10 * FROM Transactions ORDER BY ID DESC", nativeQuery = true)
	List<Transactions> getLastTenTransactions();
}
