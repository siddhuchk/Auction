package com.aks.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aks.domain.Bidders;
import com.aks.repository.BiddersRepository;
import com.aks.repository.ProductBidsRepository;
import com.aks.service.BidderService;

/**
 * @author karmveer.sharma
 *
 */
@Service
public class BiddersTableServiceImpl implements BidderService {

	private static final Logger logger = LoggerFactory
			.getLogger(BiddersTableServiceImpl.class);

	@Autowired
	private BiddersRepository biddersTableRepository;

	@Autowired
	private ProductBidsRepository productBidsTableRepository;

	@Override
	public Bidders addNewRow(Bidders bidder) {
		return biddersTableRepository.save(bidder);
	}

	@Override
	public List<Bidders> findAll() {
		return biddersTableRepository.findAll();
	}

	@Override
	public void udpateRow(Bidders bidder) {
		biddersTableRepository.save(bidder);
	}

	@Override
	public void deleteRow(Bidders bidder) {
		biddersTableRepository.delete(bidder);

	}

	@Override
	public List<Bidders> findByBidderUserId(Long userId) {
		return biddersTableRepository.findByBidderUserId(userId);
	}

	@Override
	public List<Bidders> findByProductId(Long productId) {
		return biddersTableRepository.findByProductBidId(productId);
	}

	@Override
	public List<Bidders> getIntermediateWinners() {
		// LocalDateTime currentTime = LocalDateTime.now();
		// List<ProductBids> liveBids = productBidsTableRepository
		// .findAllLiveBids(currentTime);
		// List<Bidders> winners = new ArrayList<Bidders>();
		// for (ProductBids productBid : liveBids) {
		// List<Bidders> bidders = biddersTableRepository
		// .findByProductBidId(productBid.getId());
		// Bidders currentWinner = new Bidders();
		// for (Bidders bidder : bidders) {
		// if (currentWinner.getAmount() < bidder.getAmount()) {
		// currentWinner = bidder;
		// }
		// }
		// winners.add(currentWinner);
		// }
		// return winners;
		return null;
	}
}
