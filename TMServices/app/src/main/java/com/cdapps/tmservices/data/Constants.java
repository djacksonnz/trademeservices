package com.cdapps.tmservices.data;

/**
 * Variables to be used across the application
 */

public class Constants {
    //Change this between http://api.trademe.co.nz/v1/ for full site
    //and https://api.tmsandbox.co.nz/v1/ for the testing sandbox
    private final String BASE_ADDR = "https://api.trademe.co.nz/v1/";
    private final int PAGE_SIZE = 25;
    private final String CONSUMER_KEY = "C0B2EFD4BA0897792D90EC41E4AC12A7";
    private final String CONSUMER_SECRET = "3F936A7ABECBB2B75D3C7CDBFEDFED1A";


    public String getBASE_ADDR() {
        return BASE_ADDR;
    }
    public int getPAGE_SIZE() { return PAGE_SIZE; }

    public String getCONSUMER_KEY() {
        return CONSUMER_KEY;
    }

    public String getCONSUMER_SECRET() {
        return CONSUMER_SECRET;
    }
}
