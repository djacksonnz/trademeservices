package com.trademeservices.app.location;

import java.util.ArrayList;
import java.util.List;

/*
 * District.java
 * 
 * Created 3/6/14 by David Jackson 
 */

public class District extends Location{

	//List to hold suburbs in district
	private List<Suburb> suburbs = new ArrayList<Suburb>();
	
	public District(int id, String name) {
		super (id, name);
	}

	/**
	 * @return the suburbs
	 */
	public List<Suburb> getSuburbs() {
		return suburbs;
	}

	/**
	 * @param suburbs the suburbs to set
	 */
	public void setSuburbs(List<Suburb> suburbs) {
		this.suburbs = suburbs;
	}
	
	//Add single suburb to list
	public void addSuburb(Suburb suburb)
	{
		suburbs.add(suburb);
	}

}
