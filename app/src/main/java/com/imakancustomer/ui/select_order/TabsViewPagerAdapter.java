package com.imakancustomer.ui.select_order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

    //View Pager
public class TabsViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    TabsViewPagerAdapter(android.support.v4.app.FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title, int i, Bundle userProfileDetails) {
        Bundle bundle = new Bundle();

        if (userProfileDetails != null) {
            bundle.putAll(userProfileDetails);

        }
        bundle.putInt("type", i);
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}