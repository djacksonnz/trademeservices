package com.cdapps.tmservices.models;

import android.widget.ImageView;

import java.util.Date;

/**
 * Created by dave on 10/10/14.
 */
public class ListModelSR {
    private String location;
    private String name;
    private String listedDate;
    private int totalReviews;
    private int reviewRating;
    private String image;
    private boolean featured;
    private int listingId;

    public ListModelSR(String location, String name, String listedDate, int totalReviews,
                       int reviewRating, String image, boolean featured, int listingId)
    {
        this.location = location;
        this.name = name;
        this.listedDate = listedDate;
        this.totalReviews = totalReviews;
        this.reviewRating = reviewRating;
        this.image = image;
        this.featured = featured;
        this.listingId = listingId;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getListedDate() {
        return listedDate;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public String getImage() {
        return image;
    }

    public boolean isFeatured() {
        return featured;
    }

    public int getListingId() {
        return listingId;
    }
}
