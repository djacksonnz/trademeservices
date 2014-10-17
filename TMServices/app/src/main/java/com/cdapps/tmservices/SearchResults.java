package com.cdapps.tmservices;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.cdapps.tmservices.cat.Categories;
import com.cdapps.tmservices.data.Constants;
import com.cdapps.tmservices.data.DataProcess;
import com.cdapps.tmservices.data.Database;
import com.cdapps.tmservices.location.District;
import com.cdapps.tmservices.location.Region;
import com.cdapps.tmservices.models.ListAdapterSR;
import com.cdapps.tmservices.models.ListModelSR;
import com.cdapps.tmservices.search.Results;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchResults extends Activity {

    private AQuery aq = new AQuery(this);

    String cat;
    String keywords;
    int page;
    int region;
    int district;
    boolean featured = true;

    ListView list;
    ListAdapterSR adapter;
    public SearchResults sr = null;
    public ArrayList<ListModelSR> customView = new ArrayList<ListModelSR>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        DisplayMenu();
        SetupMenu();

        sr = this;

        Bundle bundle = getIntent().getExtras();
        cat = bundle.getString("cat");
        region = bundle.getInt("region");
        district = bundle.getInt("district");
        keywords = bundle.getString("keywords");
        page = 1;

        findViewById(R.id.backImgSR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        displayInfo();
        asyncJson();
    }

    private void displayInfo() {
        TextView textView = (TextView) findViewById(R.id.detailsTextSR);

        Categories categories = new Database(this).getSpecCat(cat);

        String catTxt = categories.getName();
        if (cat == "9334-")
        {
            catTxt = "All";
        }

        String regionTxt = "All Locations";
        String districtTxt = "";

        if (region != 0 && region != 100)
        {
            Region r = new Database(this).GetRegion(region);

            regionTxt = r.getName();

            if (district != 0 && district != 100)
            {
                District d = new Database(this).GetDistrict(district);
                districtTxt = d.getName();
            }
        }

        textView.setText(catTxt + " in " + regionTxt + districtTxt);

    }

    //Async method to call for the categories from the API
    public void asyncJson(){
        //Set url getting address from constants class
        //String url = "http://api.trademe.co.nz/v1/Search/General.json?buy=All&category=9334&condition=All&expired=false&photo_size=thumbnail&return_metadata=false&shipping_method=All&rows=25&page=1";
        String url = new Constants().getBASE_ADDR() +
                "Search/General.json?" +
                "buy=All" +
                "&category=" + cat +
                "&condition=All" +
                "&expired=false" +
                "&photo_size=thumbnail" +
                "&return_metadat=false" +
                "&shipping_method=All" +
                "&rows=" + Integer.toString(new Constants().getPAGE_SIZE()) +
                "&page=" + Integer.toString(page) +
                "&search_string=" + keywords;
//
        if (region != 0 && region != 100)
        {
            url += "&region=" + Integer.toString(region);
        }

        if (district != 0 && district != 100)
        {
            url += "&district=" + Integer.toString(district);
        }


//        if (featured)
//        {
//            url += "&sort_order=FeaturedFirst";
//        }

        Log.i("out", url);

        //Run AndroidQuery AJAX call running to jsonCallbackCat when it is completed
        aq.ajax(url, JSONObject.class, this, "jsonCallback");
    }

    //Method called when cat async call is completed
    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        //Check to see if there was a JSON object returned
        if(json != null){
            Log.i("out", json.toString());
            com.cdapps.tmservices.search.SearchResults searchResults =
                    new DataProcess().ProcessSearchResults(json, this);
            setListData(searchResults);
        }else{
            //ajax error, show error code if there is no JSON
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    public void setListData(com.cdapps.tmservices.search.SearchResults searchResults)
    {
        for (Results r : searchResults.getResults())
        {
            String location = r.getRegion();
            String name = r.getTitle();
            String listedDate = r.getStartDate().toString();
            int totalReviews = r.getTotalReviews();
            int reviewRating =  100;
            String image = r.getPicHref();
            boolean featured = r.isFeatured();
            int id = r.getListingId();

            ListModelSR result = new ListModelSR(location, name, listedDate, totalReviews, reviewRating
            , image, featured, id);

            customView.add(result);
        }

        Resources res = getResources();
        list = (ListView) findViewById(R.id.resultListSR);

        adapter = new ListAdapterSR(sr, customView, res);
        list.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SetupMenu() {

        findViewById(R.id.notificationsImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Notifications.class);
                finish();
                startActivity(intent);
            }});

        findViewById(R.id.searchImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Search.class);
                finish();
                startActivity(intent);
            }});

        findViewById(R.id.listServiceImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListServiceMenu.class);
                finish();
                startActivity(intent);
            }});

        findViewById(R.id.watchlistImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Watchlist.class);
                finish();
                startActivity(intent);
            }});

        findViewById(R.id.accountImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Account.class);
                finish();
                startActivity(intent);
            }});

    }

    protected void DisplayMenu()
    {
        GridLayout menuGrid = (GridLayout) this.findViewById(R.id.menuGridSR);
        int childCount = menuGrid.getChildCount();

        SharedPreferences appInfo = getSharedPreferences("appInfo", 0);

        int deviceWidth = appInfo.getInt("deviceWidth", 0);
        int deviceHeight = appInfo.getInt("deviceHeight", 0);

        int gridHeight = deviceHeight / 10;
        int gridItemWidth = deviceWidth / 5;

        for (int i = 0; i < childCount; i++)
        {
            ImageView currImg = (ImageView) menuGrid.getChildAt(i);
            currImg.getLayoutParams().width = gridItemWidth;
            currImg.getLayoutParams().height = gridHeight;
        }
    }

    public void onItemClick(int listingId) {
        Intent intent = new Intent(this, Listing.class);
        intent.putExtra("id", listingId);
        startActivity(intent);
    }
}
