package com.pn.model;

import java.util.List;

public class StatePojo {
	int id;
	int countryId;
	String name;
	List<CityPojo> cityList;
	
	public List<CityPojo> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityPojo> cityList) {
		this.cityList = cityList;
	}

	public StatePojo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int stateId) {
		this.countryId = stateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "StatePojo [id=" + id + ", stateId=" + countryId + ", name="
				+ name + "]";
	}
}
