package com.cdapps.tmservices.data;

/**
 * Variables to be used across the application
 */

public class Constants {
    //Change this between http://api.trademe.co.nz/v1/ for full site
    //and https://api.tmsandbox.co.nz/v1/ for the testing sandbox
    private final String BASE_ADDR = "http://api.trademe.co.nz/v1/";
    private final int PAGE_SIZE = 25;


    public String getBASE_ADDR() {
        return BASE_ADDR;
    }
    public int getPAGE_SIZE() { return PAGE_SIZE; }
}
