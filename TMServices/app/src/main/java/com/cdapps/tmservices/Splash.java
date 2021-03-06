package com.cdapps.tmservices;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.cdapps.tmservices.data.Constants;
import com.cdapps.tmservices.data.DataProcess;
import com.cdapps.tmservices.data.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Splash extends Activity {

    private AQuery aq = new AQuery(this);
    private SharedPreferences appInfo;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appInfo = this.getSharedPreferences("appInfo", 0);
        boolean firstRun = appInfo.getBoolean("firstRun", true);

        if (firstRun) {
            //Create database on device
            db = new Database(this);
            //Set first run to false in appInfo prefrences
            SharedPreferences.Editor editor = appInfo.edit();
            editor.putBoolean("firstRun", false);
            editor.commit();
            //Load api info to database
            asyncJsonCat();
            //Set screen size of device
            GetDeviceScreenSize();
        } else {
            DisplaySearch();
        }

    }

    private void DisplaySearch()
    {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    //Method that gets device screen size and stores it in local vars
    private void GetDeviceScreenSize() {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        //Add to shared prefrences appInfo
        SharedPreferences.Editor editor = appInfo.edit();
        editor.putInt("deviceHeight", height);
        editor.putInt("deviceWidth", width);
        editor.commit();
    }

    //Async method to call for the categories from the API
    public void asyncJsonCat(){
        //Set url getting address from constants class
        String url = new Constants().getBASE_ADDR() + "Categories/9334.json?with_counts=false";
        //Run AndroidQuery AJAX call running to jsonCallbackCat when it is completed
        aq.ajax(url, JSONObject.class, this, "jsonCallbackCat");
    }

    //Method called when cat async call is completed
    public void jsonCallbackCat(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        //Check to see if there was a JSON object returned
        if(json != null){
            //Process category data via DataProcess class passing in the recieved JSON and this
            new DataProcess().ProcessCategories(json, this);
            //Start Location ASYNC call
            asyncJsonReg();
        }else{
            //ajax error, show error code if there is no JSON
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    //Method for JSON location async call
    public void asyncJsonReg(){
        //Set url getting base from constants class
        String url = new Constants().getBASE_ADDR() + "Localities.json?with_counts=false";
        //Run AndroidQuery AJAX call running to jsonCallbackReg when it is completed
        aq.ajax(url, JSONArray.class, this, "jsonCallbackReg");
    }

    //Method called when region async call is completed
    public void jsonCallbackReg(String url, JSONArray json, AjaxStatus status) throws JSONException
    {
        //Check to see if there was a JSON object returned
        if(json != null){
            //Process region, district and suburb info by passing to DataProcess
            new DataProcess().ProcessLocations(json, this);
            //Start MainActivity (search) as all is loaded into database now
            DisplaySearch();
        }else{
            //ajax error, show error code
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
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
