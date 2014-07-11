package com.trademeservices.app;

/**
 * Constants to be used across the application
 */

public class Constants {
    //Change this between http://api.trademe.co.nz/v1/ for full site
    //and https://api.tmsandbox.co.nz/v1/ for the testing sandbox
    private final String BASE_ADDR = "https://api.tmsandbox.co.nz/v1/";

    public String getBASE_ADDR() {
        return BASE_ADDR;
    }
}
