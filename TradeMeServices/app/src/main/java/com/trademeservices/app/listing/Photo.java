/**
 * Photo.java
 * 
 * Created 3/06/2014 David Jackson 
 */

package com.trademeservices.app.listing;

/**
 * @author David Jackson
 * 
 * Class to hold information about photos of a listing
 *
 */
public class Photo {

	/**
	 * Fields for image locations
	 */
	
	private int key;
	private int id;
	private String thumb;
	private String list;
	private String med;
	private String gal;
	private String lge;
	private String full;
	
	//Constructor
	public Photo(int key, int id, String thumb, String list, String med,
			String gal, String lge, String full) {
		this.key = key;
		this.id = id;
		this.thumb = thumb;
		this.list = list;
		this.med = med;
		this.gal = gal;
		this.lge = lge;
		this.full = full;
	}

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the thumb
	 */
	public String getThumb() {
		return thumb;
	}

	/**
	 * @return the list
	 */
	public String getList() {
		return list;
	}

	/**
	 * @return the med
	 */
	public String getMed() {
		return med;
	}

	/**
	 * @return the gal
	 */
	public String getGal() {
		return gal;
	}

	/**
	 * @return the lge
	 */
	public String getLge() {
		return lge;
	}

	/**
	 * @return the full
	 */
	public String getFull() {
		return full;
	}

}
