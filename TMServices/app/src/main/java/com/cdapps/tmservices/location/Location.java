package com.cdapps.tmservices.location;

/*
 * Location.java
 * 
 * Created 3/6/14 by David Jackson 
 */

public abstract class Location {
	
	//Values to come from sub classes
	private int id;
	private String name;
	
	//Constructor, takes int String, no return
	public Location(int id, String name)
	{
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

    @Override
    public String toString()
    {
        return name;
    }
	
}
