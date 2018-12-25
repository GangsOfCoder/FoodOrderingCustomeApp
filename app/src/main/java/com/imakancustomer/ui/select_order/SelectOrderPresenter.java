package com.imakancustomer.ui.select_order;

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

public class SelectOrderPresenter implements SelectOrderContract.Action {


    private final SelectOrderContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    SelectOrderPresenter(SelectOrderContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }


    @Override
    public void getServiceList(String providerId, String subCategoryIds) {
        mView.showLoader();
        SelectOrderContract.Service service = mRetrofit.create(SelectOrderContract.Service.class);
        Call<ResponseBody> call = service.getServiceListAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY, "0", "1000", providerId, subCategoryIds);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    try {
                        JSONObject data = new JSONObject(response.body().string());
                        if (data.getString("statusCode").equals("200")) {
                            mView.apiResponse(data.getJSONArray("data"));

                        } else {
                            mView.showMessage(data.getString("message"));
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    mView.showMessage(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }
}

