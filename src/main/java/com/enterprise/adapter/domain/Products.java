/**
 * 
 */
package com.enterprise.adapter.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author karmveer.sharma
 *
 */
@Entity
@Table(name = "products")
public class Products  implements Serializable {
	private static final long serialVersionUID = -3028114538116703622L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "imgURL", nullable = false)
	private String imgURL;
	@Column(name = "ownerId", nullable = false)
	private Long ownerId;
	@Column(name = "isSold", nullable = false)
	private boolean isSold;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public long getOwnerId() {
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
		return "Products [isSold=" + isSold + "]";
	}

}
