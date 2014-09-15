package com.trademeservices.app.data;

import com.trademeservices.app.gen.ContactDetails;

/**
 * Created by jacksdl2 on 15/09/2014.
 */
public class SearchVariables {
    private static SearchVariables instance = null;

    private int district;
    private int region;

    private String category;

    protected SearchVariables() {
        // Exists only to defeat instantiation.
    }
    public static SearchVariables getInstance() {
        if(instance == null) {
            instance = new SearchVariables();
        }
        return instance;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
