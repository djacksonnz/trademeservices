package com.cdapps.tmservices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.cdapps.tmservices.cat.Categories;
import com.cdapps.tmservices.data.Database;
import com.cdapps.tmservices.location.District;
import com.cdapps.tmservices.location.Region;
import com.cdapps.tmservices.models.ListAdapterCat;
import com.cdapps.tmservices.models.ListAdapterLoc;
import com.cdapps.tmservices.models.ListAdapterSR;
import com.cdapps.tmservices.models.ListModelCat;
import com.cdapps.tmservices.models.ListModelLoc;
import com.cdapps.tmservices.models.ListModelSR;

import java.util.ArrayList;
import java.util.List;


public class Search extends Activity {

    String selectedCat;
    String prevCat;
    int region;
    int district;
    Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ctx = this;

        DisplayMenu();
        DisplayTabs();

        InitSetup();
    }

    private void InitSetup() {
        DisplayCat();
        TabClick();
        SortClick();

        selectedCat = "9334-";
        prevCat = "9334-";
        region = 0;
        district = 0;

        PopulateCat();
        PopulateLoc();

        SearchView searchView =(SearchView) findViewById(R.id.keywordsInS);
        searchView.setFocusable(false);

        ImageView back = (ImageView) findViewById(R.id.blueBackImgS);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCat = prevCat;
                PopulateCat();
            }
        });

        ImageView locBack = (ImageView) findViewById(R.id.blueBackLocS);
        locBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (district != 0)
                {
                    district = 0;
                    PopulateLoc();
                }
                else if (region != 0)
                {
                    region = 0;
                    PopulateLoc();
                }
            }
        });

        Button search = (Button) findViewById(R.id.buttonS);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchView searchV = (SearchView) findViewById(R.id.keywordsInS);
                String keywords = searchV.getQuery().toString();
                Intent intent = new Intent(ctx, SearchResults.class);
                intent.putExtra("cat", selectedCat);
                intent.putExtra("region", region);
                intent.putExtra("district", district);
                intent.putExtra("keywords", keywords); 
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    private void CatBackVis()
    {
        if (selectedCat == "9334-")
            findViewById(R.id.blueBackImgS).setVisibility(View.INVISIBLE);
        else
            findViewById(R.id.blueBackImgS).setVisibility(View.VISIBLE);
    }

    private void LocBackVis()
    {
        if (region == 0)
        {
            findViewById(R.id.blueBackLocS).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.blueBackLocS).setVisibility(View.VISIBLE);
        }
    }

    private void PopulateCat() {

        CatBackVis();


            ImageView back = (ImageView) findViewById(R.id.blueBackImgS);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedCat = prevCat;
                    PopulateCat();
                }
            });

        Categories selectedCategory = new Database(this).getSpecCat(selectedCat);

        TextView catTitle = (TextView) findViewById(R.id.catTitleS);
        if (selectedCategory.getName().equals("All"))
            catTitle.setText("Services");
        else
            catTitle.setText(selectedCategory.getName());


        TextView catCount = (TextView) findViewById(R.id.catTitleCountS);
        catCount.setText("### listings");

        List<Categories> catList = new Database(this).getCat(selectedCat);

        ListView list;
        ListAdapterCat adapter;
        SearchResults sr = null;
        ArrayList<ListModelCat> customView = new ArrayList<ListModelCat>();

        for (Categories c : catList)
        {
            String name = c.getName();
            int count = 0;

            if (!name.equals("All")) {
                ListModelCat data = new ListModelCat(name, c.getNumber(), count);
                customView.add(data);
            }
        }

        Resources res = getResources();
        list = (ListView) findViewById(R.id.catListS);

        adapter = new ListAdapterCat(this, customView, res);

        if (catList.size() == 0)
            list.setAdapter(null);
        else
            list.setAdapter(adapter);

    }

    private void PopulateLoc() {

        LocBackVis();

        ListView list = (ListView) findViewById(R.id.locListS);;
        ListAdapterLoc adapter;
        SearchResults sr = null;
        ArrayList<ListModelLoc> customView = new ArrayList<ListModelLoc>();

        if (region == 0)
        {
            List<Region> regionList = new Database(this).GetRegions();

            TextView title = (TextView) findViewById(R.id.locTitleS);
            title.setText("New Zealand");

            for (Region r : regionList)
            {
                ListModelLoc data = new ListModelLoc(r.getName(), r.getId());

                customView.add(data);
            }

            Resources res = getResources();
            list = (ListView) findViewById(R.id.locListS);

            adapter = new ListAdapterLoc(this, customView, res);

            if (regionList.size() == 0)
                list.setAdapter(null);
            else
                list.setAdapter(adapter);

        }
        else if (district == 0)
        {
            Region regionSel = new Database(this).GetRegion(region);
            List<District> districtList = new Database(this).GetDistricts(region);

            TextView title = (TextView) findViewById(R.id.locTitleS);
            title.setText(regionSel.getName());

            for (District r : districtList)
            {
                ListModelLoc data = new ListModelLoc(r.getName(), r.getId());

                customView.add(data);
            }

            Resources res = getResources();
            adapter = new ListAdapterLoc(this, customView, res);

            if (districtList.size() == 0)
                list.setAdapter(null);
            else
                list.setAdapter(adapter);
        }
        else
        {
            District d = new Database(this).GetDistrict(district);
            TextView title = (TextView) findViewById(R.id.locTitleS);
            title.setText(d.getName());
            list.setAdapter(null);
        }

    }

    public void onItemClick(String newCatId)
    {
        prevCat = selectedCat;
        selectedCat = newCatId;
        PopulateCat();
    }

    public void onItemClickLoc(int num)
    {
        if (region == 0) {
            region = num;
            PopulateLoc();
        }
        else if (district == 0)
        {
            district = num;
            PopulateLoc();
        }
        else
            PopulateLoc();
    }

    private void TabClick() {
        ImageView cat = (ImageView) findViewById(R.id.catTabS);
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayCat();
            }
        });

        ImageView loc = (ImageView) findViewById(R.id.locTabS);
                loc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DisplayLocation();
                    }
                });

        ImageView sort = (ImageView) findViewById(R.id.refineTabS);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplaySort();
            }
        });
    }

    private void DisplayCat() {
        ImageView cat = (ImageView) findViewById(R.id.catTabS);
        cat.setImageResource(R.drawable.cat_selected);

        ImageView loc = (ImageView) findViewById(R.id.locTabS);
        loc.setImageResource(R.drawable.location);

        ImageView refine = (ImageView) findViewById(R.id.refineTabS);
        refine.setImageResource(R.drawable.sort);

        RelativeLayout catLay = (RelativeLayout) findViewById(R.id.catLayS);
        catLay.setVisibility(View.VISIBLE);

        RelativeLayout locLay = (RelativeLayout) findViewById(R.id.locLayS);
        locLay.setVisibility(View.INVISIBLE);

        RelativeLayout refineLay = (RelativeLayout) findViewById(R.id.refineLayS);
        refineLay.setVisibility(View.INVISIBLE);

    }

    private void DisplayLocation() {
        ImageView cat = (ImageView) findViewById(R.id.catTabS);
        cat.setImageResource(R.drawable.cat);

        ImageView loc = (ImageView) findViewById(R.id.locTabS);
        loc.setImageResource(R.drawable.location_selected);

        ImageView refine = (ImageView) findViewById(R.id.refineTabS);
        refine.setImageResource(R.drawable.sort);

        RelativeLayout catLay = (RelativeLayout) findViewById(R.id.catLayS);
        catLay.setVisibility(View.INVISIBLE);

        RelativeLayout locLay = (RelativeLayout) findViewById(R.id.locLayS);
        locLay.setVisibility(View.VISIBLE);

        RelativeLayout refineLay = (RelativeLayout) findViewById(R.id.refineLayS);
        refineLay.setVisibility(View.INVISIBLE);

    }

    private void DisplaySort() {
        ImageView cat = (ImageView) findViewById(R.id.catTabS);
        cat.setImageResource(R.drawable.cat);

        ImageView loc = (ImageView) findViewById(R.id.locTabS);
        loc.setImageResource(R.drawable.location);

        ImageView refine = (ImageView) findViewById(R.id.refineTabS);
        refine.setImageResource(R.drawable.sort_selected);

        RelativeLayout catLay = (RelativeLayout) findViewById(R.id.catLayS);
        catLay.setVisibility(View.INVISIBLE);

        RelativeLayout locLay = (RelativeLayout) findViewById(R.id.locLayS);
        locLay.setVisibility(View.INVISIBLE);

        RelativeLayout refineLay = (RelativeLayout) findViewById(R.id.refineLayS);
        refineLay.setVisibility(View.VISIBLE);

    }

    private void SortClick() {
        findViewById(R.id.titleS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UntickAll();
                findViewById(R.id.titleTickS).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.featuredS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UntickAll();
                findViewById(R.id.featuredTickS).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.latestS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UntickAll();
                findViewById(R.id.latestTickS).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.mostS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UntickAll();
                findViewById(R.id.mostTickS).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.viewS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UntickAll();
                findViewById(R.id.viewTickS).setVisibility(View.VISIBLE);
            }
        });
    }

    private void UntickAll() {

        findViewById(R.id.featuredTickS).setVisibility(View.INVISIBLE);
        findViewById(R.id.latestTickS).setVisibility(View.INVISIBLE);
        findViewById(R.id.mostTickS).setVisibility(View.INVISIBLE);
        findViewById(R.id.titleTickS).setVisibility(View.INVISIBLE);
        findViewById(R.id.viewTickS).setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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

    protected void DisplayTabs() {
        LinearLayout tabs = (LinearLayout) this.findViewById(R.id.tabViewS);
        int childCount = tabs.getChildCount();

        SharedPreferences appInfo = getSharedPreferences("appInfo", 0);

        int deviceWidth = appInfo.getInt("deviceWidth", 0);

        int gridItemWidth = deviceWidth / 3;

        for (int i = 0; i < childCount; i++)
        {
            ImageView currImg = (ImageView) tabs.getChildAt(i);
            currImg.getLayoutParams().width = gridItemWidth;
        }

    }

    protected void DisplayMenu()
    {
        GridLayout menuGrid = (GridLayout) this.findViewById(R.id.menuS);
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
