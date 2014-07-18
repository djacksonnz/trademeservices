package com.trademeservices.app;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.trademeservices.app.listing.Attribute;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


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
        tv.setText(data.getListing().getTitle());

        LinearLayout layout = (LinearLayout) findViewById(R.id.linLay);

        List<Attribute> att = data.getListing().getAttributes();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        LinearLayout newLin = new LinearLayout(this);
        newLin.setOrientation(LinearLayout.VERTICAL);
        ScrollView sv = new ScrollView(this);
        //Attributes
        for (Attribute a : att)
        {
            GridLayout gl = new GridLayout(this);
            TextView attText = new TextView(this);
            attText.setText(a.getDisplayName() + ": " + a.getValue());
            attText.setMinimumWidth(size.x);
            ImageView div = new ImageView(this);
            div.setMinimumHeight(100);
            div.setMinimumWidth(size.x);
            div.setBackgroundColor(Color.YELLOW);
            gl.addView(attText);
            gl.addView(div);
            newLin.addView(gl);
        }

        //body
        TextView body = new TextView(this);
        body.setText(data.getListing().getBody());
        newLin.addView(body);
        sv.addView(newLin);
        layout.addView(sv);
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
