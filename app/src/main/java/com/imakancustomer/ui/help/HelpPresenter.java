package com.imakancustomer.ui.help;

import android.content.Context;
import android.support.annotation.NonNull;

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

public class HelpPresenter implements HelpContract.Action {

    private final HelpContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    HelpPresenter(HelpContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        this.mContext = mContext;
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void onBtnSendClicked(String body) {
        mView.showLoader();
        HelpContract.Service service = mRetrofit.create(HelpContract.Service.class);
        Call<ResponseBody> call = service.sendHelpRequestAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        JSONObject data = new JSONObject(res);
                        if (data.getString("statusCode").equals("200")) {
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
    }
}
