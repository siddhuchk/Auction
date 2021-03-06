/**
 * 
 */
package com.aks.web.dto.response;

import java.util.List;

import com.aks.domain.Products;

/**
 * @author karmveer.sharma
 *
 */
public class GetAllProductsResponse {
	private List<Products> products;

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "GetAllProductsResponse [products=" + products + "]";
	}

}
