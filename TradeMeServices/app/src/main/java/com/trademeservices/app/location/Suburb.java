package com.trademeservices.app.location;

import java.util.ArrayList;
import java.util.List;

/*
 * Suburb.java
 * 
 * Created 3/6/14 by David Jackson 
 */

public class Suburb extends Location {

	//List for holding associated suburbs (close by)
	private List<Integer> assocSuburbsID = new ArrayList<Integer>();
	
	//Constructor
	public Suburb(int id, String name) {
		super(id,name);
		
	}

//	/**
//	 * @return the assocSuburbs
//	 */
//	public List<Integer> getAssocSuburbs() {
//		return assocSuburbs;
//	}
//
//	/**
//	 * @param assocSuburbs the assocSuburbs to set
//	 */
//	public void setAssocSuburbs(List<Integer> assocSuburbs) {
//		this.assocSuburbs = assocSuburbs;
//	}
//
//	//Adds single suburb to list
//	public void addSuburb(Suburb suburb)
//	{
//		assocSuburbs.add(suburb);
//	}
}
