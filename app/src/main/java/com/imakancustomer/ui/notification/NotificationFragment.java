package com.imakancustomer.ui.notification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemSelectedListener;
import com.imakancustomer.model.NotificationPojo;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.utils.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationFragment extends Fragment implements NotificationContract.View, OnItemSelectedListener {

    //VIEWS
    @BindView(R.id.rv_notification)
    RecyclerView rv_notification;
    @BindView(R.id.ll_loader)
    LinearLayout ll_loader;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;
    @BindView(R.id.tv_no_notification)
    TextView tv_no_notification;

    private View mView;

    private NotificationFragment mNotificationFragment;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<NotificationPojo.Datum> mNotificationList = new ArrayList<>();
    private NotificationAdapter mNotificationAdapter;
    private NotificationContract.Action mNotificationPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_notification, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_notifications);
        ButterKnife.bind(this, mView);
        HomeActivity.hideShowLocationOption(false,"");
        mNotificationPresenter = new NotificationPresenter(this, getActivity());
        init();
        checkInternet();
        setHasOptionsMenu(true);
        return mView;
    }

    private void init() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_notification.setLayoutManager(mLayoutManager);
    }

    private void checkInternet() {
        if (Function.isNetworkConnected(Objects.requireNonNull(getActivity()))) {
            swipe_refresh_layout.setRefreshing(false);
            mNotificationPresenter.getNotitficationList();
        } else {
            Function.showMessage(getString(R.string.no_internet), getActivity());
        }
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

    @Override
    public void setDataToAdapter(List<NotificationPojo.Datum> data) {
        if (data.size() > 0) {
            tv_no_notification.setVisibility(View.GONE);
            mNotificationList = data;
            mNotificationAdapter = new NotificationAdapter(data, getActivity());
            mNotificationAdapter.OnItemSelectedListener(this);
            rv_notification.setAdapter(mNotificationAdapter);
            mNotificationAdapter.notifyDataSetChanged();
        } else {
            tv_no_notification.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemClick(int position, String type) {

    }
}
