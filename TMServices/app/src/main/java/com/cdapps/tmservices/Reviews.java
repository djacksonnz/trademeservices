package com.cdapps.tmservices;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cdapps.tmservices.data.Constants;
import com.cdapps.tmservices.data.DataProcess;
import com.cdapps.tmservices.reviews.Review;
import com.cdapps.tmservices.reviews.ReviewResults;

import org.json.JSONException;
import org.json.JSONObject;


public class Reviews extends Activity {

    private AQuery aq = new AQuery(this);
    private int listingId;
    private int totalReviews;
    private int posReviews;
    private String listingTitle;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getIntent().getExtras();
        listingId = bundle.getInt("id");
        totalReviews = bundle.getInt("totalReviews");
        posReviews = bundle.getInt("posReviews");
        listingTitle = bundle.getString("title");
        page = 1;

        SetLinks();

        asyncJsonSearch();
    }


    public void asyncJsonSearch(){
        String url = new Constants().getBASE_ADDR() + "Listings/"
                + Integer.toString(listingId) + "/reviews.json?page=" + page;

        //Setup new Ajax call back as a JSON object
        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        //Pass in url return type and callback method
        cb.url(url).type(JSONObject.class).weakHandler(this, "jsonCallback");
        //Add authentication header
        cb.header("Authorization", "OAuth oauth_consumer_key=" + new Constants().getCONSUMER_KEY() + ", oauth_signature_method=\"PLAINTEXT\", oauth_signature=" + new Constants().getCONSUMER_SECRET() + '&');
        //Run aq call on the ajax callback object
        aq.ajax(cb);

    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        if(json != null){
            ProcessCall(new DataProcess().ProcessReviews(json));
        }else{
            //ajax error, show error code
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    private void ProcessCall(ReviewResults rr)
    {
        if (rr.getReviewsList().size() <= 0)
        {
            findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            findViewById(R.id.reviewNumR).setVisibility(View.GONE);
            findViewById(R.id.addPhotoSM).setVisibility(View.GONE);
        }
        else {
            TextView reviewNum = (TextView) findViewById(R.id.reviewNumR);
            double percent = 100;
            String text = percent + "% positive reviews from " + totalReviews;
            reviewNum.setText(text);

            View view;
            ViewGroup parent = (ViewGroup) findViewById(R.id.reviewLayR);

            for (Review r : rr.getReviewsList()) {
                view = LayoutInflater.from(this).inflate(R.layout.reviews_layout, null);

                ImageView thumb = (ImageView) view.findViewById(R.id.thumbImg);
                if (r.isPositive())
                    thumb.setImageResource(R.drawable.thumb_up);
                else
                    thumb.setImageResource(R.drawable.thumb_dwn);

                TextView reviewText = (TextView) view.findViewById(R.id.reviewRE);
                reviewText.setText(r.getReviewText());

                TextView member = (TextView) view.findViewById(R.id.nicknameRE);
                member.setText(r.getMember().getNickname());

                TextView feedback = (TextView) view.findViewById(R.id.feedbackRE);
                feedback.setText(Integer.toString(r.getMember().getFeedbackCount()));

                ImageView av = (ImageView) view.findViewById(R.id.avRE);
                if (!r.getMember().isAddressVerified())
                    av.setVisibility(View.GONE);

                TextView response = (TextView) view.findViewById(R.id.responseRE);
                if (r.getResponse() == "")
                    response.setVisibility(View.GONE);
                else
                    response.setText(r.getResponse());

                parent.addView(view);

            }

            RelativeLayout relativeLayout = (RelativeLayout) parent.getChildAt(parent.getChildCount() - 1);
            relativeLayout.removeViewAt(1);
        }
    }


    private void SetLinks() {
        ImageView back = (ImageView) findViewById(R.id.backBtnR);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reviews, menu);
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
