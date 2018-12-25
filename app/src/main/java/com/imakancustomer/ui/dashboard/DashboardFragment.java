package com.imakancustomer.ui.dashboard;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemSelectedListener;
import com.imakancustomer.model.DashboardPojo;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.ui.order.OrderFragment;
import com.imakancustomer.utils.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment implements DashboardContract.View, OnItemSelectedListener {

    @BindView(R.id.tv_dashboard_total_booking)
    TextView tv_dashboard_total_booking;
    @BindView(R.id.tv_dashboard_total_fav)
    TextView tv_dashboard_total_fav;
    @BindView(R.id.rv_recent_booking)
    RecyclerView rv_recent_booking;
    @BindView(R.id.ll_loader)
    LinearLayout ll_loader;

    private View mView;
    private OrderFragment mDashboardFragment;
    private RecyclerView.LayoutManager mLayoutManager;
    //private List<DashboardPojo> mDashboardList = new ArrayList<>();
    private DashboardAdapter mDashboardAdapter;
    private DashboardContract.Action mDashboardPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_dashboard);
        ButterKnife.bind(this, mView);
        HomeActivity.hideShowLocationOption(false,"");
        mDashboardPresenter = new DashboardPresenter(this, getActivity());
        init();
        checkInternet();
        setHasOptionsMenu(true);
        return mView;
    }


    private void checkInternet() {
        if (Function.isNetworkConnected(Objects.requireNonNull(getActivity()))) {
            mDashboardPresenter.getDashboardlist();
        } else {
            Function.showMessage(getString(R.string.no_internet), getActivity());
        }
    }


    private void init() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_recent_booking.setLayoutManager(mLayoutManager);
    }


    @Override
    public void showLoader() {
        ll_loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        ll_loader.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setDataToAdapter(DashboardPojo data) {
        try {
            Log.d("TEST", "" + data.getData().get(0).getCompleteBookings().get(0).getCompleteRequest());
            tv_dashboard_total_booking.setText("" + data.getData().get(0).getCompleteBookings().get(0).getCompleteRequest());
            tv_dashboard_total_fav.setText("" + data.getData().get(0).getTotalFavourites().get(0).getTotalFavouriteCount());
            if (data.getData().size() > 0) {
                //mDashboardList = data;
                mDashboardAdapter = new DashboardAdapter(data.getData().get(0).getRecentBooking(), getActivity());
                mDashboardAdapter.OnItemSelectedListener(this);
                rv_recent_booking.setAdapter(mDashboardAdapter);
                mDashboardAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position, String type) {

    }
}
