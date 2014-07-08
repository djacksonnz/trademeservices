/**
 * Attribute.java
 * 
 * Created 3/06/2014 David Jackson 
 */

package com.trademeservices.app.listing;

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
	private String encodedValue;
	
	public Attribute(String name, String displayName, String value, String encodedValue) {
		this.name = name;
		this.displayName = displayName;
		this.value = value;
		this.encodedValue = encodedValue;
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

	/**
	 * @return the encodedValue
	 */
	public String getEncodedValue() {
		return encodedValue;
	}

}
