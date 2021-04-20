package com.skdirect.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.skdirect.fragment.AllOrderFragment;

import java.util.ArrayList;

public class DynamicTabAdapter extends FragmentPagerAdapter {
    private ArrayList<String> statusList;
    public DynamicTabAdapter(FragmentManager fm, int behavior, ArrayList<String> statusList) {
        super(fm, behavior);
        this.statusList = statusList;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new AllOrderFragment(statusList.get(position));
    }
    @Override
    public int getCount() {
        return statusList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return statusList.get(position);
    }
}
