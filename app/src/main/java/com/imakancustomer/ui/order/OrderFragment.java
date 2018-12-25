package com.imakancustomer.ui.order;


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
import com.imakancustomer.model.BookingListPojo;
import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.ui.category_list.CategoryListAdapter;
import com.imakancustomer.ui.category_list.CategoryListContract;
import com.imakancustomer.ui.category_list.CategoryListFragment;
import com.imakancustomer.ui.category_list.CategoryListPresenter;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.utils.CustomerTextViewBold;
import com.imakancustomer.utils.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFragment extends Fragment implements OrderContract.View, OnItemSelectedListener {


    @BindView(R.id.rv_orders)
    RecyclerView rv_orders;
    @BindView(R.id.ll_loader)
    LinearLayout ll_loader;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;
    @BindView(R.id.tv_no_notification)
    TextView tv_no_notification;

    private View mView;
    private OrderFragment mOrderFragment;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<BookingListPojo.Datum> mOrderList = new ArrayList<>();
    private OrderAdapter mOrderAdapter;
    private OrderContract.Action mOrderPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_order, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_my_orders);
        ButterKnife.bind(this, mView);
        HomeActivity.hideShowLocationOption(false,"");
        mOrderPresenter = new OrderPresenter(this, getActivity());
        init();
        checkInternet();
        setHasOptionsMenu(true);
        return mView;
    }


    private void checkInternet() {
        if (Function.isNetworkConnected(Objects.requireNonNull(getActivity()))) {
            swipe_refresh_layout.setRefreshing(false);
            mOrderPresenter.getOrderlist();
        } else {
            Function.showMessage(getString(R.string.no_internet), getActivity());
        }
    }


    private void init() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_orders.setLayoutManager(mLayoutManager);
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
    public void setDataToAdapter(List<BookingListPojo.Datum> data) {
        if (data.size() > 0) {
            tv_no_notification.setVisibility(View.GONE);
            mOrderList = data;
            mOrderAdapter = new OrderAdapter(data, getActivity());
            mOrderAdapter.OnItemSelectedListener(this);
            rv_orders.setAdapter(mOrderAdapter);
            mOrderAdapter.notifyDataSetChanged();
        } else {
            tv_no_notification.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(int position, String type) {

    }
}
