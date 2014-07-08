package com.trademeservices.app.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * Region.java
 * 
 * Created 3/6/14 by David Jackson 
 */

public class Region extends Location {

	//List to hold all districts for the region
	private List<District> districts = new ArrayList<District>();
	
	//Constructor takes in int and string returns none
	public Region(int id, String name) {
		super(id, name);
	}

	/**
	 * @return the districts
	 */
	public List<District> getDistricts() {
		return districts;
	}

	/**
	 * @param districts the districts to set
	 */
	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}

	//Add single district
	public void addDistrict(District district) {
		districts.add(district);
	}
}
