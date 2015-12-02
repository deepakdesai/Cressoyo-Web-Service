package com.pn.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductSubCategory {
	int id;
	int catId;
	String name;
	String description;
	List<Product> productList = new ArrayList<>();
	byte[] img;
	
	public ProductSubCategory() {
	}

	public ProductSubCategory(int catId, String name, String description) {
		this.catId = catId;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "ProductSubCategory [id=" + id + ", catId=" + catId + ", name=" + name + ", description=" + description
				+ ", productList=" + productList + ", img=" + Arrays.toString(img) + "]";
	}
	
}
