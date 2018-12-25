package com.imakancustomer.ui.add_address;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.utils.Constant;
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

public class AddAddressPresenter implements AddAddressContract.Action {

    private final AddAddressContract.View mView;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    AddAddressPresenter(AddAddressContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void addAddress(String data) {
        mView.showLoader();
        AddAddressContract.Service service = mRetrofit.create(AddAddressContract.Service.class);
        Call<ResponseBody> call = service.addAddressAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY, data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    try {
                        JSONObject data = new JSONObject(response.body().string());
                        if (data.getString("statusCode").equals("200")) {
                            mView.showMessage(data.getString("message"));
                            mView.onSuccess();
                        } else {
                            mView.showMessage(data.getString("message"));
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        mView.showMessage(jsonObject.getString("message"));
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
    }


}
