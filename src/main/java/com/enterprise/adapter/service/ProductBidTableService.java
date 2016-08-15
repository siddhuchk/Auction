/**
 * 
 */
package com.enterprise.adapter.service;

import java.time.LocalDateTime;
import java.util.List;

import com.enterprise.adapter.domain.ProductBids;

/**
 * @author karmveer.sharma
 *
 */
public interface ProductBidTableService {
	ProductBids addNewRow(ProductBids user);

	List<ProductBids> findAll();

	List<ProductBids> findByProductId(Long productId);

	void udpateRow(ProductBids user);

	void deleteRow(ProductBids user);
	
	List<ProductBids> findExpiredBides(LocalDateTime now);

}
