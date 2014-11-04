package com.cdapps.tmservices.models;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdapps.tmservices.R;
import com.cdapps.tmservices.Search;

import java.util.ArrayList;

/**
 * Created by dave on 13/10/14.
 */
public class ListAdapterLoc extends BaseAdapter implements View.OnClickListener{

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    ListModelLoc tempValues = null;
    int i = 0;

    public ListAdapterLoc (Activity a, ArrayList d, Resources resLocal)
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
        public TextView name;
        public TextView selected;
        public ImageView more;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.search_list_item_loc , null);

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.locNameMD);
            holder.selected = (TextView) vi.findViewById(R.id.textView);
            holder.more = (ImageView) vi.findViewById(R.id.imageView);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        if (data.size() <= 0) {
            holder.name.setVisibility(View.GONE);
            holder.more.setVisibility(View.GONE);
            holder.selected.setVisibility(View.VISIBLE);
        } else {
            tempValues = null;
            tempValues = (ListModelLoc) data.get(position);

            holder.name.setText(tempValues.getName());


            vi.setOnClickListener(new OnItemClickListener(tempValues.getNumber()));
        }

        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int num;

        OnItemClickListener(int num){
            this.num = num;
        }

        @Override
        public void onClick(View arg0) {


            Search s = (Search)activity;

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            s.onItemClickLoc(num);
        }
    }
}
