package com.imakancustomer.ui.provider;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemSelectedListener;
import com.imakancustomer.model.ProviderListPojo;
import com.imakancustomer.ui.category_list.CategoryListAdapter;
import com.imakancustomer.ui.category_list.CategoryListPresenter;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.ui.restaurant_details.RestaurantDetailsFragment;
import com.imakancustomer.utils.Function;
import com.imakancustomer.utils.GPSTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderListFragment extends Fragment implements ProviderListContract.View, OnMapReadyCallback, OnItemSelectedListener {


    //VIEWS
    @BindView(R.id.ll_loader)
    LinearLayout ll_loader;
    //GOOGLE MAP
    @BindView(R.id.layout_map)
    RelativeLayout layout_map;
    //PROVIDER LIST
    @BindView(R.id.layout_listing)
    RelativeLayout layout_listing;
    @BindView(R.id.no_provider)
    TextView no_provider;
    @BindView(R.id.rv_provider_list)
    RecyclerView rv_provider_list;
    @BindView(R.id.ll_bottom_list_view)
    LinearLayout ll_bottom_list_view;


    private GoogleMap mGoogleMap;
    private Double mlatitutde, mLongitude;
    private GPSTracker mGPSTracker;
    private Geocoder mGeocoder;
    private List<Address> mAddresses;
    private String mState, mCity, mPincode, mOutput, mCountry, mLat, mLng;

    private View mView;
    private ProviderListFragment mProviderListFragment;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ProviderListPojo.Datum> mProviderList = new ArrayList<>();
    private ProviderListAdapter mProviderListAdapter;
    private ProviderListContract.Action mProviderListPresenter;
    private boolean is_map;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_provider_list, container, false);
        ButterKnife.bind(this, mView);
        mProviderListPresenter = new ProviderListPresenter(this, getActivity());

        init();
        checkInternet();
        setHasOptionsMenu(true);
        return mView;
    }

    private void checkInternet() {
        if (Function.isNetworkConnected(Objects.requireNonNull(getActivity()))) {
            mGPSTracker = new GPSTracker(getContext());
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            /*mlatitutde = mGPSTracker.getLatitude();
            mLongitude = mGPSTracker.getLongitude();*/

            mlatitutde = 30.727524;
            mLongitude = 76.846195;

            mProviderListPresenter.getProviderList(mlatitutde, mLongitude, getArguments().getString("categoryId"));
        } else {
            Function.showMessage(getString(R.string.no_internet), getActivity());
        }
    }

    private void init() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_provider_list.setLayoutManager(mLayoutManager);
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
    public void setDataToAdapter(List<ProviderListPojo.Datum> data) {
        if (data.size() > 0) {
            mProviderList = data;
            mProviderListAdapter = new ProviderListAdapter(data, getActivity());
            mProviderListAdapter.OnItemSelectedListener(this);
            rv_provider_list.setAdapter(mProviderListAdapter);
            mProviderListAdapter.notifyDataSetChanged();
            addmarker(data);
        } else {

        }
    }

    @OnClick(R.id.ll_bottom_list_view)
    public void onListClicked() {
        if (is_map) {
            layout_map.setVisibility(View.VISIBLE);
            layout_listing.setVisibility(View.GONE);

            is_map = false;
        } else {
            HomeActivity.hideShowLocationOption(false, "");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Search Results");
            layout_map.setVisibility(View.GONE);
            layout_listing.setVisibility(View.VISIBLE);
            ll_bottom_list_view.setVisibility(View.GONE);
            is_map = true;

            if (mProviderList != null) {
                if (mProviderList.size() > 0) {
                    no_provider.setVisibility(View.GONE);
                    rv_provider_list.setVisibility(View.VISIBLE);
                } else {
                    no_provider.setVisibility(View.VISIBLE);
                    rv_provider_list.setVisibility(View.GONE);
                }
            } else {
                rv_provider_list.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap mGoogleMap) {
        this.mGoogleMap = mGoogleMap;
        mGPSTracker.getLocation();

/*        mlatitutde = mGPSTracker.getLatitude();
        mLongitude = mGPSTracker.getLongitude();*/

        mlatitutde = 30.727524;
        mLongitude = 76.846195;
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(new LatLng(mlatitutde, mLongitude), 15);
        mGoogleMap.animateCamera(location);

        Marker mMarker = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(mlatitutde, mLongitude))
                .title("SubWay"));
        mMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dot));


        mGoogleMap.addCircle(new CircleOptions()
                .center(new LatLng(mlatitutde, mLongitude))
                .radius(20)
                .strokeWidth(0)
                .strokeColor(Color.parseColor("#2271cce7"))
                .fillColor(Color.parseColor("#2271cce7")));

        moveMap(mlatitutde, mLongitude);
        new LongOperation().execute(String.valueOf(mlatitutde), String.valueOf(mLongitude));
    }

    private void moveMap(Double mlatitude, Double mLongitude) {
        //hit get provider api's here
    }

    private void addmarker(List<ProviderListPojo.Datum> arrayList_marker) {
        //mGoogleMap.clear();
        ArrayList<Object> arraylist_marker = new ArrayList<>();
        for (int i = 0; i < arrayList_marker.size(); i++) {
            Marker m1 = mGoogleMap.addMarker(new MarkerOptions()
                    .zIndex(i)
                    .position(new LatLng(arrayList_marker.get(i).getLat(), arrayList_marker.get(i).getLng()))
                    .title(arrayList_marker.get(i).getFirstName() + " " + arrayList_marker.get(i).getFirstName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
            arraylist_marker.add(m1);
        }
        if (arrayList_marker.size() > 0) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arrayList_marker.get(0).getLat(), arrayList_marker.get(0).getLng()), 18));
        } else {
            Toast.makeText(getActivity(), "Sorry, We don't Serve in your location. We're redirecting you to a default location!!", Toast.LENGTH_SHORT).show();
         /*   lat = 39.74362;
            lng = -8.80705;
            movemap(lat, lng);*/
        }
        mapinformarker(arrayList_marker, arraylist_marker);
    }

    public void mapinformarker(final List<ProviderListPojo.Datum> arrayList_markeras, ArrayList<Object> arraylist_marker) {

        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public View getInfoContents(Marker arg0) {
                int pos = (int) arg0.getZIndex();
                View v = getLayoutInflater().inflate(R.layout.custom_infowindow, null);
                TextView custom_txt = v.findViewById(R.id.custom_txt);
                RatingBar ratingBar_custom = v.findViewById(R.id.ratingBar_custom);
                if (arrayList_markeras.size() > 0) {
                    custom_txt.setText(arrayList_markeras.get(pos).getFirstName() + " " + arrayList_markeras.get(pos).getLastName());
                    ratingBar_custom.setRating(Float.valueOf(arrayList_markeras.get(pos).getAvgRating()));
                }
                mGoogleMap.setOnInfoWindowClickListener(marker -> {
                    if (Function.isConnectedInternet(getActivity())) {
                        //replacefragment(new BusinessDescActivity(), arrayList_markeras.get(pos).getUserId(), true);
                        showMessage("CLICKED ME");
                    } else {
                        Function.customadialog(getActivity(), getActivity().getString(R.string.check_internet));
                    }

                });
                return v;
            }
        });
    }

    @Override
    public void onItemClick(int position, String type) {
        Fragment fragment = new RestaurantDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", mProviderList.get(position).getUserId().toString());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @SuppressLint("StaticFieldLeak")
    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                mGeocoder = new Geocoder(getActivity(), Locale.getDefault());
                mAddresses = mGeocoder.getFromLocation(Double.valueOf(params[0]), Double.valueOf(params[1]), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                getActivity().runOnUiThread(() -> {
                    if (mAddresses.size() > 0) {
                        mOutput = mAddresses.get(0).getAddressLine(0);
                    } else {
                        //Toast.makeText(getActivity(), "Unable to fetch address please enter manually", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mOutput;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                HomeActivity.hideShowLocationOption(true, mOutput);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
