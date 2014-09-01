package com.trademeservices.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.trademeservices.app.R;
import com.trademeservices.app.data.Constants;
import com.trademeservices.app.data.DataProcess;
import com.trademeservices.app.data.DownloadImage;
import com.trademeservices.app.reviews.Review;
import com.trademeservices.app.reviews.ReviewResults;
import com.trademeservices.app.search.Results;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Reviews extends ActionBarActivity {

    private AQuery aq = new AQuery(this);

    private int listingId;
    private int posReviews;
    private int totalReviews;
    private int page;
    private int totalPages;
    private int pageSize;
    private String listingTitle;
    private boolean firstPull;

    private List<Review> reviewsList = new ArrayList<Review>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Bundle bundle = getIntent().getExtras();
        listingId = bundle.getInt("id");
        totalReviews = bundle.getInt("totalReviews");
        posReviews = bundle.getInt("posReviews");
        listingTitle = bundle.getString("title");
        page = 1;
        firstPull = true;
        asyncJsonSearch();
    }

    public void asyncJsonSearch(){
        String url = new Constants().getBASE_ADDR() + "Listings/"
                + Integer.toString(listingId) + "/reviews.json?page=" + page;

        Log.i("out", url);
        aq.ajax(url, JSONObject.class, this, "jsonCallbackSearch");

    }

    public void jsonCallbackSearch(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        if(json != null){
            ProcessCall(new DataProcess().ProcessReviews(json));
        }else{
            //ajax error, show error code
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    public void InitDataPull(ReviewResults results)
    {
        pageSize = results.getPageSize();
        totalPages = (int) Math.floor(totalReviews / pageSize) + 1;

        LinearLayout mainLayout = (LinearLayout) this.findViewById(R.id.layout);
        TextView titleText = new TextView(this);
        titleText.setWidth(mainLayout.getWidth());
        titleText.setText("Reviews for " + listingTitle);
        mainLayout.addView(titleText);

        GridLayout reviewIconsBar = new GridLayout(this);
        reviewIconsBar.setRowCount(1);
        reviewIconsBar.setColumnCount(2);
        TextView posText = new TextView(this);
        int percentPos = (posReviews / totalReviews) * 100;
        posText.setText(percentPos + "% from " + totalReviews + " reviews.");
        ImageView posImg = new ImageView(this);
        posImg.setMinimumWidth(100);
        posImg.setMaxWidth(100);
        posImg.setMinimumHeight(100);
        posImg.setMaxHeight(100);
        new DownloadImage(posImg)
                .execute("http://www.trademe.co.nz/Images/services/thumbs_up_icon_white.gif");
        reviewIconsBar.addView(posImg);
        reviewIconsBar.addView(posText);
        mainLayout.addView(reviewIconsBar);
        firstPull = false;
        DisplayInfo(results);
    }

    public void ProcessCall(ReviewResults results)
    {
        if (firstPull)
            InitDataPull(results);
        else
            DisplayInfo(results);
    }

    public void DisplayInfo(ReviewResults results)
    {
        LinearLayout mainLayout = (LinearLayout) this.findViewById(R.id.layout);

        for (Review r : results.getReviewsList())
        {

            GridLayout reviewGrid = new GridLayout(this);
            GridLayout reviewTextGrid = new GridLayout(this);
            reviewGrid.setColumnCount(2);
            reviewGrid.setRowCount(1);

            if (r.getResponse().equals(""))
            {
                reviewTextGrid.setColumnCount(1);
                reviewTextGrid.setRowCount(2);
            }
            else
            {
                reviewTextGrid.setColumnCount(1);
                reviewTextGrid.setRowCount(3);
            }

            TextView reviewText = new TextView(this);
            TextView reviewDateMem = new TextView(this);
            ImageView thumbImg = new ImageView(this);

            reviewDateMem.setText(r.getMember().getNickname() + " | " + r.getDate().toString());
            reviewText.setText(r.getReviewText());

            if (r.isPositive())
                new DownloadImage(thumbImg).execute("http://www.trademe.co.nz/Images/services/thumbs_up_icon_white.gif");
            else
                new DownloadImage(thumbImg).execute("http://www.trademe.co.nz/Images/services/thumbs_down_icon_white.gif");

            reviewTextGrid.addView(reviewText);
            reviewTextGrid.addView(reviewDateMem);

            if (r.getResponse().equals(""))
            {
                TextView responseText = new TextView(this);
                responseText.setText(r.getResponse());
                reviewTextGrid.addView(responseText);
            }

            reviewGrid.addView(thumbImg);
            reviewGrid.addView(reviewTextGrid);

            mainLayout.addView(reviewGrid);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reviews, menu);
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



