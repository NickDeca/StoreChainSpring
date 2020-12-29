package com.StoreChain.spring.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.StoreChain.spring.Helper.ResupplyHelperClass;
import com.StoreChain.spring.Model.*;

public interface ProductRepository extends JpaRepository<Products, Integer> {

	@Query(value = "SELECT new com.StoreChain.spring.Helper.ResupplyHelperClass(?1, MinStorage)"
					+ " FROM ProductMinQuantity  " 
					+ " WHERE Id = ?1 AND ?2 < MinStorage" , 
					  nativeQuery = true)
	List<ResupplyHelperClass> CheckIfNeedReSupply(int productId, int quantityinStorage);
}

