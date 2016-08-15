/**
 * 
 */
package com.enterprise.adapter.web.dto.request;

/**
 * @author anuj.kumar2
 *
 */
public class CreateProductRequest {
	private String name;
	private String imgURL;
	private Long ownerId;
	private boolean isSold;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isSold() {
		return isSold;
	}

	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}

	@Override
	public String toString() {
		return "CreateProductRequest [name=" + name + ", imgURL=" + imgURL
				+ ", ownerId=" + ownerId + ", isSold=" + isSold + "]";
	}

}
