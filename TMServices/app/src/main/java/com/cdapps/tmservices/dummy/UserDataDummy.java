package com.cdapps.tmservices.dummy;

import com.cdapps.tmservices.Member;
import com.cdapps.tmservices.gen.ContactDetails;
import com.cdapps.tmservices.listing.Attribute;
import com.cdapps.tmservices.listing.Listing;
import com.cdapps.tmservices.listing.Photo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dave on 15/10/14.
 */
public class UserDataDummy {

    private static UserDataDummy instance = null;

    private List<Listing> myListings = new ArrayList<Listing>();
    private List<Integer> myWatchlist = new ArrayList<Integer>();

    protected UserDataDummy() {
        myWatchlist.add(721027156);
        myWatchlist.add(724549825);

        Listing l = new Listing(1000000001, "Randys Toolshed", "9334-9349-9357-", new Date(2014, 9, 10),
                new Date(2016,12,21), true, false, false, false, new Date(), "/Services/Domestic-services/Repairs-maintenance",
                "0", 10, "Otago", "Dunedin", 100, "Repairs & maintenance", true, 0, " ", false, 0, 0, "I fix things with my tools very fast \n\n rates starting at $50 per half hour \n\n email or call for more information",
                false, false);

        List <Attribute> lAttributes =  new ArrayList<Attribute>();
        lAttributes.add(new Attribute("business name", "Business Name", "Randys Repairz"));
        lAttributes.add(new Attribute("area", "Areas Serviced", "Dunedin and Otago"));

        l.setAttributes(lAttributes);

        com.cdapps.tmservices.gen.Member member = new com.cdapps.tmservices.gen.Member(5000000, "auser", "Waverley", "Otago", 1000, new Date(), new Date(), 1, 999, false, false);
        l.setMember(member);

        List<Photo> photos = new ArrayList<Photo>();
        photos.add(new Photo(1,"http://thumb1.shutterstock.com/display_pic_with_logo/116932/198169034/stock-photo-great-idea-concept-with-crumpled-colorful-paper-and-light-bulb-on-wooden-table-198169034.jpg", "http://thumb1.shutterstock.com/display_pic_with_logo/116932/198169034/stock-photo-great-idea-concept-with-crumpled-colorful-paper-and-light-bulb-on-wooden-table-198169034.jpg",
                "http://thumb1.shutterstock.com/display_pic_with_logo/116932/198169034/stock-photo-great-idea-concept-with-crumpled-colorful-paper-and-light-bulb-on-wooden-table-198169034.jpg", "http://thumb1.shutterstock.com/display_pic_with_logo/116932/198169034/stock-photo-great-idea-concept-with-crumpled-colorful-paper-and-light-bulb-on-wooden-table-198169034.jpg", "http://thumb1.shutterstock.com/display_pic_with_logo/116932/198169034/stock-photo-great-idea-concept-with-crumpled-colorful-paper-and-light-bulb-on-wooden-table-198169034.jpg", "http://thumb1.shutterstock.com/display_pic_with_logo/116932/198169034/stock-photo-great-idea-concept-with-crumpled-colorful-paper-and-light-bulb-on-wooden-table-198169034.jpg"));

        l.setPhotos(photos);

        myListings.add(l);
    }

    public static UserDataDummy getInstance() {
        if (instance == null) {
            instance = new UserDataDummy();
        }
        return instance;
    }

    public List<Listing> getMyListings() {
        return myListings;
    }

    public void setMyListings(List<Listing> myListings) {
        this.myListings = myListings;
    }

    public List<Integer> getMyWatchlist() {
        return myWatchlist;
    }

    public void setMyWatchlist(List<Integer> myWatchlist) {
        this.myWatchlist = myWatchlist;
    }

    public void addToWatchlist(int item) {
        myWatchlist.add(item);
    }

    public void addMyListing(Listing l) {
        myListings.add(l);
    }

    public void removeFromWatchlist(int item) {
        myWatchlist.remove((Integer) item);
    }

    public void removeMyListing(int id) {
        for (Listing l : myListings)
        {
            if (l.getListingId() == id) {
                myListings.remove(l);
                break;
            }
        }
    }
}
