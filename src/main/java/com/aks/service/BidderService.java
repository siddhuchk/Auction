package com.aks.service;

import java.util.List;

import com.aks.domain.Bidders;

/**
 * @author karmveer.sharma
 *
 */
public interface BidderService {
	Bidders addNewRow(Bidders user);

	List<Bidders> findAll();

	List<Bidders> findByBidderUserId(Long userId);

	List<Bidders> findByProductId(Long productId);

	List<Bidders> getIntermediateWinners();

	void udpateRow(Bidders user);

	void deleteRow(Bidders user);

}
