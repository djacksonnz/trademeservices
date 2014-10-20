package com.cdapps.tmservices;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cdapps.tmservices.data.Database;
import com.cdapps.tmservices.data.ListingOptions;
import com.cdapps.tmservices.location.District;
import com.cdapps.tmservices.location.Region;

import java.util.List;


public class LocSelection extends Activity {

    ListingOptions data = ListingOptions.getInstance();
    boolean mainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_selection);

        InitSetup();
    }

    private void ShowMain() {
        mainPage = true;
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText("Location");

        findViewById(R.id.info).setVisibility(View.VISIBLE);
        findViewById(R.id.region).setVisibility(View.GONE);
        findViewById(R.id.district).setVisibility(View.GONE);
    }

    private void InitSetup() {
        EditText text;
        if (data.getStreetNum() != 0) {
            text = (EditText) findViewById(R.id.editText);
            text.setText(data.getStreetNum());
        }

        if (data.getFlat() != 0) {
            text = (EditText) findViewById(R.id.editText2);
            text.setText(data.getFlat());
        }

        if (data.getStreet() != null && data.getStreet().trim().length() > 0) {
            text = (EditText) findViewById(R.id.editText3);
            text.setText(data.getStreet());
        }

        ShowMain();

        findViewById(R.id.relativeLayout6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupRegion();
            }
        });

        findViewById(R.id.relativeLayout7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getRegionId() == 0 || data.getRegionId() == 100) {
                    Toast.makeText(view.getContext(), "You need to select a region first", Toast.LENGTH_SHORT).show();
                }
                else {
                    SetupDistrict();
                }

            }
        });

        findViewById(R.id.backImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainPage) {
                    EditText e;
                    e = (EditText) findViewById(R.id.editText);
                    data.setStreetNum(Integer.parseInt(e.getText().toString()));
                    e = (EditText) findViewById(R.id.editText2);
                    data.setFlat(Integer.parseInt(e.getText().toString()));
                    e = (EditText) findViewById(R.id.editText3);
                    data.setStreet(e.getText().toString());
                    finish();
                }
                else
                {
                    ShowMain();
                }
            }
        });
        findViewById(R.id.backText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainPage) {
                    finish();
                }
                else
                {
                    ShowMain();
                }
            }
        });
    }

    private void SetupRegion() {
        Hide();
        mainPage = false;
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText("Region");
        findViewById(R.id.region).setVisibility(View.VISIBLE);
        List<Region> regionList = new Database(this).GetRegions();

        LinearLayout rLay = (LinearLayout) findViewById(R.id.rLay);
        rLay.removeAllViews();

        View v;

        for (final Region r : regionList) {
            v = LayoutInflater.from(this).inflate(R.layout.list_service_item, null);
            TextView text = (TextView) v.findViewById(R.id.catNameMD);
            text.setText(r.getName());

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.setRegionId(r.getId());
                    data.setDistrictId(0);
                    ShowMain();
                }
            });

            rLay.addView(v);
        }
    }

    private void Hide() {
        findViewById(R.id.region).setVisibility(View.GONE);
        findViewById(R.id.info).setVisibility(View.GONE);
        findViewById(R.id.district).setVisibility(View.GONE);
    }

    private void SetupDistrict() {
        Hide();
        mainPage = false;
        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText("District");
        findViewById(R.id.district).setVisibility(View.VISIBLE);
        List<District> districtList = new Database(this).GetDistricts(data.getRegionId());

        LinearLayout dLay = (LinearLayout) findViewById(R.id.dLay);
        dLay.removeAllViews();

        View v;

        for (final District d : districtList) {
            v = LayoutInflater.from(this).inflate(R.layout.list_service_item, null);
            TextView text = (TextView) v.findViewById(R.id.catNameMD);
            text.setText(d.getName().toString());

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.setDistrictId(d.getId());
                    ShowMain();
                }
            });

            dLay.addView(v);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loc_selection, menu);
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
