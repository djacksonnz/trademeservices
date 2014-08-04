package com.trademeservices.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.androidquery.AQuery;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;
import com.trademeservices.app.cat.Categories;
import com.trademeservices.app.location.District;
import com.trademeservices.app.location.Region;
import com.trademeservices.app.search.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Data data = Data.getInstance();
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PopulateSpinners();
    }

    private void PopulateSpinners()
    {
        RegionSpinner();
        CatSpinner();
    }

    //Method that populates and controls the region spinner/dropdown
    private void RegionSpinner()
    {
        //Get regions from database
        List<Region> regions = new Database(ctx).GetRegions();
        //Get spinner from activity
        final Spinner spinner = (Spinner)this.findViewById(R.id.regionSpinner);
        //Make new adapter holding Region list as its type
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                regions);
        //Set spinner values to adapter
        spinner.setAdapter(adapter);
        //Set inital value to be "All"
        spinner.setSelection(regions.size() - 1);
        //Method for on selection changed
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Get selected region object
                Region region = (Region) spinner.getSelectedItem();
                //Get the district spinner
                Spinner s = (Spinner) findViewById(R.id.districtSpinner);

                //If its not equal to 100 (All selection)
                if (region.getId() != 100){
                    //Get list of districts where there region is the selected region
                    List<District> districts = new Database(ctx).GetDistricts(region.getId());

                    //If there is at least 1 district
                    if (districts.size() > 0)
                    {
                        //Add a record for all districts
                        districts.add(0, new District(100,"All",region.getId()));
                        //Process district spinner
                        DistrictSpinner(i,s, districts);
                    }
                }
                else
                {
                    //If all is selected set district spinner to invisible
                    s.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    //Method that populates and controls the region spinner/dropdown
    private void DistrictSpinner(int pos, Spinner s, List<District> districts)
    {
        s.setVisibility(View.VISIBLE);
        //Create adapter from district list
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                districts);

        s.setAdapter(adapter);
        s.setSelection(0);
    }

    //Display and populate sub cat spinner
    private void SubCatSpinner(int pos, Spinner s, List<Categories> subCats)
    {
        s.setVisibility(View.VISIBLE);
        //Create adapter from cats list
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                subCats);
        //Set spinner values to be the values in array adapter
        s.setAdapter(spinnerArrayAdapter);
        //Select All to be default value
        s.setSelection(subCats.size() - 1);
    }

    //Populates drop down lists for the search screen - Main cats here
    private void CatSpinner()
    {
        //Pull list of main categories from the database by sending the main cat through 9334- is every thing in services
        List<Categories> cats = new Database(ctx).getCat("9334-");
        //Get spinner from the view
        final Spinner spinner = (Spinner)this.findViewById(R.id.catSpinner);
        //Create adapter from cats list
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                cats);
        //Set spinner values to be the values in array adapter
        spinner.setAdapter(spinnerArrayAdapter);
        //Listener for when selection is changed, this will initate and show subcat spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Get the selected category object
                Categories selectedMain = (Categories)spinner.getSelectedItem();
                //Get subcat spinner from Activity
                Spinner s = (Spinner) findViewById(R.id.subCatSpinner);

                //If selected cat is all, make sure second spinner is hidden, if not get the sub cats
                if (!selectedMain.getName().equals("All")) {
                    //Get the subCategories for the selected cat by passing in the number of the main cat
                    List<Categories> subCats = new Database(ctx).getCat(selectedMain.getNumber());

                    //If there is a result add all and then call SubCatSpinner to process display
                    if (subCats.size() > 0) {
                        //Add a record for All sub cats, providing info from the main selected as path and number
                        subCats.add(new Categories("All",selectedMain.getNumber(),selectedMain.getPath(),true,false,false,selectedMain.getMainCat()));
                        SubCatSpinner(i,s,subCats);
                    }
                }
                else
                {
                    //Set spinner to invisible if All is selected
                    s.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    //Method to gather and submit search terms
    public void searchClick(View view)
    {
        Spinner regionSpin = (Spinner)this.findViewById(R.id.regionSpinner);
        Spinner districtSpin = (Spinner)this.findViewById(R.id.districtSpinner);
        Spinner catSpin = (Spinner)this.findViewById(R.id.catSpinner);
        Spinner subCatSpin = (Spinner)this.findViewById(R.id.subCatSpinner);

        String cat;
        if (subCatSpin.getVisibility() == View.VISIBLE) {
            Categories selected = (Categories) subCatSpin.getSelectedItem();
            cat = selected.getNumber();
        }
        else {
            Categories selected = (Categories) catSpin.getSelectedItem();
            cat = selected.getNumber();
        }

        Region selRegion = (Region)regionSpin.getSelectedItem();
        int region = selRegion.getId();

        int district = 0;
        if (districtSpin.getVisibility() == View.VISIBLE) {
            District selDistrict = (District) districtSpin.getSelectedItem();
            district = selDistrict.getId();
        }

        String keywords = "";
        EditText keywordsIn = (EditText) findViewById(R.id.keywordInput);
        keywords = keywordsIn.getText().toString();
        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra("district", district);
        intent.putExtra("region", region);
        intent.putExtra("cat", cat);
        intent.putExtra("keywords", keywords);
        startActivity(intent);
        Log.i("out", keywords + " " + Integer.toString(region) + " " + Integer.toString(district) + " " + cat);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Method that controls the pressing of the back button, not designed to go back from here
    @Override
    public void onBackPressed(){
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
