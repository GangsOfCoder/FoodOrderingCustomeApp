package com.imakancustomer.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;

import com.imakancustomer.R;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.ui.login.LoginActivity;
import com.imakancustomer.utils.Function;
import com.imakancustomer.utils.GPSTracker;
import com.imakancustomer.utils.SharedPref;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler;
    GPSTracker gpsTracker;
    private SharedPref sharedPref;

    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gpsTracker = new GPSTracker(SplashActivity.this); // Object for GPS Tracker
        //generateKeyHash();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermissions()) {
            gpsTracker.getLocation();
            if (gpsTracker.canGetLocation()) {
                if (Function.isConnectedInternet(SplashActivity.this)) {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        sharedPref = new SharedPref(SplashActivity.this);
                        if (sharedPref.getLogin()) {
                            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    }, 3000);
                } else {
                    Function.customadialog(SplashActivity.this, getString(R.string.check_internet));
                }
            } else {
                gpsTracker.showSettingsAlert();
            }
        }
    }

    /**
     * Method to generate sha key
     */
    private void generateKeyHash() {
        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {
            ignored.printStackTrace();
        }
    }

    private boolean checkPermissions() {

        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

}
