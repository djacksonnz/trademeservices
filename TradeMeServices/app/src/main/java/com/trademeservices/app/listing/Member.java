/**
 * Member.java
 * 
 * Created 3/06/2014 David Jackson 
 */

package com.trademeservices.app.listing;

/**
 * @author David Jackson
 *
 * Class to hold member information for a listing
 */
public class Member {

	/**
	 * Fields 
	 */
	
	private int id;
	private String nickname;
	private String suburb;
	private String region;
    private int feedbackCount;
	
	public Member(int id, String nickname, String suburb, String region, int feedbackCount) {
		this.id = id;
		this.nickname = nickname;
		this.suburb = suburb;
		this.region = region;
        this.feedbackCount = feedbackCount;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @return the suburb
	 */
	public String getSuburb() {
		return suburb;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

    public int getFeedbackCount() {
        return feedbackCount;
    }
}
