package com.aks.service;

import java.time.LocalDateTime;
import java.util.List;

import com.aks.domain.ProductBids;

/**
 * @author karmveer.sharma
 *
 */
public interface ProductBidService {
	ProductBids addNewRow(ProductBids user);

	List<ProductBids> findAll();

	List<ProductBids> findByProductId(Long productId);

	void udpateRow(ProductBids user);

	void deleteRow(ProductBids user);
	
	List<ProductBids> findExpiredBides(LocalDateTime now);

}
