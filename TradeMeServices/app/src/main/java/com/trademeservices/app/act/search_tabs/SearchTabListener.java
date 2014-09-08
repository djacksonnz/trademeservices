package com.trademeservices.app.act.search_tabs;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.trademeservices.app.R;

public class SearchTabListener implements ActionBar.TabListener {
    Fragment fragment;

    public SearchTabListener(Fragment fragment) {
        this.fragment = fragment;
    }

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(R.id.fragment_container, fragment);
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // nothing done here
    }
}