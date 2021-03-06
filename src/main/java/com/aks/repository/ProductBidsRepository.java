package com.aks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aks.domain.ProductBids;

@Repository
public interface ProductBidsRepository extends
		JpaRepository<ProductBids, Integer> {

	ProductBids findById(Long id);

	List<ProductBids> findByProductId(Long productId);

	List<ProductBids> findAll();

	// @Query("Select * from Product_Bids  where bidStartTime<(:currentTime) and bidStopTime>(:currentTime) ")
	// List<ProductBids> findAllLiveBids(
	// @Param("currentTime") LocalDateTime currentTime);
	//
	// @Query("Select productbids from ProductBids productbids where productbids.bidStopTime < (:now)")
	//List<ProductBids> findExpiredBides(LocalDateTime now);
}
