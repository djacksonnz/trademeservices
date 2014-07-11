package com.trademeservices.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Data data = Data.getInstance();
    private AQuery aq = new AQuery(this);

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

    private void RegionSpinner()
    {
        String[] array_spinner = new String[data.getRegionList().size()];

        for (int i = 0; i < array_spinner.length; i++ )
        {
            array_spinner[i] = data.getRegionList().get(i).getName();
        }

        Spinner s = (Spinner) findViewById(R.id.regionSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                array_spinner);

        s.setAdapter(adapter);
        s.setSelection(array_spinner.length - 1);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Spinner s = (Spinner) findViewById(R.id.districtSpinner);

                if (data.getRegionList().get(i).getDistricts().size() != 0)
                {
                    DistrictSpinner(i,s);
                }
                else
                {
                    s.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    private void DistrictSpinner(int pos, Spinner s)
    {
        s.setVisibility(View.VISIBLE);
        String[] array_spinner = new String[data.getRegionList().get(pos).getDistricts().size() + 1];

        for (int j = 0; j < array_spinner.length - 1; j++ )
        {
            array_spinner[j] = data.getRegionList().get(pos).getDistricts().get(j).getName();
        }

        array_spinner[array_spinner.length - 1] = "All";
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
        s.setSelection(array_spinner.length - 1);
    }

    private void SubCatSpinner(int pos, Spinner s)
    {
        s.setVisibility(View.VISIBLE);
        String[] array_spinner = new String[data.getCategories().get(pos).getSubCats().size() + 1];

        for (int j = 0; j < array_spinner.length - 1; j++ )
        {
            array_spinner[j] = data.getCategories().get(pos).getSubCats().get(j).getName();
        }

        array_spinner[array_spinner.length - 1] = "All";
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
        s.setSelection(array_spinner.length - 1);
    }

    private void CatSpinner()
    {
        String[] array_spinner = new String[data.getCategories().size() + 1];

        for (int i = 0; i < array_spinner.length - 1; i++ )
        {
            array_spinner[i] = data.getCategories().get(i).getName();
        }
        array_spinner[array_spinner.length - 1] = "All";

        Spinner s = (Spinner) findViewById(R.id.catSpinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                array_spinner);
        s.setAdapter(adapter);
        s.setSelection(array_spinner.length - 1);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Spinner s = (Spinner) findViewById(R.id.subCatSpinner);

                if (i != data.getCategories().size()) {
                    if (data.getCategories().get(i).getSubCats().size() != 0) {
                        SubCatSpinner(i, s);
                    }
                }
                else
                {
                    s.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    public void searchClick(View view)
    {
        String cat = "";
        String keywords = "";
        int region = 0;
        int district = 0;

        Spinner s = (Spinner) findViewById(R.id.regionSpinner);
        String regionName = s.getSelectedItem().toString();
        int pos = 0;
        for (int i = 0; i < data.getRegionList().size(); i++)
        {
            if (data.getRegionList().get(i).getName() == regionName)
            {
                region = data.getRegionList().get(i).getId();
                pos = i;
                break;
            }
        }

        String districtName;
        s = (Spinner) findViewById(R.id.districtSpinner);
        if (s.getVisibility() != View.GONE)
        {
            districtName = s.getSelectedItem().toString();
            for (District d : data.getRegionList().get(pos).getDistricts())
            {
                if (d.getName() == districtName)
                {
                    district = d.getId();
                }
            }
        }

        s = (Spinner) findViewById(R.id.catSpinner);

        if (s.getSelectedItem().toString() == "All")
        {
            cat = "9334-";
        }
        else
        {
            String catName = s.getSelectedItem().toString();
            pos = 0;
            for (int i = 0; i < data.getCategories().size(); i++)
            {
                if (data.getCategories().get(i).getName() == catName)
                {
                    s = (Spinner) findViewById(R.id.subCatSpinner);
                    if (s.getVisibility() == View.GONE || s.getSelectedItem().toString() == "All")
                    {
                        cat = data.getCategories().get(i).getNumber();
                    }
                    else
                    {
                        String subCatName = s.getSelectedItem().toString();
                        for (Categories c : data.getCategories().get(i).getSubCats())
                        {
                            if (c.getName() == subCatName)
                            {
                                cat = c.getNumber();
                            }
                        }
                    }
                }
            }
        }

        EditText keywordsIn = (EditText) findViewById(R.id.keywordInput);
        keywords = keywordsIn.getText().toString();

        Log.i("out", keywords + " " + Integer.toString(region) + " " + Integer.toString(district) + " " + cat);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
