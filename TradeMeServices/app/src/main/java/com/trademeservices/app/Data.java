package com.trademeservices.app;

import com.trademeservices.app.cat.Categories;
import com.trademeservices.app.listing.Listing;
import com.trademeservices.app.location.Region;
import com.trademeservices.app.search.SearchResults;

import java.util.ArrayList;
import java.util.List;

/**
 * Data class used for processing and holding data is singleton so can be accessed app wide
 */
public class Data {

    private static Data instance = null;
    private List<Region> regionList = new ArrayList<Region>();
    private SearchResults results;
    private List<Categories> categories  = new ArrayList<Categories>();
    private Listing listing;
    private Variables variables = new Variables();

    protected Data(){}

    public static  Data getInstance()
    {
        if (instance == null)
            instance = new Data();

        return instance;
    }


    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }

    public void addRegionList(Region reg)
    {
        regionList.add(reg);
    }

    public SearchResults getResults() {
        return results;
    }

    public void setResults(SearchResults results) {
        this.results = results;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public void addCategories(Categories cat)
    {
        categories.add(cat);
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }
}
