package com.trademeservices.app.search;

/**
 * Created by jacksdl2 on 15/09/2014.
 */
public class SearchCounts {
    private int count;
    private String category;

    public SearchCounts(int count, String category)
    {
        this.count = count;
        this.category = category;
    }

    public int getCount () {
        return count;
    }

    public String getCat() {
        return category;
    }
}
