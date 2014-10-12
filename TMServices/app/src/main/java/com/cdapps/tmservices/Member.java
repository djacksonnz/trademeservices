package com.cdapps.tmservices;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.cdapps.tmservices.data.Constants;
import com.cdapps.tmservices.data.DataProcess;
import com.cdapps.tmservices.gen.MemberInfo;

import org.json.JSONException;
import org.json.JSONObject;


public class Member extends Activity {

    private AQuery aq = new AQuery(this);
    private int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getIntent().getExtras();
        memberId = bundle.getInt("id");

        DisplayMenu();
        SetLinks();
        asyncJson();
    }

    //Async method to call for the categories from the API
    public void asyncJson(){
        //Set url getting address from constants class
        //String url = "http://api.trademe.co.nz/v1/Search/General.json?buy=All&category=9334&condition=All&expired=false&photo_size=thumbnail&return_metadata=false&shipping_method=All&rows=25&page=1";
        String url = new Constants().getBASE_ADDR() + "Member/" + memberId + "/Profile.json";

        Log.i("out", url);

        //Run AndroidQuery AJAX call running to jsonCallbackCat when it is completed
        aq.ajax(url, JSONObject.class, this, "jsonCallback");
    }

    //Method called when cat async call is completed
    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        //Check to see if there was a JSON object returned
        if(json != null){
            DisplayInfo(new DataProcess().ProcessMemberInfo(json));
        }else{
            //ajax error, show error code if there is no JSON
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    public void DisplayInfo(MemberInfo m)
    {
        TextView nickname = (TextView) findViewById(R.id.nicknameM);
        nickname.setText(m.getMember().getNickname());

        Log.i("out", Integer.toString(m.getMember().getFeedbackCount()));

        TextView feedback = (TextView) findViewById(R.id.feedbackM);
        feedback.setText(Integer.toString(m.getMember().getFeedbackCount()));

        if (m.getMember().isAddressVerified())
        {
            ImageView addressVerified = (ImageView) findViewById(R.id.addressVerifiedM);
            addressVerified.setVisibility(View.GONE);
        }

        double positiveFeedback = m.getMember().getUniquePositive();
        double totalFeedback = m.getMember().getUniqueNegative() + m.getMember().getUniquePositive();

        TextView positiveCount = (TextView) findViewById(R.id.positiveM);
        positiveCount.setText(((positiveFeedback / totalFeedback) * 100) + "% positive feedback");

        TextView joinDate = (TextView) findViewById(R.id.joinDateM);
        joinDate.setText(m.getMember().getJoinDate().toString());

        TextView name = (TextView) findViewById(R.id.nameM);
        name.setText(m.getFirstName());

        TextView location = (TextView) findViewById(R.id.locationM);
        location.setText(m.getMember().getSuburb());

        TextView occupation = (TextView) findViewById(R.id.occupationM);
        occupation.setText(m.getOccupation());

        TextView biography = (TextView) findViewById(R.id.biographyM);
        biography.setText(m.getBiography());

        TextView quote = (TextView) findViewById(R.id.quoteM);
        quote.setText(m.getQuote());
    }

    private void SetLinks() {
        ImageView back = (ImageView) findViewById(R.id.backArrowM);
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
        getMenuInflater().inflate(R.menu.member, menu);
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

    protected void DisplayMenu()
    {
        GridLayout menuGrid = (GridLayout) this.findViewById(R.id.menuM);
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
