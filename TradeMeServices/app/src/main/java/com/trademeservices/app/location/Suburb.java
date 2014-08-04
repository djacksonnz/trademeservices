package com.trademeservices.app.location;

/**
 * Created by jacksdl2 on 4/08/2014.
 */
public class Suburb extends Location {

    private int districtId;

    public Suburb(int id, String name, int districtId)
    {
        super(id,name);
        this.districtId = districtId;
    }

    public int GetDistrictId()
    {
        return districtId;
    }
}
