package com.trademeservices.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.trademeservices.app.search.Results;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;



public class SearchResults extends ActionBarActivity {

    private AQuery aq = new AQuery(this);
    private String cat;
    private int district;
    private int region;
    private String keywords;
    private Data data = Data.getInstance();
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        GetTerms();
        page = 1;
        asyncJsonSearch();
    }

    //Get terms that were passed from the search activity
    private void GetTerms()
    {
        Bundle extras = getIntent().getExtras();
        cat = extras.getString("cat");
        keywords = extras.getString("keywords");
        district = extras.getInt("district");
        region = extras.getInt("region");
    }

    public void asyncJsonSearch(){
        String url = new Constants().getBASE_ADDR() + "Search/General.json?buy=All&category="+cat+"&condition=All&expired=false&pay=All&photo_size=Thumbnail&return_metadata=false&shipping_method=All&sort_order=FeaturedFirst&rows="+ Integer.toString(new Constants().getPAGE_SIZE()) + "&page=" + Integer.toString(page);

        if (region != 0 && region != 100)
        {
            url += "&region=" + Integer.toString(region);
        }
        if (district != 0)
        {
            url += "&district=" + Integer.toString(district);
        }

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
        final LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        final TextView infoText = new TextView(this);
        //infoText.setMaxWidth(data.getConstants().getDeviceWidth());
        int totalPages = data.getResults().getTotal() / data.getResults().getPageSize();
        int showing = data.getResults().getPage() * data.getResults().getPageSize();
        infoText.setText("Total results:" + Integer.toString(data.getResults().getTotal()) +
                " | Page " + Integer.toString(data.getResults().getPage()) + " of " + totalPages + " | " + Integer.toString((showing - 24)) + " - " + Integer.toString(showing));
        infoText.setTextSize(15);
        infoText.setBackgroundColor(Color.GRAY);
        layout.addView(infoText);
        boolean place = true;
        final ScrollView sv = new ScrollView(this);
        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);

        for (Results r : data.getResults().getResults())
        {
            GridLayout rl = new GridLayout(this);
            rl.setMinimumHeight(200);
            if (place)
            {
                rl.setBackgroundColor(Color.LTGRAY);
                place = !place;
            }
            else
            {
                rl.setBackgroundColor(Color.WHITE);
                place = !place;
            }
            rl.setOnClickListener(new ResultOnClick(r,this));
            ImageView img = new ImageView(this);
            img.setMaxWidth(200);
            img.setMinimumWidth(200);
            img.setMinimumHeight(200);
            img.setMaxHeight(200);
            img.setBackgroundColor(Color.WHITE);
            ViewGroup.LayoutParams layoutParams = img.getLayoutParams();

            new DownloadImageTask(img)
                    .execute(r.getPic());

            TextView text = new TextView(this);

            //text.setMaxWidth(data.getConstants().getDeviceWidth() - 200);
            text.setText(r.getTitle());
                      rl.addView(img);
                      rl.addView(text);

            lay.addView(rl);
        }
        Button btn =  new Button(this);
            btn.setText("More");
            //btn.setMinimumWidth(data.getConstants().getDeviceWidth());
            btn.setMinHeight(200);
            btn.setMaxHeight(200);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page++;
                    layout.removeView(sv);
                    layout.removeView(infoText);
                    TextView searchLbl = (TextView) findViewById(R.id.searchingLbl);
                    searchLbl.setVisibility(View.VISIBLE);
                    asyncJsonSearch();
                }
            });

        lay.addView(btn);
        sv.addView(lay);
        layout.addView(sv);
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

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
