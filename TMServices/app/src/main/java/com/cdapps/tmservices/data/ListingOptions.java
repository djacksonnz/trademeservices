package com.cdapps.tmservices.data;

import com.cdapps.tmservices.gen.ContactDetails;
import com.cdapps.tmservices.listing.Listing;

/**
 * Created by dave on 16/10/14.
 */
public class ListingOptions {

    private static ListingOptions instance = null;

    private Listing listing;

    private String cat;

    private int regionId;
    private int districtId;
    private String suburb;

    private String about;
    private String areas;
    private String services;
    private String availability;
    private String description;

    private int streetNum;
    private int flat;
    private String street;


    protected ListingOptions() {

    }

    public static ListingOptions getInstance() {
        if (instance == null)
        {
            instance = new ListingOptions();
        }
        return instance;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburbId) {
        this.suburb = suburbId;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
