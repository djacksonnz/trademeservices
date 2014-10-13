package com.cdapps.tmservices.models;

/**
 * Created by dave on 13/10/14.
 */
public class ListModelCat {
    private String catName;
    private String catNumber;
    private int results;

    public ListModelCat(String catName, String catNumber, int results) {
        this.catName = catName;
        this.catNumber = catNumber;
        this.results = results;
    }

    public String getCatName() {
        return catName;
    }

    public String getCatNumber() {
        return catNumber;
    }

    public int getResults() {
        return results;
    }
}
