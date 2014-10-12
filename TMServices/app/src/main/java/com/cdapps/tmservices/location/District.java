package com.cdapps.tmservices.location;

import java.util.ArrayList;
import java.util.List;

/*
 * District.java
 * 
 * Created 3/6/14 by David Jackson 
 */

public class District extends Location{

    private int regionId;
	
	public District(int id, String name, int regionId) {
		super (id, name);
        this.regionId = regionId;
	}

    public int GetRegionId()
    {
        return regionId;
    }
}
