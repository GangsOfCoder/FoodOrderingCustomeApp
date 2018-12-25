package com.imakancustomer.ui.signup_map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.imakancustomer.R;
import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.utils.Constant;
import com.imakancustomer.utils.Function;
import com.imakancustomer.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpMapPresenter implements SignUpMapContract.Action {

    private final SignUpMapContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit retrofit;

    public SignUpMapPresenter(SignUpMapContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }


    @Override
    public boolean validation(String address, String city, String postalCode) {
        if (address.isEmpty()) {
            mView.showMessage(mContext.getString(R.string.str_enter_address));
            return false;
        } else if (city.isEmpty()) {
            mView.showMessage(mContext.getString(R.string.str_enter_city));
            return false;
        } else if (postalCode.isEmpty()) {
            mView.showMessage(mContext.getString(R.string.str_enter_postal_code));
            return false;
        }
        return true;
    }

    @Override
    public void doRegister(String body) {
        Log.d("TEST", body);
        if (Function.isNetworkConnected(mContext)) {
            mView.showLoader();
            SignUpMapContract.Service service = retrofit.create(SignUpMapContract.Service.class);
            Call<ResponseBody> call = service.register(Constant.API_KEY, body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    mView.hideLoader();
                    if (response.isSuccessful()) {
                        try {
                            String res = response.body().string();
                            JSONObject data = new JSONObject(res);
                            if (data.getString("statusCode").equals("200")) {
                                mView.showMessage(data.getString("message"));
                                mView.onSuccess("login");
                            } else {
                                mView.showMessage(data.getString("message"));
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            //mView.showMessage(jsonObject.getString("message"));
                            Function.customadialog(mContext, jsonObject.getString("message"));
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    mView.hideLoader();
                    mView.showMessage("Please try again later");
                }
            });
        } else {
            Function.customadialog(mContext, mContext.getString(R.string.check_internet));
        }
    }
}
