package com.StoreChain.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.StoreChain.spring.model.Suppliers;

public interface SuppliersRepository extends JpaRepository<Suppliers, Integer> {
	
}
