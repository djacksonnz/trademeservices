/**
 * Listing.java
 * 
 * Created 3/06/2014 David Jackson 
 */

package com.cdapps.tmservices.listing;

import com.cdapps.tmservices.gen.ContactDetails;
import com.cdapps.tmservices.gen.Member;

import java.util.Date;
import java.util.List;

/**
 * @author David Jackson
 * 
 * Class to hold listing information
 */
public class Listing {

	private int listingId;
	private String title;
	private String category;
	private Date startDate;
	private Date endDate;
	private boolean isFeatured;
    private boolean hasGallery;
    private boolean isBold;
    private boolean isHighlighted;
    private boolean hasHomePageFeature;
    private Date asAt;
    private String categoryPath;
    private String photoId;
    private int regionId;
    private String region;
    private String suburb;
    private int viewCount;
    private String categoryName;
	private List<Attribute> attributes;
	private boolean isClassified;
    private int relistedItemId;
    private String subtitle;
    private boolean isOnWatchList;
    private int totalReviewCount;
    private int positiveReviewCount;
	private Member member;
	private String body;
	private List<Photo> photos;
    private ContactDetails contactDetails;
    private boolean withdrawnBySeller;

	
	public Listing(int listingId, String title, String category, Date startDate, Date endDate,
                   boolean isFeatured, boolean hasGallery, boolean isHighlighted, boolean hasHomePageFeature,
                   Date asAt, String categoryPath, String photoId, int regionId, String region,
                   String suburb, int viewCount, String categoryName, boolean isClassified, int relistedItemId,
                   String subtitle, boolean isOnWatchList, int totalReviewCount, int positiveReviewCount,
                   String body, boolean withdrawnBySeller, boolean isBold) {
	    this.listingId = listingId;
        this.title = title;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFeatured = isFeatured;
        this.hasGallery = hasGallery;
        this.isHighlighted = isHighlighted;
        this.hasHomePageFeature = hasHomePageFeature;
        this.asAt = asAt;
        this.categoryPath = categoryPath;
        this.photoId = photoId;
        this.regionId = regionId;
        this.region = region;
        this.suburb = suburb;
        this.viewCount = viewCount;
        this.categoryName = categoryName;
        this.isClassified = isClassified;
        this.relistedItemId = relistedItemId;
        this.subtitle = subtitle;
        this.isOnWatchList = isOnWatchList;
        this.totalReviewCount = totalReviewCount;
        this.positiveReviewCount = positiveReviewCount;
        this.body = body;
        this.withdrawnBySeller = withdrawnBySeller;
        this.isBold = isBold;
	}

    public int getListingId() {
        return listingId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public boolean isHasGallery() {
        return hasGallery;
    }

    public boolean isBold() {
        return isBold;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public boolean isHasHomePageFeature() {
        return hasHomePageFeature;
    }

    public Date getAsAt() {
        return asAt;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public String getPhotoId() {
        return photoId;
    }

    public int getRegionId() {
        return regionId;
    }

    public String getRegion() {
        return region;
    }

    public String getSuburb() {
        return suburb;
    }

    public int getViewCount() {
        return viewCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public boolean isClassified() {
        return isClassified;
    }

    public int getRelistedItemId() {
        return relistedItemId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public boolean isOnWatchList() {
        return isOnWatchList;
    }

    public int getTotalReviewCount() {
        return totalReviewCount;
    }

    public int getPositiveReviewCount() {
        return positiveReviewCount;
    }

    public Member getMember() {
        return member;
    }

    public String getBody() {
        return body;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public boolean isWithdrawnBySeller() {
        return withdrawnBySeller;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }
}
