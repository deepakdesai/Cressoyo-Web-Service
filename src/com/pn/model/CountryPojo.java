package com.pn.model;

import java.util.List;

public class CountryPojo {
	int id;
	String name;
	List<StatePojo> stateList;
	
	public List<StatePojo> getStateList() {
		return stateList;
	}

	public void setStateList(List<StatePojo> stateList) {
		this.stateList = stateList;
	}

	public CountryPojo() {
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

	@Override
	public String toString() {
		return "CountryPojo [id=" + id + ", name=" + name + "]";
	}
	
}
