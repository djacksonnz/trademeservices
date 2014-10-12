package com.cdapps.tmservices.gen;

/**
 * Created by jacksdl2 on 11/08/2014.
 */
public class ContactDetails {
    private String contactName;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private String bestContactTime;

    public ContactDetails(String contactName, String phoneNumber, String mobilePhoneNumber,
                          String bestContactTime)
    {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.bestContactTime = bestContactTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public String getBestContactTime() {
        return bestContactTime;
    }
}
