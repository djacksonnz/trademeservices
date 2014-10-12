/**
 * Results.java
 * 
 * Created 4/06/2014 David Jackson 
 */

package com.cdapps.tmservices.search;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author David Jackson
 *
 * Class to hold result item of search
 */
public class Results {

	private int listingId;
	private String title;
	private String category;
	private String picHref;
    private Date startDate;
    private Date endDate;
    private boolean isFeatured;
    private boolean hasGallery;
    private boolean isBold;
    private boolean isHighlighted;
    private boolean hasHomePageFeature;
    private Date asAt;
    private String categoryPath;
    private int regionId;
    private String region;
    private int suburbId;
    private String suburb;
    private boolean isClassified;
    private String subtitle;
    private boolean onWatchList;
    private int totalReviews;
    private int positiveReviews;
	
	public Results(){

	}


    /**
     *
     */
    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicHref() {
        return picHref;
    }

    public void setPicHref(String picHref) {
        this.picHref = picHref;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public boolean isHasGallery() {
        return hasGallery;
    }

    public void setHasGallery(boolean hasGallery) {
        this.hasGallery = hasGallery;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean isBold) {
        this.isBold = isBold;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public boolean isHasHomePageFeature() {
        return hasHomePageFeature;
    }

    public void setHasHomePageFeature(boolean hasHomePageFeature) {
        this.hasHomePageFeature = hasHomePageFeature;
    }

    public Date getAsAt() {
        return asAt;
    }

    public void setAsAt(Date asAt) {
        this.asAt = asAt;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getSuburbId() {
        return suburbId;
    }

    public void setSuburbId(int suburbId) {
        this.suburbId = suburbId;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public boolean isClassified() {
        return isClassified;
    }

    public void setClassified(boolean isClassified) {
        this.isClassified = isClassified;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isOnWatchList() {
        return onWatchList;
    }

    public void setOnWatchList(boolean onWatchList) {
        this.onWatchList = onWatchList;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public int getPositiveReviews() {
        return positiveReviews;
    }

    public void setPositiveReviews(int positiveReviews) {
        this.positiveReviews = positiveReviews;
    }
}
