/**
 * Member.java
 * 
 * Created 3/06/2014 David Jackson 
 */

package com.trademeservices.app.gen;

import java.util.Date;

/**
 * @author David Jackson
 *
 * Class to hold member information for a listing
 */
public class Member {

	private int memberId;
	private String nickname;
    private Date addressVerifiedDate;
    private Date joinDate;
    private int uniqueNegative;
    private int uniquePositive;
    private int feedBackCount;
    private boolean isAddressVerified;
	private String suburb;
	private String region;
    private int feedbackCount;
    private boolean isAuthenticated;
	
	public Member(int id, String nickname, String suburb, String region, int feedbackCount,
                  Date addressVerifiedDate, Date joinDate, int uniqueNegative, int uniquePositive,
                  boolean isAddressVerified, boolean isAuthenticated) {
		this.memberId = id;
		this.nickname = nickname;
		this.suburb = suburb;
		this.region = region;
        this.feedbackCount = feedbackCount;
        this.addressVerifiedDate = addressVerifiedDate;
        this.joinDate = joinDate;
        this.uniqueNegative = uniqueNegative;
        this.uniquePositive = uniquePositive;
        this.isAddressVerified = isAddressVerified;
        this.isAuthenticated = isAuthenticated;
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

    /**
     * Fields
     */
    public int getMemberId() {
        return memberId;
    }

    public Date getAddressVerifiedDate() {
        return addressVerifiedDate;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public int getUniqueNegative() {
        return uniqueNegative;
    }

    public int getUniquePositive() {
        return uniquePositive;
    }

    public boolean isAddressVerified() {
        return isAddressVerified;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}
