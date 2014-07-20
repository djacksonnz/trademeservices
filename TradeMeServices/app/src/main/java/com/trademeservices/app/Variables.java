package com.trademeservices.app;

/**
 * Variables to be used across the application
 */

public class Variables {
    //Change this between http://api.trademe.co.nz/v1/ for full site
    //and https://api.tmsandbox.co.nz/v1/ for the testing sandbox
    private final String BASE_ADDR = "http://api.trademe.co.nz/v1/";
    private final int PAGE_SIZE = 25;
    private int deviceWidth;
    private int deviceHeight;

    public int getDeviceWidth() { return deviceWidth;}
    public int getDeviceHeight() { return deviceHeight; }

    public void setDeviceHeight(int deviceHeight) {
        this.deviceHeight = deviceHeight;
    }

    public void setDeviceWidth(int deviceWidth) {
        this.deviceWidth = deviceWidth;
    }

    public String getBASE_ADDR() {
        return BASE_ADDR;
    }
    public int getPAGE_SIZE() { return PAGE_SIZE; }
}
