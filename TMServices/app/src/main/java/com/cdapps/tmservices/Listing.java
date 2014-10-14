package com.cdapps.tmservices;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.cdapps.tmservices.data.Constants;
import com.cdapps.tmservices.data.DataProcess;
import com.cdapps.tmservices.data.DownloadImage;
import com.cdapps.tmservices.gen.ContactDetails;
import com.cdapps.tmservices.listing.Attribute;
import com.cdapps.tmservices.listing.Photo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;


public class Listing extends Activity {

    private AQuery aq = new AQuery(this);
    private Context ctx;

    private int listingId;
    private int memberId;
    private int totalReviews;
    private int posReviews;
    private String listingTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        ctx = this;

        DisplayMenu();
        SetLinks();

        Bundle bundle = getIntent().getExtras();
        listingId = bundle.getInt("id");
        Log.i("out", Integer.toString(listingId));
        asyncJsonSearch();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listing, menu);

        return true;
    }

    @Override
    public void onBackPressed() {

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

    public void asyncJsonSearch(){
        String url = new Constants().getBASE_ADDR() + "Listings/" + Integer.toString(listingId) + ".json";

        Log.i("out", url);
        aq.ajax(url, JSONObject.class, this, "jsonCallbackSearch");

    }

    public void jsonCallbackSearch(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        if(json != null){
            DisplayInfo(new DataProcess().ProcessListing(json));
        }else{
            //ajax error, show error code
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    private void DisplayInfo(com.cdapps.tmservices.listing.Listing listing)
    {
        memberId = listing.getMember().getMemberId();
        totalReviews = listing.getTotalReviewCount();
        posReviews = listing.getPositiveReviewCount();
        listingTitle = listing.getTitle();

        TextView title = (TextView) findViewById(R.id.titleTextL);
        title.setText(listing.getTitle());

        TextView date = (TextView) findViewById(R.id.dateTextL);
        date.setText(listing.getStartDate().toString());

        TextView description = (TextView) findViewById(R.id.descriptionTextL);
        description.setText(listing.getBody());

        TextView memberName = (TextView) findViewById(R.id.memberNameL);
        memberName.setText(listing.getMember().getNickname());

        TextView listingId = (TextView) findViewById(R.id.listingIdL);
        listingId.setText("Listing #: " + listing.getListingId());

        TextView viewCount = (TextView) findViewById(R.id.viewCountL);
        viewCount.setText("Views: " + listing.getViewCount());

        TextView categoryTxt = (TextView) findViewById(R.id.catTextL);
        categoryTxt.setText(listing.getCategoryName());
        View view;
        ViewGroup parent = (ViewGroup) findViewById(R.id.detailsLayL);

        for (int i = 0; i < listing.getAttributes().size(); i++)
        {
            view = LayoutInflater.from(this).inflate(R.layout.attributes_layout, null);

            TextView attrName = (TextView) view.findViewById(R.id.attrNameAL);
            attrName.setText(listing.getAttributes().get(i).getDisplayName());

            TextView attrValue = (TextView) view.findViewById(R.id.attrValueAL);
            attrValue.setText(listing.getAttributes().get(i).getValue());

            parent.addView(view);
        }
        RelativeLayout relativeLayout = (RelativeLayout) parent.getChildAt(parent.getChildCount() - 1);
        relativeLayout.removeViewAt(2);

        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.imgScrollL);

        int imgHeight = hsv.getHeight();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.imagesL);

        for (final Photo p : listing.getPhotos())
        {
            ImageView img = new ImageView(this);
            new DownloadImage(img).execute(p.getLge());
            img.setMaxHeight(imgHeight);
            //img.setMinimumHeight(imgHeight);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertadd = new AlertDialog.Builder(
                            ctx);
                    LayoutInflater factory = LayoutInflater.from(ctx);
                    final View imgview = factory.inflate(R.layout.view_image, null);
                    ImageView bigImg = (ImageView) imgview.findViewById(R.id.viewImage);
                    new DownloadImage(bigImg).execute(p.getFull());
                    alertadd.setView(imgview);
                    alertadd.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {

                        }
                    });

                    alertadd.show();
                }
            });

            img.setPadding(10,10,10,10);

            linearLayout.addView(img);

        }

    }

    private void SetLinks() {
        RelativeLayout email = (RelativeLayout) findViewById(R.id.emailLinkL);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, SendMessage.class);
                startActivity(intent);
            }
        });

        ImageView back = (ImageView) findViewById(R.id.backArrowL);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView addWatchlist = (ImageView) findViewById(R.id.watchlistL);
        addWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Listing added to Watchlist\n(Not implemented)",
                        Toast.LENGTH_LONG).show();
            }
        });

        RelativeLayout addWatchlistText = (RelativeLayout) findViewById(R.id.addToWatchlistTextL);
        addWatchlistText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Listing added to Watchlist\n(Not implemented)",
                        Toast.LENGTH_LONG).show();
            }
        });

        RelativeLayout reviews = (RelativeLayout) findViewById(R.id.reviewsL);
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, Reviews.class);
                intent.putExtra("id", listingId);
                intent.putExtra("totalReviews", totalReviews);
                intent.putExtra("posReviews", posReviews);
                intent.putExtra("title", listingTitle);
                startActivity(intent);
            }
        });

        RelativeLayout share = (RelativeLayout) findViewById(R.id.shareL);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Listing Shared\n(Not implemented)",
                        Toast.LENGTH_LONG).show();
            }
        });

        RelativeLayout report = (RelativeLayout) findViewById(R.id.reportL);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Listing Reported\n(Not implemented)",
                        Toast.LENGTH_LONG).show();
            }
        });

        RelativeLayout phone = (RelativeLayout) findViewById(R.id.phoneL);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Calling\n(Not implemented)",
                        Toast.LENGTH_LONG).show();
            }
        });

        RelativeLayout mobile = (RelativeLayout) findViewById(R.id.mobileL);
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Calling\n(Not implemented)",
                        Toast.LENGTH_LONG).show();
            }
        });

        RelativeLayout member = (RelativeLayout) findViewById(R.id.nicknameM);
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, Member.class);
                intent.putExtra("id", memberId);
                startActivity(intent);
            }
        });

    }

    private void SetupMenu() {
        findViewById(R.id.notificationsImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Notifications.class);
                finish();
                startActivity(intent);
            }});

        findViewById(R.id.accountImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Account.class);
                finish();
                startActivity(intent);
            }});

        findViewById(R.id.searchImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Search.class);
                finish();
                startActivity(intent);
            }
        });

        findViewById(R.id.listServiceImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), Notifications.class);
                //finish();
                //startActivity(intent);
            }});

        findViewById(R.id.watchlistImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Watchlist.class);
                finish();
                startActivity(intent);
            }});

    }

    protected void DisplayMenu()
    {
        GridLayout menuGrid = (GridLayout) this.findViewById(R.id.menuL);
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
