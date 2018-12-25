package com.imakancustomer.ui.profile;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.ui.signup_map.SignUpMapActivity;
import com.imakancustomer.ui.signup_map.SignUpMapPresenter;
import com.imakancustomer.utils.CircleTransform;
import com.imakancustomer.utils.Function;
import com.imakancustomer.utils.GPSTracker;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileFragment extends Fragment implements ProfileContract.View, OnMapReadyCallback {


    @BindView(R.id.et_profile_name)
    EditText et_profile_name;
    @BindView(R.id.et_profile_number)
    EditText et_profile_number;
    @BindView(R.id.et_profile_email)
    EditText et_profile_email;
    @BindView(R.id.et_profile_address)
    EditText et_profile_address;

    @BindView(R.id.et_profile_postalcode)
    EditText et_profile_postalcode;
    @BindView(R.id.et_profile_city)
    EditText et_profile_city;
    @BindView(R.id.et_profile_registerdate)
    EditText et_profile_registerdate;
    @BindView(R.id.img_profile)
    ImageView img_profile;
    @BindView(R.id.btn_profile_update)
    AppCompatButton btn_profile_update;

    @BindView(R.id.ccp_profile_countrycode)
    CountryCodePicker ccp_profile_countrycode;


    @BindView(R.id.iv_location_icon)
    ImageView iv_location_icon;

    private View mView;
    private int year, month, day;
    private ProfileContract.Action mProfilePresenter;
    private ProgressDialog progress;
    private String gender;
    public final int PERMISSION_REQUEST_CODE_CG = 4;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private File user_dp;

    //VARIABLES
    private GoogleMap mGoogleMap;
    private Double mLatitutde, mLongitude;
    private GPSTracker mGPSTracker;
    private Geocoder mGeocoder;
    private List<Address> mAddresses;
    private String mState, mCity, mPincode, mOutput, mCountry, mLat, mLng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);
        progress = new ProgressDialog(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_profile);
        ButterKnife.bind(this, mView);
        HomeActivity.hideShowLocationOption(false,"");
        mProfilePresenter = new ProfilePresenter(this, getActivity());
        mGPSTracker = new GPSTracker(getActivity());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        checkInternet();
        disableViews();
        return mView;
    }

    private void checkInternet() {
        if (Function.isConnectedInternet(getActivity())) {
            mProfilePresenter.getProfileDetails();
        } else {
            Function.showMessage(getString(R.string.no_internet), getActivity());
        }
    }

    @OnClick(R.id.btn_profile_update)
    public void onUpdateProfileClicked() {
        //if (validation()) {
        showAlertDialog();
        //}
    }


    @OnClick(R.id.img_profile)
    public void getImage() {
        dialogSelectImage();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.edit).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit) {
            enableViews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enableViews() {
        btn_profile_update.setVisibility(View.VISIBLE);
        et_profile_name.setEnabled(true);
        ccp_profile_countrycode.setEnabled(true);
        et_profile_email.setEnabled(true);

        et_profile_address.setEnabled(true);
        et_profile_postalcode.setEnabled(true);
        et_profile_registerdate.setEnabled(true);
        et_profile_city.setEnabled(true);
    }

    private void disableViews() {
        btn_profile_update.setVisibility(View.GONE);
        et_profile_name.setEnabled(false);
        ccp_profile_countrycode.setEnabled(false);
        et_profile_email.setEnabled(false);
        et_profile_address.setEnabled(false);
        et_profile_postalcode.setEnabled(false);
        et_profile_registerdate.setEnabled(false);
        et_profile_city.setEnabled(false);
    }

    /**
     * Method used to update the profile
     */
    private void updateProfile() {
        MultipartBody.Part profile_pic = null;
        // add another part within the multipart request
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), et_profile_name.getText().toString());
        RequestBody countryCode = RequestBody.create(MediaType.parse("multipart/form-data"), ccp_profile_countrycode.getSelectedCountryCode());
        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), et_profile_email.getText().toString());
        RequestBody number = RequestBody.create(MediaType.parse("multipart/form-data"), et_profile_number.getText().toString());
        RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), et_profile_address.getText().toString());
        RequestBody postalcode = RequestBody.create(MediaType.parse("multipart/form-data"), et_profile_postalcode.getText().toString());
        RequestBody city = RequestBody.create(MediaType.parse("multipart/form-data"), et_profile_city.getText().toString());
        RequestBody lat = RequestBody.create(MediaType.parse("multipart/form-data"), mLat);
        RequestBody lng = RequestBody.create(MediaType.parse("multipart/form-data"), mLat);
        //RequestBody state = RequestBody.create(MediaType.parse("multipart/form-data"), mState);

        if (user_dp != null) {
            // Create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), user_dp);
            // MultipartBody.Part is used to send also the actual file name
            profile_pic = MultipartBody.Part.createFormData("image", user_dp.getName(), requestFile);
        }

        mProfilePresenter.updateProfile(name, countryCode, email, number, address, postalcode, city, lat, lng, profile_pic);
    }


    @Override
    public void showLoader() {
        progress.setTitle("Loading");
        progress.setMessage("Please wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
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
    public void setDataToAdapter(String res) {
        try {
            JSONObject dataa = new JSONObject(res);
            JSONArray dataArr = dataa.getJSONArray("data");
            JSONObject data = (JSONObject) dataArr.get(0);

            String image = data.optString("image");
            String name = data.optString("name");
            String phoneNumber = data.optString("contact");
            String countryCode = data.optString("country_code");
            String email = data.getJSONObject("user").optString("email");

            //GETTING DEFAULT ADDRESS
            JSONObject addressObj = data.getJSONObject("address");
            String address = addressObj.optString("address");
            String postalCode = data.optString("pincode");
            String registerDate = data.optString("secondary_contact");
            String city = data.optString("city");
            mLat = data.optString("lat");
            mLng = data.optString("lng");
            mState=data.optString("state");
            setViews(image, name, phoneNumber, countryCode, email, address, postalCode, registerDate, city, mLat, mLng);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void setViews(String image, String name, String phoneNumber, String countryCode, String email, String address, String postalCode, String registerDate, String city, String lat, String lng) {
        Function.setCircularImageView(img_profile, image, getActivity(), R.drawable.dummyuser);
        et_profile_name.setText(name != null && !name.equalsIgnoreCase("null") ? name : "");
        et_profile_number.setText(phoneNumber != null && !phoneNumber.equalsIgnoreCase("null") ? phoneNumber : "");
        et_profile_email.setText(email != null && !email.equalsIgnoreCase("null") ? email : "");
        ccp_profile_countrycode.setCountryForPhoneCode(Integer.parseInt(countryCode));
        et_profile_address.setText(address != null && !address.equalsIgnoreCase("null") ? address : "");
        et_profile_postalcode.setText(postalCode != null && !postalCode.equalsIgnoreCase("null") ? postalCode : "");
        /*etNumberOfBranch.setText(numberOfBranch != null && !numberOfBranch.equalsIgnoreCase("null") ? numberOfBranch : "");
        etOpeningTime.setText(startTime != null && !startTime.equalsIgnoreCase("null") ? startTime : "");
        etClosingTime.setText(endTime != null && !endTime.equalsIgnoreCase("null") ? endTime : "");*/
        camerachange();

    }


    @Override
    public void onSuccess() {
        disableViews();
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionCG() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)
                && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_CG);
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_CG);
        }
    }

    void dialogSelectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
            boolean result = Function.checkStoragePermission(getContext());
            if (items[item].equals("Take Photo")) {
                if (result)
                    cameraIntent();
            } else if (items[item].equals("Choose from Library")) {
                if (result)
                    galleryIntent();
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        user_dp = destination;
        Picasso.get().load(user_dp).transform(new CircleTransform()).into(img_profile);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String name = Function.getPath(getActivity(), data.getData());
        user_dp = new File(name);
        Picasso.get().load(user_dp).transform(new CircleTransform()).into(img_profile);
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setTitle(getString(R.string.str_update_profile_title));
        builder1.setMessage(getString(R.string.str_update_profile));
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Okay",
                (dialog, id) -> {
                    dialog.cancel();
                    updateProfile();
                });

        builder1.setNegativeButton(
                "Cancel",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
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
                        mPincode = mAddresses.get(0).getPostalCode();
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
                et_profile_address.setText(mOutput);
                et_profile_city.setText(mCity);
                et_profile_postalcode.setText(mPincode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
