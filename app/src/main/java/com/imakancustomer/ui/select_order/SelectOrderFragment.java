package com.imakancustomer.ui.select_order;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imakancustomer.R;

import java.util.ArrayList;

import butterknife.BindView;

public class SelectOrderFragment extends Fragment {

    private View mView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mVpViewpager;
    TabsViewPagerAdapter adapter;
    String TAG = "SelectServicesActivity";
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;
    private String LOG_TAG = "SelectServicesActivity";
    //ArrayList<DataPojo> categoryServiceList = new ArrayList<>();
    int i;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_select_order, container, false);
        return mView;
    }


    void setDataInViewObjects() {
        mLlMain.setVisibility(View.VISIBLE);
        setupViewPager();
        mTabLayout.setupWithViewPager(mVpViewpager);
        mVpViewpager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mVpViewpager.setCurrentItem(0);
            }
        }, 100);
        setCustomFont();
    }

    private void setupViewPager() {
        adapter = new TabsViewPagerAdapter(getChildFragmentManager());

//        for (int i = 0; i < categoryServiceList.size(); i++) {
//            Bundle bundle = new Bundle();
//            bundle.putString("subcat_id", categoryServiceList.get(i).getId());
//            adapter.addFragment(new SelectItemsFragment(), categoryServiceList.get(i).getName(), 1, bundle);
//        }

        mVpViewpager.setAdapter(adapter);
        mVpViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
                int filter = 0;
                switch (position) {
                    case 0:
                        filter = 0;
                        break;
                    case 1:
                        filter = 1;
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void setCustomFont() {
        ViewGroup vg = (ViewGroup) mTabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    /*((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Muli-Light_1.ttf"));
                    ((TextView) tabViewChild).setAllCaps(false);*/
                }
            }
        }
    }

}
