package com.trademeservices.app.disp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;

import com.trademeservices.app.R;

/**
 * Created by jacksdl2 on 17/09/2014.
 */
public class ListModelCat {
    private String name;
    private int listings;

    public ListModelCat(String name, int listings)
    {
        this.name = name;
        this.listings = listings;
    }

    public String getName() {
        return name;
    }

    public int getListings() {
        return listings;
    }
}
