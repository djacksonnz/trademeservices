package com.trademeservices.app;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.trademeservices.app.act.Search;
import com.trademeservices.app.data.Constants;
import com.trademeservices.app.data.DataProcess;
import com.trademeservices.app.data.Database;
import com.trademeservices.app.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class InitLoad extends Activity {

    private AQuery aq = new AQuery(this);
    private SharedPreferences appInfo;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_load);

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
            //If app has already been installed or no update, just start main activity
            Intent intent = new Intent(this, Search.class);
            startActivity(intent);
        }

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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            //ajax error, show error code
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onStop() {
        //Log.w(TAG, "App stopped");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //Log.w(TAG, "App destoryed");

        super.onDestroy();
    }



}
