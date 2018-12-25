package com.imakancustomer.ui.basket;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemSelectedListener;
import com.imakancustomer.model.AddressListPojo;
import com.imakancustomer.model.ServiceListPojo;
import com.imakancustomer.ui.address.AddressAdapter;
import com.imakancustomer.ui.address.AddressContract;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.utils.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BasketFragment extends Fragment implements BasketContract.View, OnItemSelectedListener {

    @BindView(R.id.tv_restaurant_name)
    TextView tv_restaurant_name;
    @BindView(R.id.tv_restaurant_location)
    TextView tv_restaurant_location;
    @BindView(R.id.tv_delivery_address)
    TextView tv_delivery_address;
    @BindView(R.id.tv_contact_number)
    TextView tv_contact_number;
    @BindView(R.id.et_personal_msg)
    EditText et_personal_msg;
    @BindView(R.id.tv_basket_total_amount)
    TextView tv_basket_total_amount;


    @BindView(R.id.rv_item_list)
    RecyclerView rv_item_list;

    private View mView;
    private LinearLayoutManager linearLayoutManager;
    private Float mTotalPrice;
    private ArrayList<ServiceListPojo> servicesArrayList = new ArrayList<>();
    private BasketAdapter mBasketAdapter;
    private BasketContract.Action mBasketPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_basket, container, false);
        ButterKnife.bind(this, mView);
        HomeActivity.hideShowLocationOption(false, "");
        mBasketPresenter = new BasketPresenter(this, getActivity());
        init();
        checkInternet();
        getBundleData();
        return mView;
    }

    private void checkInternet() {
        if (Function.isNetworkConnected(Objects.requireNonNull(getActivity()))) {
            getBundleData();
            mBasketPresenter.getPrimaryAddress();
        } else {
            Function.showMessage(getString(R.string.no_internet), getActivity());
        }

    }


    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_item_list.setLayoutManager(linearLayoutManager);
    }


    @OnClick(R.id.btn_basket_checkout)
    public void onCheckoutBtnClicked() {

    }


    private void getBundleData() {
        if (getArguments() != null) {
            mTotalPrice = getArguments().getFloat("total");
            servicesArrayList = (ArrayList<ServiceListPojo>) getArguments().getSerializable("catSubcatArraylist");
            tv_basket_total_amount.setText("RM " + getArguments().getFloat("total"));
            setAdapter();
        }
    }

    private void setAdapter() {
        if (servicesArrayList.size() > 0) {
            mBasketAdapter = new BasketAdapter(getActivity(), servicesArrayList);
            mBasketAdapter.OnItemSelectedListener(this);
            rv_item_list.setAdapter(mBasketAdapter);
            mBasketAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position, String type) {

        if (type.equalsIgnoreCase("DELETE")) {
            showMessage(servicesArrayList.get(position).getPrice());
            Float itemPrice = Float.parseFloat(servicesArrayList.get(position).getPrice()) * servicesArrayList.get(position).getCount();
            mTotalPrice = mTotalPrice - itemPrice;
            tv_basket_total_amount.setText("RM " + mTotalPrice);
        }

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setDataToAdapter(List<AddressListPojo.Datum> data) {
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (!data.get(i).getStatus().toString().equalsIgnoreCase("1")) {
                    //primary address
                    tv_delivery_address.setText(data.get(0).getAddress());
                    tv_contact_number.setText(data.get(0).getContact());
                }
            }
        }
    }
}
