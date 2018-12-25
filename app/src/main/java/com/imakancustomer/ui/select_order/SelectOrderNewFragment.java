package com.imakancustomer.ui.select_order;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemAddedListener;
import com.imakancustomer.model.BookingListPojo;
import com.imakancustomer.model.ServiceListPojo;
import com.imakancustomer.ui.basket.BasketFragment;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.utils.Function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectOrderNewFragment extends Fragment implements SelectOrderContract.View, OnItemAddedListener {


    @BindView(R.id.lvExp)
    ExpandableListView listView;
    @BindView(R.id.tlSliding)
    TabLayout tlSliding;

    private View mView;
    private SelectOrderNewFragment mSelectOrderNewFragment;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<BookingListPojo.Datum> mServiceList = new ArrayList<>();
    private Adapter_Expandable mExpandableAdapter;
    private SelectOrderContract.Action mSelectOrderPresenter;
    private ProgressDialog progress;


    ArrayList<ServiceListPojo> subCatKeysArraylist = new ArrayList<>();
    ArrayList<ArrayList<ServiceListPojo>> serviceslistitemsPojos = new ArrayList<>();
    ArrayList<ArrayList<ServiceListPojo>> selected_serviceslistitemsPojos = new ArrayList<>();
    ArrayList<ServiceListPojo> serviceslistitemArraylist = new ArrayList<>();
    ArrayList<ServiceListPojo> returnIntentArrayList = new ArrayList<>();
    public static ArrayList<ServiceListPojo> selectedServicesArrayList = new ArrayList<>();

    Adapter_Expandable listAdapter;
    List<String> listDataHeader = new ArrayList<>();
    OnItemAddedListener onItemAddedListener = this;
    Float total_amount;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_select_order_new, container, false);
        progress = new ProgressDialog(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_select_order);
        ButterKnife.bind(this, mView);
        HomeActivity.hideShowLocationOption(false, "");
        mSelectOrderPresenter = new SelectOrderPresenter(this, getActivity());
        checkInternet();
        return mView;
    }

    private void checkInternet() {
        if (Function.isNetworkConnected(Objects.requireNonNull(getActivity()))) {
            listView.setGroupIndicator(null);
            listView.setDividerHeight(0);
            mSelectOrderPresenter.getServiceList(getArguments().getString("providerId"), getArguments().getString("subCategoryIds"));
        } else {
            Function.showMessage(getString(R.string.no_internet), getActivity());
        }
    }


    @Override
    public void showLoader() {
        progress.setMessage(getString(R.string.str_please_wait));
        progress.setCancelable(false);
        progress.show();
    }


    @OnClick(R.id.previous)
    public void onPreviousClicked() {


    }

    @OnClick(R.id.previous)
    public void onNextClicked() {

    }

    @OnClick(R.id.btn_continue)
    public void onContinueClicked() {
        getItemList();
        getSelectedItemList();

        if (selectedServicesArrayList.size() > 0) {
            Fragment fragment = new BasketFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("catSubcatArraylist", selectedServicesArrayList);
            bundle.putFloat("total", total_amount);
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else {
            showMessage(getString(R.string.str_valid_select_services));
        }
    }

    private void getItemList() {
        selectedServicesArrayList.clear();
        total_amount = (float) 0;
        Log.d("TEST=", "" + serviceslistitemsPojos.size());
        for (int i = 0; i < serviceslistitemsPojos.size(); i++) {

            for (int j = 0; j < serviceslistitemsPojos.get(i).size(); j++) {
                if (serviceslistitemsPojos.get(i).get(j).getCount() > 0) {
                    selectedServicesArrayList.add(serviceslistitemsPojos.get(i).get(j));
                    total_amount = total_amount + (serviceslistitemsPojos.get(i).get(j).getCount() * Integer.parseInt(serviceslistitemsPojos.get(i).get(j).getPrice()));
                }
            }
        }
    }


    @Override
    public void hideLoader() {
        progress.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void apiResponse(JSONArray res) {
        parseResult(res);

        for (int i = 0; i < listDataHeader.size(); i++) {
            tlSliding.addTab(tlSliding.newTab().setText(listDataHeader.get(i)));
            tlSliding.addTab(tlSliding.newTab().setText(listDataHeader.get(i)));
            tlSliding.addTab(tlSliding.newTab().setText(listDataHeader.get(i)));
        }

        listAdapter = new Adapter_Expandable(getActivity(), listDataHeader, serviceslistitemsPojos, onItemAddedListener);
        listView.setAdapter(listAdapter);
    }


    @Override
    public void onItemAdded(String totalamount, ArrayList<ServiceListPojo> serviceListPojos) {
  /*      this.total_amount = Float.valueOf(totalamount);
        Boolean isFound = false;
        for (int i = 0; i < selectedServicesArrayList.size(); i++) {
            if (selectedServicesArrayList.get(i).getId().equals(serviceListPojos.get(pos).getId())) {
                selectedServicesArrayList.get(i).setCount(serviceListPojos.get(0).getCount());
                isFound = true;
            }
        }

        if (!isFound) {
            selectedServicesArrayList.add(serviceListPojos);
            isFound = false;
        }*/
    }

    private void getSelectedItemList() {
        selected_serviceslistitemsPojos.clear();
        Boolean aBoolean = false;
        selected_serviceslistitemsPojos.add(initialEmptyService());
        int k = 0;
        for (int i = 0; i < serviceslistitemsPojos.size(); i++) {

            for (int j = 0; j < serviceslistitemsPojos.get(i).size(); j++) {
                if ((serviceslistitemsPojos.get(i).get(j).getCount() > 0)) {

                    selected_serviceslistitemsPojos.get(k).add(serviceslistitemsPojos.get(i).get(j));
                    aBoolean = true;
                }
            }
            if (aBoolean) {
                selected_serviceslistitemsPojos.get(k).remove(0);
                k = k + 1;
                selected_serviceslistitemsPojos.add(initialEmptyService());
            }
            aBoolean = false;
        }
        selected_serviceslistitemsPojos.remove(selected_serviceslistitemsPojos.size() - 1);
    }

    /**
     * Method used to init the pojo
     * @return
     */
    private ArrayList<ServiceListPojo> initialEmptyService() {
        ArrayList<ServiceListPojo> list_local = new ArrayList<>(1);
        list_local.add(new ServiceListPojo("", "", "", "", "", "", "", "", ""));
        return list_local;
    }

    @Override
    public void onItemAdded(String totalamount, ServiceListPojo serviceListPojo) {
        this.total_amount = Float.valueOf(totalamount);
        Boolean isFound = false;
        for (int i = 0; i < selectedServicesArrayList.size(); i++) {
            if (selectedServicesArrayList.get(i).getId().equals(serviceListPojo.getId())) {
                selectedServicesArrayList.get(i).setCount(serviceListPojo.getCount());
                isFound = true;
            }
        }

        if (!isFound) {
            selectedServicesArrayList.add(serviceListPojo);
            isFound = false;
        }

    }


    /**
     * Method used to parse dynamic json data
     *
     * @param dataArr hold the dynamic json
     */
    private void parseResult(JSONArray dataArr) {
        try {
            Gson gson = new GsonBuilder().create();
            //TypedByteArray typedByteArray = (TypedByteArray) data;
            if (dataArr != null) {
                //String json = data;
                //JSONObject jsonObj = new JSONObject(json);

                //JSONArray dataArr = jsonObj.getJSONArray("data");
                JSONObject mainDataObj = dataArr.getJSONObject(0);

                Iterator<?> keys = mainDataObj.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (mainDataObj.get(key) instanceof JSONArray) {
                        Log.d("Key---", key); // do whatever you want with it
                        JSONArray jsonArray = (JSONArray) mainDataObj.get(key);
                        listDataHeader.add(key);//adding key's
                        if (jsonArray != null) {
                            Type listType = new TypeToken<List<ServiceListPojo>>() {
                            }.getType();
                            subCatKeysArraylist = gson.fromJson(String.valueOf(jsonArray), listType);
                            Log.d("id---", subCatKeysArraylist.get(0).getSub_cat_name() + "        " + subCatKeysArraylist.size());
                            serviceslistitemsPojos.add(subCatKeysArraylist);
                        }
                    }
//                    subCatKeysArraylist.clear();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
