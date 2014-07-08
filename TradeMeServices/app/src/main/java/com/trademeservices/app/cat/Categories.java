package com.trademeservices.app.cat;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to hold information about categories
 */

public class Categories {
    private String name;
    private String number;
    private String path;
    private List<Categories> subCats = new ArrayList<Categories>();
    private boolean hasClassifieds;

    public Categories(String name, String number, String path, boolean hasClassifieds)
    {
        this.name = name;
        this.number = number;
        this.path = path;
        this.hasClassifieds = hasClassifieds;
    }

    public void AddSubCat(Categories subCat)
    {
        subCats.add(subCat);
    }

    public boolean GetHasClassifieds() {
        return hasClassifieds;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPath() {
        return path;
    }

    public List<Categories> getSubCats() {
        return subCats;
    }
}
