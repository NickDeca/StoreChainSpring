package com.StoreChain.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreChain.spring.Model.ProductMinQuantity;

public interface ProductMinQuantityRepository extends JpaRepository<ProductMinQuantity, Integer> {

}
