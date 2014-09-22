package com.trademeservices.app.act.search_tabs;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.trademeservices.app.R;
import com.trademeservices.app.cat.Categories;
import com.trademeservices.app.data.Constants;
import com.trademeservices.app.data.DataProcess;
import com.trademeservices.app.data.Database;
import com.trademeservices.app.data.SearchVariables;
import com.trademeservices.app.disp.CatAdapter;
import com.trademeservices.app.disp.ListModelCat;
import com.trademeservices.app.location.Region;
import com.trademeservices.app.search.SearchCounts;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacksdl2 on 8/09/2014.
 */
public class CategoryTab extends Fragment {

    private SearchVariables searchVariables = SearchVariables.getInstance();
    private Context ctx;
    private Activity act;
    private String baseCat;
    private List<SearchCounts> searchCounts;
    private View view;
    private ListView list;
    private CatAdapter adapter;
    private ArrayList<ListModelCat> listModelCat = new ArrayList<ListModelCat>();


    private AQuery aq;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx=activity;
        act = activity;
        aq = new AQuery(ctx);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        this.baseCat = "9334-";

        view = inflater.inflate(R.layout.search_tabs, container, false);
        LinearLayout containerLayout = (LinearLayout) view.findViewById(R.id.search_tabs_layout);
        ScrollView scrollView = new ScrollView(ctx);
        scrollView.setId(R.id.cat_scroll);
        LinearLayout mainLayout = new LinearLayout(ctx);
        mainLayout.setId(R.id.cat_layout);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setMinimumHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        GridLayout selectedGrid = new GridLayout(ctx);
        selectedGrid.setId(R.id.selected_grid);
        selectedGrid.setBackgroundColor(getResources().getColor(R.color.Grey));
        selectedGrid.setColumnCount(1);
        selectedGrid.setRowCount(2);
        GridLayout.LayoutParams gridLayout = new GridLayout.LayoutParams();
        gridLayout.setGravity(Gravity.CENTER);
        gridLayout.setMargins(0, 100, 0, 100);
        gridLayout.width = GridLayout.LayoutParams.MATCH_PARENT;
        gridLayout.height = GridLayout.LayoutParams.WRAP_CONTENT;
        selectedGrid.setLayoutParams(gridLayout);

        TextView mainText = new TextView(ctx);
        ViewGroup.LayoutParams textParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mainText.setLayoutParams(textParams);
        mainText.setGravity(Gravity.CENTER);
        mainText.setId(R.id.main_text);
        mainText.setTextSize(20);
        mainText.setTypeface(null, Typeface.BOLD);

        TextView countText = new TextView(ctx);
        countText.setLayoutParams(textParams);
        countText.setId(R.id.count_text);
        countText.setTextSize(10);
        countText.setGravity(Gravity.CENTER);


        selectedGrid.addView(mainText);
        selectedGrid.addView(countText);

        mainLayout.addView(selectedGrid);

        scrollView.addView(mainLayout);
        containerLayout.addView(scrollView);

        asyncJsonCat();

        return view;
    }

    public void PopulateDisp()
    {

        searchVariables.setCategory(baseCat);
        List<Categories> categories = new Database(ctx).getCat(baseCat);
        Categories baseCatObj = new Database(ctx).getSpecCat(baseCat);


        for (int i = 1; i < categories.size(); i++)
        {
            String name = categories.get(i).getName();
            String path = categories.get(i).getNumber();
            int listNo = 0;
            for (int y = 0; y < searchCounts.size(); y++)
            {
                if (searchCounts.get(y).getCat().equals(path))
                    listNo = searchCounts.get(y).getCount();
            }



            ListModelCat lmc = new ListModelCat(name, listNo, path);
            listModelCat.add(lmc);
            Log.i("out", lmc.getName() + " " + lmc.getListings());
        }

        Resources res = getResources();

        list = new ListView(ctx);

        adapter = new CatAdapter(act, listModelCat, res );
        list.setAdapter(new CatAdapter(act, listModelCat, res));
        ViewGroup.LayoutParams listLay = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        list.setLayoutParams(listLay);
        TextView mainText = (TextView) view.findViewById(R.id.main_text);
        if (baseCatObj.getName().equals("All"))
            mainText.setText("Services");
        else
            mainText.setText(baseCatObj.getName());

        TextView countText = (TextView) view.findViewById(R.id.count_text);
        countText.setText(Integer.toString(searchCounts.get(0).getCount()) + " Listings");

        LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.cat_layout);

        mainLayout.addView(list);
    }



    public void asyncJsonCat(){
//        //Set url getting address from constants class
String url = new Constants().getBASE_ADDR() + "Search/General.json?category="+baseCat+"&expired=false";
            Log.i("out", url);
//        //Run AndroidQuery AJAX call running to jsonCallbackCat when it is completed
       aq.ajax(url, JSONObject.class, this, "jsonCallbackCat");
    }

    //Method called when cat async call is completed
    public void jsonCallbackCat(String url, JSONObject json, AjaxStatus status) throws JSONException
    {
        //Check to see if there was a JSON object returned
        if(json != null){
            //Process category data via DataProcess class passing in the recieved JSON and this
            searchCounts = new DataProcess().ProcessSearchCounts(json, ctx, baseCat);
            PopulateDisp();
        }else{
            //ajax error, show error code if there is no JSON
            Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
        }
    }

    public static class OnCatClick implements View.OnClickListener
    {
        private String cat;

        public OnCatClick(String cat){
            this.cat = cat;
        }

        @Override
        public void onClick(View view) {


        }
    }
}
