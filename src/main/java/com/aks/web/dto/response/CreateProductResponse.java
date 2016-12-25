package com.aks.web.dto.response;

import com.aks.domain.Products;

/**
 * 
 * @author anuj.kumar2
 *
 */
public class CreateProductResponse {
	private Products product;

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "CreateProductResponse [product=" + product + "]";
	}

}
