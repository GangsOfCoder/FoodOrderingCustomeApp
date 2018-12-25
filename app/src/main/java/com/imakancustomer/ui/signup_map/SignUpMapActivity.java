package com.imakancustomer.ui.signup_map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.imakancustomer.R;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.ui.login.LoginActivity;
import com.imakancustomer.utils.GPSTracker;
import com.imakancustomer.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpMapActivity extends FragmentActivity implements OnMapReadyCallback, SignUpMapContract.View {

    //VIEWS
    @BindView(R.id.et_signup_address)
    EditText et_signup_address;
    @BindView(R.id.et_signup_city)
    EditText et_signup_city;
    @BindView(R.id.et_signup_postalcode)
    EditText et_signup_postalcode;
    @BindView(R.id.iv_location_icon)
    ImageView iv_location_icon;
    @BindView(R.id.sv_main)
    ScrollView sv_main;

    //VARIABLES
    private GoogleMap mGoogleMap;
    private Double mLatitutde, mLongitude;
    private GPSTracker mGPSTracker;
    private Geocoder mGeocoder;
    private List<Address> mAddresses;
    private String mState, mCity, mPincode, mOutput, mCountry;
    private ProgressDialog mProgress;
    private SignUpMapContract.Action mSignUpMapPresenter;
    private SharedPref mSharedPref;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_map);
        ButterKnife.bind(this);
        mProgress = new ProgressDialog(this);
        mSharedPref = new SharedPref(this);
        mGPSTracker = new GPSTracker(SignUpMapActivity.this);
        mSignUpMapPresenter = new SignUpMapPresenter(this, this);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
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

    }

    @OnClick(R.id.img_view_back)
    public void goBack() {
        finish();
    }

    @OnClick(R.id.btn_signupmap)
    public void onSignUpClicked() {
        if (mSignUpMapPresenter.validation(et_signup_address.getText().toString().trim(), et_signup_city.getText().toString().trim(), et_signup_postalcode.getText().toString().trim())) {
            JSONObject param = new JSONObject();
            try {
                param.put("first_name", getIntent().getStringExtra("mFirstName"));
                param.put("last_name", getIntent().getStringExtra("mLastName"));
                param.put("contact", getIntent().getStringExtra("mNumber"));
                param.put("email", getIntent().getStringExtra("mEmail"));
                param.put("country_code", getIntent().getStringExtra("mCountryCode"));
                param.put("password", getIntent().getStringExtra("mPassword"));
                param.put("lat", mLatitutde.toString());
                param.put("lng", mLongitude.toString());
                param.put("country", mCountry);
                param.put("address", et_signup_address.getText().toString().trim());
                param.put("city", et_signup_city.getText().toString().trim());
                param.put("pincode", et_signup_postalcode.getText().toString().trim());
                param.put("userType", "CUSTOMER");
                param.put("device_type", "ANDROID");
                param.put("device_token", mSharedPref.getDeviceToken());
                mSignUpMapPresenter.doRegister(param.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @OnClick(R.id.tv_sign_here)
    public void onSignHereClicked() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
                mGeocoder = new Geocoder(SignUpMapActivity.this, Locale.getDefault());
                mAddresses = mGeocoder.getFromLocation(Double.valueOf(params[0]), Double.valueOf(params[1]), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                runOnUiThread(() -> {
                    if (mAddresses.size() > 0) {
                        mOutput = mAddresses.get(0).getAddressLine(0);
                        mCountry = mAddresses.get(0).getCountryName();
                        mState = mAddresses.get(0).getAdminArea();
                        mCity = mAddresses.get(0).getSubAdminArea();
                        mPincode = mAddresses.get(0).getPostalCode();
                        mLatitutde = mAddresses.get(0).getLatitude();
                        mLongitude = mAddresses.get(0).getLongitude();

                    } else {
                        Toast.makeText(SignUpMapActivity.this, "Unable to fetch address please enter manually", Toast.LENGTH_SHORT).show();
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
                et_signup_address.setText(mOutput);
                et_signup_city.setText(mCity);
                et_signup_postalcode.setText(mPincode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showLoader() {
        mProgress.setMessage(getString(R.string.loader_please_wait));
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    public void hideLoader() {
        mProgress.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String type) {
        if (type.equalsIgnoreCase("login")) {
            final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
            dialog.setContentView(R.layout.custome_dialoge);
            TextView text = dialog.findViewById(R.id.text);
            text.setText(R.string.str_verify_email);
            Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
            dialogButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
            dialog.show();
        }
    }
}
