package com.aks.web.dto.response;

import com.aks.domain.Bidders;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class GetBidderByIdResponse {
	private Bidders bidder;

	public Bidders getBidder() {
		return bidder;
	}

	public void setBidder(Bidders bidder) {
		this.bidder = bidder;
	}

	@Override
	public String toString() {
		return "GetBidderByIdResponse [bidder=" + bidder + "]";
	}

}
