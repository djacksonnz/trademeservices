package com.cdapps.tmservices;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.cdapps.tmservices.cat.Categories;
import com.cdapps.tmservices.data.Database;
import com.cdapps.tmservices.data.ListingOptions;
import com.cdapps.tmservices.gen.*;
import com.cdapps.tmservices.listing.*;
import com.cdapps.tmservices.listing.Listing;
import com.cdapps.tmservices.location.Region;
import com.cdapps.tmservices.location.Suburb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class ListService extends Activity {

    ListingOptions data = ListingOptions.getInstance();

    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_service);

        DisplayMenu();
        SetupMenu();

        page = 1;

        DisplayPage();

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlForward();
            }
        });

        findViewById(R.id.backText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlBack();
            }
        });

        SetLinks();
    }

    private void SetLinks() {

        findViewById(R.id.catSel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CatSelection.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.relativeLayout4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LocSelection.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartData(1);
            }
        });

        findViewById(R.id.relativeLayout7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartData(2);
            }
        });

        findViewById(R.id.relativeLayout8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartData(3);
            }
        });

        findViewById(R.id.relativeLayout9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartData(4);
            }
        });

        findViewById(R.id.relativeLayout10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartData(5);
            }
        });
    }

    private void StartData(int page) {
        Intent intent = new Intent(this, ListData.class);
        intent.putExtra("page", page);
        startActivity(intent);
    }

    private void ControlBack() {
        switch (page) {
            case 1:
                finish();
                break;
            case 2:
                page = 1;
                DisplayPage();
                break;
            case 3:
                page = 2;
                DisplayPage();
        }
    }

    private void ControlForward() {
        switch (page) {
            case 1:
                page = 2;
                DisplayPage();
                break;
            case 2:
                page = 3;
                DisplayPage();
                break;
            case 3:
                ProcessSummary();
                break;
        }
        
        EmailSwitch();
    }

    private void EmailSwitch() {
        final CheckBox email = (CheckBox) findViewById(R.id.checkBox);
        final CheckBox noEmail = (CheckBox) findViewById(R.id.checkBox2);
        email.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email.toggle();
                        noEmail.toggle();
                    }
                }
        );
        
        noEmail.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email.toggle();
                        noEmail.toggle();
                    }
                }
        );
                          
    }

    private void ProcessSummary() {
        EditText et;
        Switch sw;
        Categories c;
        Region r;
        
        int listingId = new Random().nextInt(9) + 1000000;
        et = (EditText) findViewById(R.id.editText);
        String title = et.getText().toString();
        c = new Database(this).getSpecCat(data.getCat());
        String category = c.getNumber();
        Date startDate = new Date();
        Date endDate = new Date();
        boolean isFeatured;
        sw = (Switch) findViewById(R.id.switch1);
        if (sw.isChecked())
            isFeatured = true;
        else
            isFeatured = false;
        
        boolean hasGallery = false;
        boolean isBold = false;
        boolean isHighlighted = false;
        boolean hasHomePageFeature = false;
        Date asAt = new Date();
        String categoryPath = c.getPath();
        String photoId = "";
        int regionId = data.getRegionId();
        r = new Database(this).GetRegion(regionId);
        String region = r.getName();
        String suburb = data.getSuburb();
        int viewCount = 0;
        String categoryName = c.getName();
        List<Attribute> attributes = new ArrayList<Attribute>();
        et = (EditText) findViewById(R.id.editText2);
        attributes.add(new Attribute("business name", "Business Name", et.getText().toString()));
        attributes.add(new Attribute("about", "About", data.getAbout()));
        attributes.add(new Attribute("service offered", "Services Offered", data.getServices()));
        attributes.add(new Attribute("areas", "Areas Serviced", data.getAreas()));
        attributes.add(new Attribute("avaliability", "Availability", data.getAvailability()));
        boolean isClassified = true;
        int relistedItemId = 0;
        String subtitle = "";
        boolean isOnWatchList = false;
        int totalReviewCount = 0;
        int positiveReviewCount = 0;
        com.cdapps.tmservices.gen.Member member  = new com.cdapps.tmservices.gen.Member(5000000, "auser", "Waverley", "Otago", 1000, new Date(), new Date(), 1, 999, false, false);
        
        String body = data.getDescription();
        List<Photo> photos = new ArrayList<Photo>();
        et = (EditText) findViewById(R.id.editText3);
        String name = et.getText().toString();
        et = (EditText) findViewById(R.id.editText7);
        String phone = et.getText().toString();
        et = (EditText) findViewById(R.id.editText6);
        String mobile = et.getText().toString();
        ContactDetails contactDetails = new ContactDetails(name, phone, mobile, "Anytime");
        boolean withdrawnBySeller = false;
        Listing l = new Listing(listingId, title, category, startDate, endDate, isFeatured, hasGallery, isHighlighted, hasHomePageFeature,
                asAt,  categoryPath,  photoId,  regionId,  region,
                suburb,  viewCount,  categoryName,  isClassified,  relistedItemId,
                subtitle,  isOnWatchList,  totalReviewCount,  positiveReviewCount,
                body,  withdrawnBySeller,  isBold);
        l.setPhotos(photos);
        l.setAttributes(attributes);
        l.setMember(member);
        l.setContactDetails(contactDetails);
        data.setListing(l);

        Intent intent = new Intent(this, ServiceSummary.class);
        startActivity(intent);
    }

    private void DisplayPage() {
        RelativeLayout p1 = (RelativeLayout) findViewById(R.id.pg1lay);
        RelativeLayout p2 = (RelativeLayout) findViewById(R.id.pg2Lay);
        RelativeLayout p3 = (RelativeLayout) findViewById(R.id.pg3lay);

        p1.setVisibility(View.GONE);
        p2.setVisibility(View.GONE);
        p3.setVisibility(View.GONE);

        switch (page)  {
            case 1:
                p1.setVisibility(View.VISIBLE);
                break;
            case 2:
                p2.setVisibility(View.VISIBLE);
                break;
            case 3:
                p3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
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
