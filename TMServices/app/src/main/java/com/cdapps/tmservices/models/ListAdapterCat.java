package com.cdapps.tmservices.models;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdapps.tmservices.R;
import com.cdapps.tmservices.Search;
import com.cdapps.tmservices.data.DownloadImage;

import java.util.ArrayList;

/**
 * Created by dave on 13/10/14.
 */
public class ListAdapterCat extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    ListModelCat tempValues = null;
    int i = 0;

    public ListAdapterCat (Activity a, ArrayList d, Resources resLocal)
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
        public TextView count;
        public TextView name;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.search_list_item, null);

            holder = new ViewHolder();
            holder.count = (TextView) vi.findViewById(R.id.catCountMD);
            holder.name = (TextView) vi.findViewById(R.id.catNameMD);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        if (data.size() <= 0) {
            //display none
        } else {
            tempValues = null;
            tempValues = (ListModelCat) data.get(position);

            holder.name.setText(tempValues.getCatName());
            holder.count.setText(Integer.toString(tempValues.getResults()));

            vi.setOnClickListener(new OnItemClickListener(tempValues.getCatNumber()));
        }

        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private String newCatId;

        OnItemClickListener(String newCatId){
            this.newCatId = newCatId;
        }

        @Override
        public void onClick(View arg0) {


            Search s = (Search)activity;

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            s.onItemClick(newCatId);
        }
    }

}
