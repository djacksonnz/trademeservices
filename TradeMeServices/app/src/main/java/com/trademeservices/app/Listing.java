package com.trademeservices.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;


public class Listing extends ActionBarActivity {

    private Data data = Data.getInstance();
    private AQuery aq = new AQuery(this);
    private int listingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        Bundle bundle = getIntent().getExtras();
        listingId = bundle.getInt("id");
        asyncJsonSearch();
    }

    public void asyncJsonSearch(){
        String url = new Constants().getBASE_ADDR() + "Listings/" + Integer.toString(listingId) + ".json";
//        if (keywords != null) {
//            url += "&search_string=" + keywords;
//        }
        Log.i("out", url);
        aq.ajax(url, JSONObject.class, this, "jsonCallbackSearch");

    }

    public void jsonCallbackSearch(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        if(json != null){
            new DataProcess().ProcessListing(json);
            DisplayInfo();
        }else{
            //ajax error, show error code
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    public void DisplayInfo()
    {
        TextView tv = (TextView) findViewById(R.id.textList);
        tv.setText(data.getListing().getTitle() + "\n" + data.getListing().getBody());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listing, menu);
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

}
