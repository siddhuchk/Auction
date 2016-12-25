package com.aks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aks.domain.Products;

@Repository
public interface ProductRepository extends
		JpaRepository<Products, Integer> {

	Products findById(Long id);

	//Products findByProductId(Long id);

	List<Products> findAll();
}
