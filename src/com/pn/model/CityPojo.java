package com.pn.model;

public class CityPojo {
	int id;
	int countryId;
	int stateId;
	String name;

	public CityPojo() {
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

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CityPojo [id=" + id + ", countryId=" + countryId + ", stateId="
				+ stateId + ", name=" + name + "]";
	}
}
