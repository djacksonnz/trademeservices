package com.trademeservices.app.act.search_tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trademeservices.app.R;

/**
 * Created by jacksdl2 on 8/09/2014.
 */
public class LocationTab extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.search_tabs, container, false);
        //TextView textview = (TextView) view.findViewById(R.id.tabtextview);
        //textview.setText("Location go here");
        return view;
    }
}
