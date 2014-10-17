package com.cdapps.tmservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.cdapps.tmservices.data.DownloadImage;
import com.cdapps.tmservices.dummy.UserDataDummy;
import com.cdapps.tmservices.listing.*;


public class MyServices extends Activity {

    UserDataDummy data = UserDataDummy.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services);

        SetupMenu();
        DisplayMenu();
        PopList();

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

       PopList();
    }

    private void PopList() {
        LinearLayout listings = (LinearLayout) findViewById(R.id.myListingsLay);
        listings.removeAllViews();

        View view;
        final ViewGroup parent = listings;



        for (final com.cdapps.tmservices.listing.Listing l : data.getMyListings()) {
            view = LayoutInflater.from(this).inflate(R.layout.my_services_item, null);

            TextView title = (TextView) view.findViewById(R.id.listingTitleW);
            title.setText(l.getTitle());

            TextView location = (TextView) view.findViewById(R.id.locationTextW);
            location.setText(l.getSuburb());

            TextView reviewNum = (TextView) view.findViewById(R.id.numReviewsW);
            TextView reviewPercent = (TextView) view.findViewById(R.id.reviewPercentW);
            if (l.getTotalReviewCount() == 0)
            {
                reviewNum.setText("No Reviews");
                reviewPercent.setVisibility(View.GONE);
                view.findViewById(R.id.thumbsUpImgW).setVisibility(View.GONE);
            }
            else {
                reviewNum.setText(Integer.toString(l.getTotalReviewCount()) + " Reviews");
                double percent = l.getPositiveReviewCount() / l.getTotalReviewCount();
                reviewPercent.setText(Double.toString(percent) + "%");
            }

            ImageView thumb = (ImageView) view.findViewById(R.id.watchImgW);
            thumb.setImageResource(R.drawable.no_photo_sr);

            final View v2 = view;

            view.findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(v.getContext())
                            .setMessage("Delete this listing? You will not receive a refund for your listing fees")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    parent.removeView(v2);
                                    data.removeMyListing(l.getListingId());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });

            parent.addView(view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_services, menu);
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
        GridLayout menuGrid = (GridLayout) this.findViewById(R.id.gridLayout);
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
