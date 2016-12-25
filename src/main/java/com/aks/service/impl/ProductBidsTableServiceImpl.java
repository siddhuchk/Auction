package com.aks.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aks.domain.ProductBids;
import com.aks.repository.ProductBidsRepository;
import com.aks.service.ProductBidService;

/**
 * @author karmveer.sharma
 *
 */
@Service
public class ProductBidsTableServiceImpl implements ProductBidService {

	private static final Logger logger = LoggerFactory.getLogger(ProductBidsTableServiceImpl.class);

	@Autowired
	private ProductBidsRepository productBidsTableRepository;

	@Override
	public ProductBids addNewRow(ProductBids productBids) {
		return productBidsTableRepository.save(productBids);
	}

	@Override
	public List<ProductBids> findAll() {
		return productBidsTableRepository.findAll();
	}

	@Override
	public void udpateRow(ProductBids productBids) {
		productBidsTableRepository.save(productBids);
	}

	@Override
	public void deleteRow(ProductBids productBids) {
		productBidsTableRepository.save(productBids);

	}

	@Override
	public List<ProductBids> findByProductId(Long productId) {
		return productBidsTableRepository.findByProductId(productId);
	}

	@Override
	public List<ProductBids> findExpiredBides(LocalDateTime now) {
		//return productBidsTableRepository.findExpiredBides(now);
		return null;
	}

}
