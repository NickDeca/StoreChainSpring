package com.StoreChain.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreChain.spring.Model.Suppliers;

public interface SuppliersRepository extends JpaRepository<Suppliers, Integer> {
	
}
