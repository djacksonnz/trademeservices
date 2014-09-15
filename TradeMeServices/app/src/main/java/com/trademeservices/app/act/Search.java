package com.trademeservices.app.act;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.callback.AjaxStatus;
import com.trademeservices.app.R;
import com.trademeservices.app.act.search_tabs.CategoryTab;
import com.trademeservices.app.act.search_tabs.LocationTab;
import com.trademeservices.app.act.search_tabs.RefineTab;
import com.trademeservices.app.act.search_tabs.SearchTabListener;
import com.trademeservices.app.cat.Categories;
import com.trademeservices.app.data.Constants;
import com.trademeservices.app.data.DataProcess;
import com.trademeservices.app.data.Database;
import com.trademeservices.app.data.SearchVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search extends Activity {

    ActionBar.Tab categoryTab, locationTab, refineTab;

    Fragment fragmentCategoryTab = new CategoryTab();
    Fragment fragmentLocationTab = new LocationTab();
    Fragment fragmentRefineTab = new RefineTab();

    private String searchCat;

    private SearchVariables searchVariables = SearchVariables.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        DisplaySet();

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        categoryTab = actionBar.newTab().setText("Category");
        locationTab = actionBar.newTab().setText("Location");
        refineTab = actionBar.newTab().setText("Refine");

        categoryTab.setTabListener(new SearchTabListener(fragmentCategoryTab));
        locationTab.setTabListener(new SearchTabListener(fragmentLocationTab));
        refineTab.setTabListener(new SearchTabListener(fragmentRefineTab));

        actionBar.addTab(categoryTab);
        actionBar.addTab(locationTab);
        actionBar.addTab(refineTab);

        searchCat = "9334-";

        updateView("");
    }

    public void updateView(String baseCat)
    {
        List<Categories> cats = new Database(this).getCat(baseCat);
        TextView tv = new TextView(this);
        tv.setText("test");

//
//        Fragment mf = (Fragment) getFragmentManager().findFragmentById(R.id.fragment_container);
//        fragmentCategoryTab
//
//
//
//       LinearLayout ll = (LinearLayout) frag.findViewById(R.id.search_tabs_layout);
        //ll.addView(tv);

    }

//    public void asyncJsonCat(){
//        //Set url getting address from constants class
//        String url = new Constants().getBASE_ADDR() + "Categories/" + searchCat + ".json";
//        //Run AndroidQuery AJAX call running to jsonCallbackCat when it is completed
//        aq.ajax(url, JSONObject.class, this, "jsonCallbackCat");
//    }
//
//    //Method called when cat async call is completed
//    public void jsonCallbackCat(String url, JSONObject json, AjaxStatus status) throws JSONException
//    {
//        //Check to see if there was a JSON object returned
//        if(json != null){
//            //Process category data via DataProcess class passing in the recieved JSON and this
//            new DataProcess().ProcessSearchCounts(json, this, searchCat);
//
//        }else{
//            //ajax error, show error code if there is no JSON
//            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
//        }
//    }


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

    protected void DisplaySet()
    {
        GridLayout menuGrid = (GridLayout) this.findViewById(R.id.menu_grid_search);
        int childCount = menuGrid.getChildCount();

        SharedPreferences appInfo = getSharedPreferences("appInfo", 0);

        int deviceWidth = appInfo.getInt("deviceWidth", 0);
        int deviceHeight = appInfo.getInt("deviceHeight", 0);

        int gridHeight = deviceHeight / 10;
        int gridItemWidth = deviceWidth / 5;

        for (int i = 0; i < childCount; i++)
        {
            ImageView currImg = (ImageView) menuGrid.getChildAt(i);

            //currImg.setImageResource(R.drawable.ic_notification);
            currImg.getLayoutParams().width = gridItemWidth;
            currImg.getLayoutParams().height = gridHeight;
            currImg.setBackgroundColor(getResources().getColor(R.color.TMBottomBG));

        }
    }
}
