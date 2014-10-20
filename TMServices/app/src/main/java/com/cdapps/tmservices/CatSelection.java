package com.cdapps.tmservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cdapps.tmservices.cat.Categories;
import com.cdapps.tmservices.data.Database;
import com.cdapps.tmservices.data.ListingOptions;

import java.util.List;


public class CatSelection extends Activity {

    ListingOptions data = ListingOptions.getInstance();
    String baseCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_selection);

        baseCat = "9334-";
        SetDisp();

        findViewById(R.id.backText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.backImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void SetDisp()
    {
        LinearLayout list = (LinearLayout) findViewById(R.id.list);
        list.removeAllViews();

        List<Categories> categoriesList = new Database(this).getCat(baseCat);

        if (baseCat.equals("9334-")) {
            categoriesList.remove(0);
        }

        if (categoriesList.size() <= 0)
        {
            data.setCat(baseCat);
            Categories specCat = new Database(this).getSpecCat(baseCat);
            Toast.makeText(this, specCat.getName().toString() + " Selected", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {

            View v;


            for (final Categories c : categoriesList) {
                v = LayoutInflater.from(this).inflate(R.layout.list_service_item, null);

                TextView title = (TextView) v.findViewById(R.id.catNameMD);
                title.setText(c.getName());

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        baseCat = c.getNumber();
                        SetDisp();
                    }
                });
                list.addView(v);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cat_selection, menu);
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
