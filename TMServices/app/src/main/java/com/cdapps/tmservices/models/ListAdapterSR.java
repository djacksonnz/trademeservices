package com.cdapps.tmservices.models;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdapps.tmservices.R;
import com.cdapps.tmservices.SearchResults;
import com.cdapps.tmservices.data.DownloadImage;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by dave on 10/10/14.
 */
public class ListAdapterSR extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    ListModelSR tempValues = null;
    int i = 0;

    public ListAdapterSR (Activity a, ArrayList d, Resources resLocal)
    {
        activity = a;
        data = d;
        res = resLocal;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if (data.size() <= 0)
            return 1;
       return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public ImageView listingImg;
        public TextView location;
        public TextView listingTitle;
        public TextView listedDate;
        public TextView reviewPercent;
        public TextView reviewCount;
        public ImageView reviewImg;
        public RelativeLayout infoLay;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            vi =inflater.inflate(R.layout.list_item_sr, null);

            holder = new ViewHolder();
            holder.listingImg = (ImageView) vi.findViewById(R.id.listingImgSR);
            holder.location = (TextView) vi.findViewById(R.id.locationTextSR);
            holder.listingTitle = (TextView) vi.findViewById(R.id.listingTitleSR);
            holder.listedDate = (TextView) vi.findViewById(R.id.listedDateSR);
            holder.reviewPercent = (TextView) vi.findViewById(R.id.reviewPercentSR);
            holder.reviewCount = (TextView) vi.findViewById(R.id.numReviewsSR);
            holder.reviewImg = (ImageView) vi.findViewById(R.id.thumbsUpImgSR);
            holder.infoLay = (RelativeLayout) vi.findViewById(R.id.listItemSR);

            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) vi.getTag();
        }

        if (data.size() <= 0)
        {
            //display none
        }
        else
        {
            tempValues = null;
            tempValues = (ListModelSR) data.get(position);

            if (tempValues.getImage() != "no img")
                new DownloadImage(holder.listingImg).execute(tempValues.getImage());

            if (tempValues.isFeatured())
                holder.infoLay.setBackgroundColor(res.getColor(R.color.TMFeatured));
            holder.reviewCount.setText(Integer.toString(tempValues.getTotalReviews()) + " Reviews");
            holder.listingTitle.setText(tempValues.getName());
            holder.listedDate.setText(tempValues.getListedDate());
            holder.reviewPercent.setText(tempValues.getReviewRating() + "%");
            holder.location.setText(tempValues.getLocation());

            vi.setOnClickListener(new OnItemClickListener(tempValues.getListingId()));
        }

        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int listingId;

        OnItemClickListener(int listingId){
            this.listingId = listingId;
        }

        @Override
        public void onClick(View arg0) {


            SearchResults sr = (SearchResults)activity;

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            sr.onItemClick(listingId);
        }
    }



}
