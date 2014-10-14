package com.cdapps.tmservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.cdapps.tmservices.data.Constants;
import com.cdapps.tmservices.data.DataProcess;
import com.cdapps.tmservices.data.DownloadImage;
import com.cdapps.tmservices.listing.*;
import com.cdapps.tmservices.reviews.Review;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class Watchlist extends Activity {

    List<Integer> items = new ArrayList<Integer>();

    Context ctx;

    private AQuery aq = new AQuery(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        ctx = this;

        items.add(721027156);
        items.add(724549825);

        PopDisp();

        DisplayMenu();
        SetupMenu();
    }

    @Override
    public void onBackPressed() {

    }

    private void PopDisp() {

        for (int i: items)
        {
            asyncJsonSearch(i);
        }
    }

    private void DisplayInfo(final com.cdapps.tmservices.listing.Listing l) {
        final View view;
        final ViewGroup parent = (ViewGroup) findViewById(R.id.watchItemsW);

        view = LayoutInflater.from(this).inflate(R.layout.watchlist_item, null);

        TextView title = (TextView) view.findViewById(R.id.listingTitleW);
        title.setText(l.getTitle());

        TextView location = (TextView) view.findViewById(R.id.locationTextW);
        location.setText(l.getSuburb());

        TextView reviewNum = (TextView) view.findViewById(R.id.numReviewsW);
        reviewNum.setText(Integer.toString(l.getTotalReviewCount()) + " Reviews");

        TextView reviewPercent = (TextView) view.findViewById(R.id.reviewPercentW);
        reviewPercent.setText("100%");

        ImageView thumb = (ImageView) view.findViewById(R.id.watchImgW);
        new DownloadImage(thumb).execute(l.getPhotos().get(0).getThumb());

        ImageView remove = (ImageView) view.findViewById(R.id.removeFromW);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setMessage("Remove listing from watchlist?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                parent.removeView(view);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layW);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, Listing.class);
                intent.putExtra("id", l.getListingId());
                startActivity(intent);
            }
        });


        parent.addView(view);
    }

    public void asyncJsonSearch(int id){
        String url = new Constants().getBASE_ADDR() + "Listings/" + Integer.toString(id) + ".json";

        Log.i("out", url);
        aq.ajax(url, JSONObject.class, this, "jsonCallbackSearch");

    }

    public void jsonCallbackSearch(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        if(json != null){
            DisplayInfo(new DataProcess().ProcessListing(json));
        }else{
            //ajax error, show error code
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.watchlist, menu);
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
                //Intent intent = new Intent(v.getContext(), Notifications.class);
                //finish();
                //startActivity(intent);
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
        GridLayout menuGrid = (GridLayout) this.findViewById(R.id.menuW);
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
}
