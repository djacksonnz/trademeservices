/**
 * Listing.java
 * 
 * Created 3/06/2014 David Jackson 
 */

package com.trademeservices.app.listing;

import java.util.List;

/**
 * @author David Jackson
 * 
 * Class to hold listing information
 */
public class Listing {

	/**
	 * 
	 */
	
	private int id;
	private String title;
	private String cat;
	private int startPrice;
	private String startDate;
	private String endDate;
	private boolean featured;
	private List<Attribute> attributes;
	private int regionID;
	private String region;
	private String suburb;
	private String catName;
	private int reserveState;
	private boolean classified;
	private Member member;
	private String body;
	private List<Photo> photos;
	
	public Listing() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the cat
	 */
	public String getCat() {
		return cat;
	}

	/**
	 * @param cat the cat to set
	 */
	public void setCat(String cat) {
		this.cat = cat;
	}

	/**
	 * @return the startPrice
	 */
	public int getStartPrice() {
		return startPrice;
	}

	/**
	 * @param startPrice the startPrice to set
	 */
	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the featured
	 */
	public boolean isFeatured() {
		return featured;
	}

	/**
	 * @param featured the featured to set
	 */
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	/**
	 * @return the attributes
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the regionID
	 */
	public int getRegionID() {
		return regionID;
	}

	/**
	 * @param regionID the regionID to set
	 */
	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the suburb
	 */
	public String getSuburb() {
		return suburb;
	}

	/**
	 * @param suburb the suburb to set
	 */
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	/**
	 * @return the catName
	 */
	public String getCatName() {
		return catName;
	}

	/**
	 * @param catName the catName to set
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}

	/**
	 * @return the reserveState
	 */
	public int getReserveState() {
		return reserveState;
	}

	/**
	 * @param reserveState the reserveState to set
	 */
	public void setReserveState(int reserveState) {
		this.reserveState = reserveState;
	}

	/**
	 * @return the classified
	 */
	public boolean isClassified() {
		return classified;
	}

	/**
	 * @param classified the classified to set
	 */
	public void setClassified(boolean classified) {
		this.classified = classified;
	}

	/**
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the photos
	 */
	public List<Photo> getPhotos() {
		return photos;
	}

	/**
	 * @param photos the photos to set
	 */
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

}
