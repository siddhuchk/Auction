/**
 * 
 */
package com.enterprise.adapter.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.adapter.domain.Products;
import com.enterprise.adapter.repository.ProductTableRepository;
import com.enterprise.adapter.service.ProductTableService;

/**
 * @author karmveer.sharma
 *
 */
@Service
public class ProductsTableServiceImpl implements ProductTableService {

	private static final Logger logger = LoggerFactory
			.getLogger(ProductsTableServiceImpl.class);

	@Autowired
	private ProductTableRepository productTableRepository;

	@Override
	public Products addNewRow(Products product) {
		return productTableRepository.save(product);
	}

	@Override
	public List<Products> findAll() {
		return productTableRepository.findAll();
	}

	@Override
	public void udpateRow(Products product) {
		productTableRepository.save(product);

	}

	@Override
	public void deleteRow(Products product) {
		productTableRepository.delete(product);

	}

	@Override
	public Products findById(Long id) {
		return productTableRepository.findById(id);
	}

}
