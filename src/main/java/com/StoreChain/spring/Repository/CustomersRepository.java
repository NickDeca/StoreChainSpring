package com.StoreChain.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreChain.spring.Model.Customers;


public interface CustomersRepository extends JpaRepository<Customers, Integer> {
	
	
}

