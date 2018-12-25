package com.imakancustomer.ui.add_address;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.hbb20.CountryCodePicker;
import com.imakancustomer.R;
import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.ui.signup_map.SignUpMapActivity;
import com.imakancustomer.utils.GPSTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAddressFragment extends Fragment implements AddAddressContract.View, OnMapReadyCallback {

    @BindView(R.id.et_address_full)
    EditText et_address_full;
    @BindView(R.id.et_address_city)
    EditText et_address_city;
    @BindView(R.id.et_address_postalcode)
    EditText et_address_postalcode;
    @BindView(R.id.et_address_name)
    EditText et_address_name;
    @BindView(R.id.et_address_phonenumber)
    EditText et_address_phonenumber;
    @BindView(R.id.ccp_address_countrycode)
    CountryCodePicker ccp_address_countrycode;
    @BindView(R.id.iv_location_icon)
    ImageView iv_location_icon;
    @BindView(R.id.sv_main)
    ScrollView sv_main;


    private GoogleMap mGoogleMap;
    private Double mLatitutde, mLongitude;
    private GPSTracker mGPSTracker;
    private Geocoder mGeocoder;
    private List<Address> mAddresses;
    private String mFullAddress, mName, mCity, mPhoneNumber, mState, mCountry, mPostalCode, lat, lng, mOutput;
    private View mView;
    private AddAddressFragment mAddAddressFragment;
    private RecyclerView.LayoutManager mLayoutManager;
    private AddAddressContract.Action mAddAddressPresenter;
    private ProgressDialog mProgress;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_address, container, false);
        ButterKnife.bind(this, mView);
        HomeActivity.hideShowLocationOption(false,"");
        mAddAddressPresenter = new AddAddressPresenter(this, getActivity());
        mProgress = new ProgressDialog(getActivity());
        mGPSTracker = new GPSTracker(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //CODE TO SOLVE THE PROBLEM OF VERTICALLY SCROLLING OF GOOGLE MAP
        iv_location_icon.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow ScrollView to intercept touch events.
                    sv_main.requestDisallowInterceptTouchEvent(true);
                    // Disable touch on transparent view
                    return false;

                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events.
                    sv_main.requestDisallowInterceptTouchEvent(false);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    sv_main.requestDisallowInterceptTouchEvent(true);
                    return false;

                default:
                    return true;
            }
        });
        return mView;
    }


    @OnClick(R.id.btn_address_save)
    public void onSaveBtnClicked() {
        mFullAddress = et_address_full.getText().toString().trim();
        mCity = et_address_city.getText().toString().trim();
        mPostalCode = et_address_postalcode.getText().toString().trim();
        mName = et_address_name.getText().toString().trim();
        mPhoneNumber = et_address_phonenumber.getText().toString().trim();

        if (validation()) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", mName);
                jsonObject.put("city", mCity);
                jsonObject.put("state", mState);
                jsonObject.put("country", mCountry);
                jsonObject.put("address", mFullAddress);
                jsonObject.put("pincode", mPostalCode);
                jsonObject.put("latitude", mLatitutde.toString());
                jsonObject.put("longitude", mLongitude.toString());
                jsonObject.put("country_code", ccp_address_countrycode.getSelectedCountryCode());
                jsonObject.put("contact", mPhoneNumber);
                jsonObject.put("type", "0");
                mAddAddressPresenter.addAddress(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to check validation of fields
     *
     * @return true/false
     */
    private boolean validation() {
        if (mName.isEmpty()) {
            showMessage(getString(R.string.valid__name));
            return false;
        } else if (mPhoneNumber.isEmpty()) {
            showMessage(getString(R.string.valid_phone_number));
            return false;
        } else if (mFullAddress.isEmpty()) {
            showMessage(getString(R.string.valid_address));
            return false;
        } else if (mCity.isEmpty()) {
            showMessage(getString(R.string.valid_city));
            return false;
        } else if (mPostalCode.isEmpty()) {
            showMessage(getString(R.string.valid_postal_code));
            return false;
        }
        return true;
    }


    @Override
    public void showLoader() {
        mProgress.setMessage(getString(R.string.str_please_wait));
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    public void hideLoader() {
        mProgress.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        assert getFragmentManager() != null;
        getFragmentManager().popBackStack();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mGPSTracker.getLocation();
        mLatitutde = mGPSTracker.getLatitude();
        mLongitude = mGPSTracker.getLongitude();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitutde, mLongitude), 15);
        googleMap.animateCamera(location);
        camerachange();
    }

    private void camerachange() {
        mGoogleMap.setOnCameraChangeListener(cameraPosition -> {
            new LongOperation().execute(String.valueOf(cameraPosition.target.latitude), String.valueOf(cameraPosition.target.longitude));
        });
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
                        mCountry = mAddresses.get(0).getCountryName();
                        mState = mAddresses.get(0).getAdminArea();
                        mCity = mAddresses.get(0).getSubAdminArea();
                        mPostalCode = mAddresses.get(0).getPostalCode();
                        mLatitutde = mAddresses.get(0).getLatitude();
                        mLongitude = mAddresses.get(0).getLongitude();
                    } else {
                        Toast.makeText(getActivity(), "Unable to fetch address please enter manually", Toast.LENGTH_SHORT).show();
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
                et_address_full.setText(mOutput);
                et_address_city.setText(mCity);
                et_address_postalcode.setText(mPostalCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
