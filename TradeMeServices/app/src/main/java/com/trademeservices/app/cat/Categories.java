package com.trademeservices.app.cat;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to hold information about categories
 */

public class Categories {

    //Fields of categories
    private String name;
    private String number;
    private String path;
    private boolean hasClassifieds;
    private boolean isRestricted;
    private boolean hasLegal;
    private String mainCat;

    public Categories(String name, String number, String path, boolean hasClassifieds,
                      boolean isRestricted, boolean hasLegal, String mainCat)
    {
        this.name = name;
        this.number = number;
        this.path = path;
        this.hasClassifieds = hasClassifieds;
        this.isRestricted = isRestricted;
        this.hasLegal = hasLegal;
        this.mainCat = mainCat;
    }

    @Override
    public String toString()
    {
        return name + ", " + number + ", "  + path;
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

    public boolean isRestricted() {
        return isRestricted;
    }

    public boolean isHasLegal() {
        return hasLegal;
    }

    public String getMainCat() {
        return mainCat;
    }
}
