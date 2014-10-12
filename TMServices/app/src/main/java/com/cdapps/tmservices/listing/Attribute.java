/**
 * Attribute.java
 * 
 * Created 3/06/2014 David Jackson 
 */

package com.cdapps.tmservices.listing;

/**
 * @author David Jackson
 * 
 * Class to hold attribute information of a listing
 *
 */
public class Attribute {

	/**
	 * 
	 */
	
	private String name;
	private String displayName;
	private String value;
	
	public Attribute(String name, String displayName, String value) {
		this.name = name;
		this.displayName = displayName;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
