package com.trademeservices.app;

import android.content.Context;

import com.trademeservices.app.cat.Categories;
import com.trademeservices.app.listing.Attribute;
import com.trademeservices.app.listing.Listing;
import com.trademeservices.app.listing.Member;
import com.trademeservices.app.listing.Photo;
import com.trademeservices.app.location.District;
import com.trademeservices.app.location.Region;
import com.trademeservices.app.location.Suburb;
import com.trademeservices.app.search.Results;
import com.trademeservices.app.search.SearchResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jacksdl2 on 11/07/2014.
 */
public class DataProcess {

    private Data _data = Data.getInstance();

    //Method to process through locations and add them to the database
    public void ProcessLocations(JSONArray data, Context ctx) throws JSONException {
        //Iterate through regions pulled from api
        for (int i = 0; i < data.length(); i++)
        {
            //Get region at location i
            JSONObject regionData = data.getJSONObject(i);
            //Make a new region object
            Region reg = new Region(regionData.getInt("LocalityId"), regionData.getString("Name"));
            //Insert region into database
            new Database(ctx).InsertRegion(reg);
            //Get array of districts for region
            JSONArray districtObj = regionData.getJSONArray("Districts");

            for (int j = 0; j < districtObj.length(); j++)
            {
                //Get district at location j
                JSONObject districtData = districtObj.getJSONObject(j);
                //create a new district object from the JSON result
                District dis = new District(districtData.getInt("DistrictId"),
                        districtData.getString("Name"), reg.getId());
                //Insert into database
                new Database(ctx).InsertDistrict(dis);
                //Get array of suburbs
                JSONArray suburbObj = districtData.getJSONArray("Suburbs");

                for (int k = 0; k < suburbObj.length(); k++)
                {
                    //Get suburb at location k
                    JSONObject suburbData = suburbObj.getJSONObject(k);
                    //Create new suburb object using values in JSON
                    Suburb sub = new Suburb(suburbData.getInt("SuburbId"),
                            suburbData.getString("Name"), dis.getId());
                    //Insert into database
                    new Database(ctx).InsertSuburb(sub);
                    //Get array of adjacent suburbs
//                    JSONArray ajSub = suburbData.getJSONArray("AdjacentSuburbs");
//                    //Iterate through results
//                    for (int l = 0; l <= ajSub.length(); l++)
//                    {
//                        //Get the ids of the main sub and of the ajacent ones
//                        int mainSubId = sub.getId();
//                        int ajSubId = ajSub.getInt(l);
//                        //Insert record into database
//                        new Database(ctx).InsertAdjacentSuburb(mainSubId, ajSubId);
//                    }
                }
            }
        }
    }

    //Method that takes the response of the search results and puts it into a SearchResult class and returns
    public SearchResults ProcessSearchResults(JSONObject data, Context ctx) throws JSONException
    {
        //Get general search result info
        int totalCount = data.optInt("TotalCount");
        int page = data.optInt("Page");
        int pageSize = data.optInt("PageSize");
        //Make list of categories for the response of found categories
        List<Categories> foundCategories = new ArrayList<Categories>();

        //Get array from data
        JSONArray categoriesFound = data.getJSONArray("FoundCategories");
        //Iterate over all categories returned
        for (int i = 0; i < categoriesFound.length(); i++)
        {
            //Get category object
            JSONObject category = categoriesFound.getJSONObject(i);
            //Add category by pulling from database on the Number string
            foundCategories.add(new Database(ctx).getSpecCat(category.getString("Category")));
        }
        //Create a new Searchresults obj and pass in the general info, and the found categories
        SearchResults results = new SearchResults(totalCount, page, pageSize, foundCategories);
        //Get the list of results as an array
        JSONArray listingsData = data.getJSONArray("List");
        //Iterate over the results
        for (int i = 0; i < listingsData.length(); i++)
        {
            //Get the object at location i
            JSONObject currListing = listingsData.getJSONObject(i);

            //Create a new Result object
            Results res = new Results();
            //Set fields of res from the values in the JSON object
            res.setListingId(currListing.getInt("ListingId"));
            res.setTitle(currListing.getString("Title"));
            res.setCategory(currListing.getString("Category"));
            res.setPicHref(currListing.optString("PictureHref"));
            res.setStartDate(JsonDateToDate(currListing.optString("StartDate")));
            res.setEndDate(JsonDateToDate(currListing.optString("EndDate")));
            res.setFeatured(currListing.optBoolean("IsFeatured"));
            res.setHasGallery(currListing.optBoolean("HasGallery"));
            res.setBold(currListing.optBoolean("IsBold"));
            res.setHighlighted(currListing.optBoolean("IsHighlighted"));
            res.setHasHomePageFeature(currListing.optBoolean("HasHomepageFeature"));
            res.setAsAt(JsonDateToDate(currListing.optString("AsAt")));
            res.setCategoryPath(currListing.getString("CategoryPath"));
            res.setRegionId(currListing.optInt("RegionId"));
            res.setRegion(currListing.optString("Region"));
            res.setSuburbId(currListing.optInt("SuburbId"));
            res.setSuburb(currListing.optString("Suburb"));
            res.setClassified(currListing.optBoolean("IsClassified"));
            res.setSubtitle(currListing.optString("Subtitle"));
            res.setOnWatchList(currListing.optBoolean("IsOnWatchList"));
            res.setTotalReviews(currListing.optInt("TotalReviewCount"));
            res.setPositiveReviews(currListing.optInt("PositiveReviewCount"));
            //Add res to the SearchResult object
            results.addResult(res);
        }
        //Return all search results
        return results;
    }

    //Method to convert JSON date to java date (From stack overflow)
    public static Date JsonDateToDate(String jsonDate)
    {
        //  "/Date(1321867151710+0100)/"
        int idx1 = jsonDate.indexOf("(");
        int idx2 = jsonDate.indexOf(")") - 5;
        String s = jsonDate.substring(idx1+1, idx2);
        long l = Long.valueOf(s);
        return new Date(l);
    }

    //Method for processing main categories, takes json object and Activity context
    public void ProcessCategories(JSONObject data, Context ctx) throws JSONException {

        //Make JSON array from the first lot of sub categories from services section
        JSONArray catArray = data.getJSONArray("Subcategories");
        //Insert a record for selecting all categories
        new Database(ctx).InsertCat(new Categories("All","9334-","/Services",true,false,false,"9334-"));
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

    //Method for processing subcategories, takes a list of subcategories, the parent its from, and the Activity context
    private void SubCatProcess(JSONArray subList, String parentNum, Context ctx) throws JSONException
    {
        //Iterate throught all items in the JSONArray subList
        for (int y = 0; y < subList.length(); y++)
        {
            //Pull the object for each one
            JSONObject subCatObj = subList.getJSONObject(y);
            //Get values to put into the database
            String name = subCatObj.getString("Name");
            String number = subCatObj.getString("Number");
            String path = subCatObj.getString("Path");
            boolean hasClassifieds = subCatObj.optBoolean("HasClassifieds");
            boolean hasLegal = subCatObj.optBoolean("HasLegalNotice");
            boolean isRestricted = subCatObj.optBoolean("IsRestricted");
            String parentCat = parentNum;
            //Make a new categories object using values obtained above
            Categories newCat = new Categories(name,number,path,hasClassifieds,hasLegal,
                    isRestricted, parentCat);
            //Insert newCat into database
            new Database(ctx).InsertCat(newCat);

            //Check to see if categorie object (subCatObj) has a subCategories section
            if (subCatObj.has("Subcategories"))
            {
                //If it does get the sub cats as an array
                JSONArray nextSubList = subCatObj.getJSONArray("Subcategories");
                //Run subcat process method (this method)
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
