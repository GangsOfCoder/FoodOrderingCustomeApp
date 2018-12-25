package com.imakancustomer.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {


    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;
    private String PREF_KEY = "IMAKANCUSTOMER";
    private String DEVICE_TOKEN = "DEVICE_TOKEN";
    private String CUSTOMER_TOKEN = "CUSTOMER_TOKEN";
    private String CUSTOMER_NAME = "CUSTOMER_NAME";
    private String CUSTOMER_EMAIL = "CUSTOMER_EMAIL";
    private String CUSTOMER_IS_LOGIN = "CUSTOMER_IS_LOGIN";

    private Context mContext;
    private String mDeviceToken;


    public SharedPref(Context mContext) {
        this.mContext = mContext;
        mSharedPref = mContext.getSharedPreferences(PREF_KEY, 0);
        mEditor = mSharedPref.edit();
    }


    public void saveUserData(String token, String name, String email, boolean isLogin) {
        mEditor.putString(CUSTOMER_TOKEN, token);
        mEditor.putString(CUSTOMER_NAME, name);
        mEditor.putString(CUSTOMER_EMAIL, email);
        mEditor.putBoolean(CUSTOMER_IS_LOGIN, isLogin);
        mEditor.apply();
    }

    public boolean getLogin() {
        return mSharedPref.getBoolean(CUSTOMER_IS_LOGIN, false);
    }


    public void setLogin(boolean isLogin) {
        mEditor.putBoolean(CUSTOMER_IS_LOGIN, isLogin);
        mEditor.apply();
    }


    public String getDeviceToken() {
        return mSharedPref.getString(DEVICE_TOKEN, "");
    }


    public String getCustomerToken() {
        return mSharedPref.getString(CUSTOMER_TOKEN, "");
    }


    public String getEmailId() {
        return mSharedPref.getString(CUSTOMER_EMAIL, "");
    }

    public String getFullName() {
        return mSharedPref.getString(CUSTOMER_NAME, "");
    }

    public void setDeviceToken(String mDeviceToken) {
        this.mDeviceToken = mDeviceToken;
        mEditor = mSharedPref.edit();
        mEditor.putString(DEVICE_TOKEN, mDeviceToken);
        mEditor.apply();
    }
}
