package com.aks.web.dto.response;

import com.aks.domain.ProductBids;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class CreateProductBidResponse {
	private ProductBids productBid;

	public ProductBids getProductBid() {
		return productBid;
	}

	public void setProductBid(ProductBids productBid) {
		this.productBid = productBid;
	}

	@Override
	public String toString() {
		return "CreateProductBidResponse [productBid=" + productBid + "]";
	}

}
