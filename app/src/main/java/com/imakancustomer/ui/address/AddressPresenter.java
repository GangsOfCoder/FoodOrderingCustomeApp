package com.imakancustomer.ui.address;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.imakancustomer.R;
import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.model.AddressListPojo;
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

public class AddressPresenter implements AddressContract.Action {

    private final AddressContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    AddressPresenter(AddressContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }


    @Override
    public void getAddressList() {

        mView.showLoader();
        Log.d("auth", "bearer " + mSharedPref.getCustomerToken());
        Log.d("api", Constant.API_KEY);
        AddressContract.Service service = mRetrofit.create(AddressContract.Service.class);
        Call<AddressListPojo> call = service.getAddressListAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY);
        call.enqueue(new Callback<AddressListPojo>() {
            @Override
            public void onResponse(Call<AddressListPojo> call, Response<AddressListPojo> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatusCode() == 200) {
                        assert response.body() != null;
                        mView.setDataToAdapter(response.body().getData());
                    } else {
                        assert response.body() != null;
                        mView.showMessage(response.body().getMessage());
                    }
                } else {
                    mView.showMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddressListPojo> call, Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }


    @Override
    public void deleteAddress(String addressId) {
        mView.showLoader();
        AddressContract.Service service = mRetrofit.create(AddressContract.Service.class);
        Call<ResponseBody> call = service.deleteAddressAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY, addressId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        JSONObject object = new JSONObject(res);
                        if (object.getString("statusCode").equalsIgnoreCase("200")) {
                            mView.showMessage(object.getString("message"));
                            getAddressList();
                        } else {
                            mView.showMessage(response.message());
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    mView.showMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void setPrimaryAddress(String data) {
        mView.showLoader();
        AddressContract.Service service = mRetrofit.create(AddressContract.Service.class);
        Call<ResponseBody> call = service.primaryAddressAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY, data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        JSONObject object = new JSONObject(res);
                        if (object.getString("statusCode").equalsIgnoreCase("200")) {
                            mView.showMessage(object.getString("message"));
                            getAddressList();
                        } else {
                            mView.showMessage(response.message());
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    mView.showMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }
}
