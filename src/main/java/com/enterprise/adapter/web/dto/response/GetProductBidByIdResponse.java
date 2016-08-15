package com.enterprise.adapter.web.dto.response;

import com.enterprise.adapter.domain.ProductBids;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class GetProductBidByIdResponse {
	private ProductBids productBid;

	public ProductBids getProductBid() {
		return productBid;
	}

	public void setProductBid(ProductBids productBid) {
		this.productBid = productBid;
	}

	@Override
	public String toString() {
		return "GetProductBidByIdResponse [productBid=" + productBid + "]";
	}

}
