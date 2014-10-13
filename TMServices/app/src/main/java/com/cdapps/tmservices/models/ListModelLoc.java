package com.cdapps.tmservices.models;

/**
 * Created by dave on 13/10/14.
 */
public class ListModelLoc {

    private String name;
    private int number;

    public ListModelLoc (String name, int number)
    {
        this.name = name;
        this.number = number;
    }

    public String getName()
    {
        return name;
    }

    public int getNumber() { return number; }
}
