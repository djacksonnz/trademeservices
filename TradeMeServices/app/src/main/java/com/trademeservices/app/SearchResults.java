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
import com.trademeservices.app.cat.Categories;
import com.trademeservices.app.data.Constants;
import com.trademeservices.app.data.DataProcess;
import com.trademeservices.app.data.Database;
import com.trademeservices.app.location.District;
import com.trademeservices.app.location.Region;
import com.trademeservices.app.search.Results;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class SearchResults extends ActionBarActivity {

    private AQuery aq = new AQuery(this);
    private String cat;
    private int district;
    private int region;
    private String keywords;
    private List<com.trademeservices.app.search.SearchResults> searchResultsList = new ArrayList<com.trademeservices.app.search.SearchResults>();
    //private Data data = Data.getInstance();
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
        if (district != 0 && district != 100)
        {
            url += "&district=" + Integer.toString(district);
        }

        aq.ajax(url, JSONObject.class, this, "jsonCallbackSearch");

    }

    public void jsonCallbackSearch(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        if(json != null){
            searchResultsList.add(new DataProcess().ProcessSearchResults(json, this));
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
        Categories category = new Database(this).getSpecCat(cat);
        Region regionData = new Database(this).GetRegion(region);
        infoText.setText(category.getPath() + " | " + regionData.getName());
        if (district != 0 && district != 100)
        {
            District districtData = new Database(this).GetDistrict(district);
            infoText.setText(infoText.getText() + "/" + districtData.getName());
        }
        if (district == 100) {
            infoText.setText(infoText.getText() + "/All");
        }
        infoText.setTextSize(15);
        infoText.setBackgroundColor(Color.GRAY);
        layout.addView(infoText);
        boolean place = true;
        final ScrollView sv = new ScrollView(this);
        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);

        for (com.trademeservices.app.search.SearchResults s : searchResultsList) {
            for (Results r : s.getResults()) {
                GridLayout listingGl = new GridLayout(this);
                listingGl.setColumnCount(2);
                listingGl.setMinimumHeight(200);
                if (place) {
                    listingGl.setBackgroundColor(Color.LTGRAY);
                    place = !place;
                } else {
                    listingGl.setBackgroundColor(Color.WHITE);
                    place = !place;
                }
                listingGl.setOnClickListener(new ResultOnClick(r, this));
                ImageView img = new ImageView(this);
                img.setMaxWidth(200);
                img.setMinimumWidth(200);
                img.setMinimumHeight(200);
                img.setMaxHeight(200);
                img.setBackgroundColor(Color.WHITE);
                ViewGroup.LayoutParams layoutParams = img.getLayoutParams();

                if (r.getPicHref() == "")
                    r.setPicHref("http://www.trademe.co.nz/images/NewSearchCards/LVIcons/noPhoto_160x120.png");
                new DownloadImageTask(img)
                        .execute(r.getPicHref());

                GridLayout gl2 = new GridLayout(this);
                gl2.setRowCount(4);
                TextView title = new TextView(this);
                title.setWidth(880);
                TextView catLoc = new TextView(this);
                catLoc.setWidth(880);
                TextView dateAdded = new TextView(this);
                dateAdded.setWidth(880);

                title.setText(r.getTitle());
                catLoc.setText(r.getCategory() + " | " + r.getRegion());
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                dateAdded.setText(df.format(r.getStartDate()));

                //gl2.addView(title);
                //gl2.addView(catLoc);
                //gl2.addView(dateAdded);

                GridLayout reviewsGl = new GridLayout(this);
                TextView numReviews = new TextView(this);
                TextView percentPos = new TextView(this);
                ImageView thumbUp = new ImageView(this);

                if (r.getTotalReviews() == 0)
                {
                    reviewsGl.setColumnCount(1);
                    numReviews.setText("No Reviews");
                    reviewsGl.addView(numReviews);
                }
                else
                {
                    reviewsGl.setColumnCount(3);
                    numReviews.setText(Integer.toString(r.getTotalReviews()) + " Reviews ");
                    int posReviews = (r.getPositiveReviews() / r.getTotalReviews()) * 100;
                    percentPos.setText(Integer.toString(posReviews) + '%');
                    new DownloadImageTask(thumbUp)
                            .execute("http://www.trademe.co.nz/Images/services/thumbs_up_featured.gif");
                    reviewsGl.addView(numReviews);
                    reviewsGl.addView(percentPos);
                    reviewsGl.addView(thumbUp);
                }
                gl2.addView(reviewsGl);

                listingGl.addView(img);
                listingGl.addView(gl2);

                lay.addView(listingGl);
            }
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
        intent.putExtra("id", r.getListingId());
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
