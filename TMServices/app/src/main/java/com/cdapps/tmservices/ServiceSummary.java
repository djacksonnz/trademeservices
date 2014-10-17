package com.cdapps.tmservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdapps.tmservices.data.ListingOptions;
import com.cdapps.tmservices.dummy.UserDataDummy;


public class ServiceSummary extends Activity {

    ListingOptions data = ListingOptions.getInstance();
    UserDataDummy dummy = UserDataDummy.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_summary);

        DisplayMenu();
        SetupMenu();
        LinkSetup();
        TextSetup();
    }

    private void TextSetup() {
        TextView cat = (TextView) findViewById(R.id.catname);
        cat.setText(data.getListing().getCategoryName());

        TextView title = (TextView) findViewById(R.id.textView2);
        title.setText(data.getListing().getTitle());

        TextView location = (TextView) findViewById(R.id.textView3);
        location.setText(data.getListing().getRegion());

        if (!data.getListing().isFeatured())
        {
            TextView featuredText = (TextView) findViewById(R.id.textView5);
            featuredText.setText("Not featured");

            TextView total = (TextView) findViewById(R.id.textView9);
            total.setText("$39.00");
        }

    }

    private void LinkSetup() {
        findViewById(R.id.backText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("Listing created")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(view.getContext(), ListServiceMenu.class);
                                dummy.addMyListing(data.getListing());
                                finish();
                                startActivity(intent);
                            }
                        });
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("Listing created")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(view.getContext(), ListServiceMenu.class);
                                dummy.addMyListing(data.getListing());
                                finish();
                                startActivity(intent);
                            }
                        });
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.service_summary, menu);
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

    @Override
    public void onBackPressed() {

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
        GridLayout menuGrid = (GridLayout) this.findViewById(R.id.menu);
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
