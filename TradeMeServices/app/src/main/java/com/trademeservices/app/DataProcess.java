package com.trademeservices.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.trademeservices.app.cat.Categories;
import com.trademeservices.app.listing.Attribute;
import com.trademeservices.app.listing.Listing;
import com.trademeservices.app.listing.Member;
import com.trademeservices.app.listing.Photo;
import com.trademeservices.app.location.District;
import com.trademeservices.app.location.Region;
import com.trademeservices.app.search.Results;
import com.trademeservices.app.search.SearchResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacksdl2 on 11/07/2014.
 */
public class DataProcess {

    private Data _data = Data.getInstance();

    public void ProcessLocations(JSONArray data) throws JSONException {

        for (int i = 0; i < data.length(); i++)
        {
            JSONObject regionData = data.getJSONObject(i);
            Region reg = new Region(regionData.getInt("LocalityId"), regionData.getString("Name"));

            JSONArray districtObj = regionData.getJSONArray("Districts");

            for (int j = 0; j < districtObj.length(); j++)
            {
                JSONObject districtData = districtObj.getJSONObject(j);
                District dis = new District(districtData.getInt("DistrictId"),
                        districtData.getString("Name"));

                reg.addDistrict(dis);
            }
            _data.addRegionList(reg);
        }

//        for (Region r : _data.getRegionList())
//        {
//            Log.i("out",r.getName());
//            for (District d : r.getDistricts())
//            {
//                Log.i("out", "      " + d.getName());
//                for (Suburb s : d.getSuburbs())
//                {
//                    Log.i("out", "          " + s.getName());
//                }
//            }
//        }
    }

    public void ProcessSearchResults(JSONObject data) throws JSONException
    {

        Log.i("out", "Total Results: " + Integer.toString(data.getInt("TotalCount")));
        _data.setResults(new SearchResults(data.getInt("TotalCount"), data.getInt("Page"),
                data.getInt("PageSize")));

        JSONArray listingsData = data.getJSONArray("List");
        Log.i("out", listingsData.toString());

        for (int i = 0; i < listingsData.length(); i++)
        {
            JSONObject currListing = listingsData.getJSONObject(i);
            Log.i("out", currListing.getString("Title"));
            String img;
            try {
                img = currListing.getString("PictureHref");
            } catch (Exception ex) {
                img = "http://www.tmsandbox.co.nz/images/NewSearchCards/LVIcons/noPhoto_160x120.png";
            }

            Results res = new Results(currListing.getInt("ListingId"), currListing.getString("Title"),
                    currListing.getString("Category"), img);
            _data.getResults().addResult(res);
        }


//        Log.i("out", "Total Results: " + Integer.toString(results.getTotal()));
//        for (Results r : results.getResults())
//        {
//            Log.i("out", r.getTitle());
//        }
    }

    public void ProcessCategories(JSONObject data, Context ctx) throws JSONException {

//        JSONArray catArray = data.getJSONArray("Subcategories");
//
//        for (int i = 0; i < catArray.length(); i++)
//        {
//            JSONObject catObj = catArray.getJSONObject(i);
//            Categories newCat = new Categories(catObj.getString("Name"), catObj.getString("Number"), catObj.getString("Path"), catObj.getBoolean("HasClassifieds"));
//            if (catObj.has("Subcategories"))
//            {
//                JSONArray subList = catObj.getJSONArray("Subcategories");
//                newCat = SubCatProcess(newCat, subList);
//            }
//            _data.addCategories(newCat);
//        }

        //Make JSON array from the first lot of sub categories from services section
        JSONArray catArray = data.getJSONArray("Subcategories");
        //Iterate through objects in catArray
        for (int i = 0; i < catArray.length(); i++)
        {
            //Pull i object from JSON array
            JSONObject catObj = catArray.getJSONObject(i);
            //Get values from JSON object
            String name = catObj.getString("Name");
            String number = catObj.getString("Number");
            String path = catObj.getString("Path");
            boolean hasClassifieds = catObj.optBoolean("HasClassifieds");
            boolean hasLegal = catObj.optBoolean("HasLegalNotice");
            boolean isRestricted = catObj.optBoolean("IsRestricted");
            String mainCat = "9334-";
            //Make new Categories object using values
            Categories newCat = new Categories(name,number,path,hasClassifieds,hasLegal,isRestricted, mainCat);
            //Insert newCat into database
            new Database(ctx).InsertCat(newCat);
            //See if catObj has any sub categories
            if (catObj.has("Subcategories"))
            {
                //If it does get the array of subCats
                JSONArray subList = catObj.getJSONArray("Subcategories");
                //Call subCat process method passing in the list of subCats, parent number, and the context
                SubCatProcess(subList, number, ctx);
            }
        }

    }

    private void SubCatProcess(JSONArray subList, String parentNum, Context ctx) throws JSONException
    {
        for (int y = 0; y < subList.length(); y++)
        {
            JSONObject subCatObj = subList.getJSONObject(y);
            String name = subCatObj.getString("Name");
            String number = subCatObj.getString("Number");
            String path = subCatObj.getString("Path");
            boolean hasClassifieds = subCatObj.optBoolean("HasClassifieds");
            boolean hasLegal = subCatObj.optBoolean("HasLegalNotice");
            boolean isRestricted = subCatObj.optBoolean("IsRestricted");
            String parentCat = parentNum;

            Categories newCat = new Categories(name,number,path,hasClassifieds,hasLegal,
                    isRestricted, parentCat);

            new Database(ctx).InsertCat(newCat);


            if (subCatObj.has("Subcategories"))
            {
                JSONArray nextSubList = subCatObj.getJSONArray("Subcategories");

                SubCatProcess(nextSubList, number, ctx);
            }
        }
    }

    public void ProcessListing(JSONObject data) throws JSONException
    {
        Listing listing = new Listing();
        listing.setId(data.getInt("ListingId"));
        listing.setTitle(data.getString("Title"));
        listing.setCat(data.getString("Category"));
        listing.setStartPrice(data.getInt("StartPrice"));
        listing.setStartDate(data.getString("StartDate"));
        listing.setEndDate(data.getString("EndDate"));
        //listing.setFeatured(data.getBoolean("IsFeatured"));
        listing.setCatName(data.getString("CategoryName"));
        listing.setRegionID(data.getInt("RegionId"));
        listing.setRegion(data.getString("Region"));
        listing.setSuburb(data.getString("Suburb"));
        listing.setReserveState(data.getInt("ReserveState"));
        listing.setClassified(data.getBoolean("IsClassified"));
        listing.setBody(data.getString("Body"));

        if (data.has("Attributes"))
        {
            JSONArray attr = data.getJSONArray("Attributes");
            List<Attribute> attrList = new ArrayList<Attribute>();

            for (int i = 0; i < attr.length(); i++)
            {
                JSONObject attrObj = attr.getJSONObject(i);
                attrList.add(new Attribute(attrObj.getString("Name"), attrObj.getString("DisplayName"), attrObj.getString("Value"), attrObj.getString("EncodedValue")));
            }
            listing.setAttributes(attrList);
        }

        JSONObject memberObj = data.getJSONObject("Member");

        Member member = new Member(memberObj.getInt("MemberId"), memberObj.getString("Nickname"), memberObj.getString("Suburb"), memberObj.getString("Region"), memberObj.getInt("FeedbackCount"));

        listing.setMember(member);

        if (data.has("Photos"))
        {
            JSONArray photo = data.getJSONArray("Photos");
            List<Photo> photosList = new ArrayList<Photo>();

            for (int i = 0; i < photo.length(); i++)
            {
                JSONObject photoObj = photo.getJSONObject(i);
                JSONObject photoDetObj = photoObj.getJSONObject("Value");
                photosList.add(new Photo(photoObj.getInt("Key"), photoDetObj.getInt("PhotoId"), photoDetObj.getString("Thumbnail"),
                        photoDetObj.getString("List"), photoDetObj.getString("Medium"), photoDetObj.getString("Gallery"),
                        photoDetObj.getString("Large"), photoDetObj.getString("FullSize")));
            }

            listing.setPhotos(photosList);
        }
        _data.setListing(listing);
    }
}
