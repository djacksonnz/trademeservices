package com.trademeservices.app.disp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trademeservices.app.R;
import com.trademeservices.app.act.search_tabs.CategoryTab;

import java.util.ArrayList;

/**
 * Created by jacksdl2 on 17/09/2014.
 */
public class CatAdapter extends BaseAdapter {

    private Activity act;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    private ListModelCat tempValues = null;
    private int i = 0;

    public CatAdapter(Activity act, ArrayList data, Resources res)
    {
        this.act = act;
        this.data = data;
        this.res = res;

        inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public static class ViewHolder{

        public TextView catName;
        public TextView listingCount;
        public ImageView moreIcon;

    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.cat_tab_item, null);


            holder = new ViewHolder();
            holder.catName = (TextView) vi.findViewById(R.id.cat_tab_name);
            holder.listingCount = (TextView) vi.findViewById(R.id.cat_tab_count);
            holder.moreIcon = (ImageView) vi.findViewById(R.id.cat_tab_img);

            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) vi.getTag();
        }

        tempValues = null;

        tempValues = (ListModelCat) data.get(position);

        holder.catName.setText(tempValues.getName());
        holder.listingCount.setText(Integer.toString(tempValues.getListings()));

        if (tempValues.getListings() == 0)
            holder.moreIcon.setImageResource(R.drawable.none);
        else
            holder.moreIcon.setImageResource(R.drawable.more_grey);

        vi.setOnClickListener(new CategoryTab.OnCatClick(tempValues.getPath()));

        return vi;
    }


}
