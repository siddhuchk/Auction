package com.aks.service;

import java.util.List;

import com.aks.domain.Products;

/**
 * @author karmveer.sharma
 *
 */
public interface ProductService {
	Products addNewRow(Products user);

	Products findById(Long id);
	
	List<Products> findAll();

	void udpateRow(Products user);

	void deleteRow(Products user);

}
