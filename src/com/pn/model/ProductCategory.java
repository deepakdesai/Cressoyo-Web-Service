package com.pn.model;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
	int id;
	String name;
	String description;
	List<ProductSubCategory> productSubCategorieList = new ArrayList<>();
	
	public ProductCategory() {
	}
	
	public ProductCategory(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public List<ProductSubCategory> getProductSubCategorieList() {
		return productSubCategorieList;
	}

	public void setProductSubCategorieList(List<ProductSubCategory> productSubCategorieList) {
		this.productSubCategorieList = productSubCategorieList;
	}

	@Override
	public String toString() {
		return "ProductCategory [id=" + id + ", name=" + name + ", description=" + description
				+ ", productSubCategorieList=" + productSubCategorieList + "]";
	}
	
}
