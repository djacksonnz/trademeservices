package com.trademeservices.app;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.trademeservices.app.search.Results;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;


public class SearchResults extends ActionBarActivity {

    private AQuery aq = new AQuery(this);
    private String cat;
    private int district;
    private String keywords;
    private Data data = Data.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Bundle extras = getIntent().getExtras();
        cat = extras.getString("cat");
        keywords = extras.getString("keywords");
        district = extras.getInt("location");

        asyncJsonSearch();
    }

    public void asyncJsonSearch(){
        String url = new Constants().getBASE_ADDR() + "Search/General.json?buy=All&category="+cat+"&condition=All&expired=false&pay=All&photo_size=Thumbnail&return_metadata=false&shipping_method=All&sort_order=FeaturedFirst&user_region=" + Integer.toString(district);
//        if (keywords != null) {
//            url += "&search_string=" + keywords;
//        }
        Log.i("out", url);
        aq.ajax(url, JSONObject.class, this, "jsonCallbackSearch");

    }

    public void jsonCallbackSearch(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        if(json != null){
            new DataProcess().ProcessSearchResults(json);
            DisplayResults();
        }else{
            //ajax error, show error code
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    private void DisplayResults()
    {
        TextView searchLbl = (TextView) findViewById(R.id.searchingLbl);
        searchLbl.setVisibility(View.GONE);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        for (Results r : data.getResults().getResults())
        {
            RelativeLayout rl = new RelativeLayout(this);
            rl.setOnClickListener(new ResultOnClick(r,this));

            TextView text = new TextView(this);
            text.setText(r.getTitle());
                      rl.addView(text);
            layout.addView(rl);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_results, menu);
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

class ResultOnClick implements View.OnClickListener
{
    private Activity act;
    private Results r;

    public ResultOnClick(Results r, Activity act)
    {
        this.r = r;
        this.act = act;
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(act, Listing.class);
        intent.putExtra("id", r.getId());
        act.startActivity(intent);
    }
}
