package com.trademeservices.app;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.trademeservices.app.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
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

    private Data data = Data.getInstance();
    private AQuery aq = new AQuery(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_load);
        GetDeviceScreenSize();
        asyncJsonCat();

    }

    private void GetDeviceScreenSize()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        data.getVariables().setDeviceHeight(size.x);
        data.getVariables().setDeviceWidth(size.y);
    }

    public void asyncJsonCat(){
        String url = new Variables().getBASE_ADDR() + "Categories/9334.json?with_counts=false";
        aq.ajax(url, JSONObject.class, this, "jsonCallbackCat");

    }

    public void jsonCallbackCat(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        if(json != null){
            new DataProcess().ProcessCategories(json);
            Log.i("out", "success 1");
            asyncJsonReg();
        }else{
            //ajax error, show error code
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    public void asyncJsonReg(){
        String url = new Variables().getBASE_ADDR() + "Localities.json?with_counts=false";
        aq.ajax(url, JSONArray.class, this, "jsonCallbackReg");

    }

    public void jsonCallbackReg(String url, JSONArray json, AjaxStatus status) throws JSONException
    {
        if(json != null){
            new DataProcess().ProcessLocations(json);
            Log.i("out", "success 2");
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


}
