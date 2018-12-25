package com.imakancustomer.ui.address;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnServiceItemSelectedListener;
import com.imakancustomer.model.AddressListPojo;
import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.ui.add_address.AddAddressFragment;
import com.imakancustomer.ui.category_list.CategoryListAdapter;
import com.imakancustomer.ui.category_list.CategoryListContract;
import com.imakancustomer.ui.category_list.CategoryListFragment;
import com.imakancustomer.ui.category_list.CategoryListPresenter;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.utils.Function;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddressFragment extends Fragment implements AddressContract.View, OnServiceItemSelectedListener {
    @BindView(R.id.rv_address_list)
    RecyclerView rv_address_list;
    @BindView(R.id.ll_loader)
    LinearLayout ll_loader;

    private View mView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<AddressListPojo.Datum> mAddressList = new ArrayList<>();
    private AddressAdapter mAddressAdapter;
    private AddressContract.Action mAddressPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_my_address);
        mView = inflater.inflate(R.layout.fragment_address, container, false);
        HomeActivity.hideShowLocationOption(false, "");
        ButterKnife.bind(this, mView);
        mAddressPresenter = new AddressPresenter(this, getActivity());
        init();
        checkInternet();
        setHasOptionsMenu(true);
        return mView;
    }

    private void checkInternet() {
        if (Function.isNetworkConnected(Objects.requireNonNull(getActivity()))) {
            mAddressPresenter.getAddressList();
        } else {
            Function.showMessage(getString(R.string.no_internet), getActivity());
        }
    }


    private void init() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_address_list.setLayoutManager(mLayoutManager);
    }


    @OnClick(R.id.tv_address_add)
    public void onAddAddressClicked() {
        replaceFragment(new AddAddressFragment());
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
    public void setDataToAdapter(List<AddressListPojo.Datum> data) {
        if (data.size() > 0) {
            mAddressList = data;
            mAddressAdapter = new AddressAdapter(data, getActivity());
            mAddressAdapter.OnServiceItemSelectedListener(this);
            rv_address_list.setAdapter(mAddressAdapter);
            mAddressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position, String type, String addressId) {
        if (type.equalsIgnoreCase("delete")) {
            mAddressPresenter.deleteAddress(addressId);
        } else if (type.equalsIgnoreCase("selected")) {
            JSONObject param = new JSONObject();
            try {
                param.put("addressId", addressId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mAddressPresenter.setPrimaryAddress(param.toString());
        }
    }
}
