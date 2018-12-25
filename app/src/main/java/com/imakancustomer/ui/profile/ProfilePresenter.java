package com.imakancustomer.ui.profile;

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

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfilePresenter implements ProfileContract.Action {
    private final ProfileContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;

    @Inject
    Retrofit mRetrofit;

    public ProfilePresenter(ProfileContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void getProfileDetails() {

        //mView.showLoader();
        ProfileContract.Service service = mRetrofit.create(ProfileContract.Service.class);
        Log.d("TOKEN=", "bearer " + mSharedPref.getCustomerToken());
        Log.d("KEY=", Constant.API_KEY);
        Call<ResponseBody> call = service.getProfileDetailsAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //mView.hideLoader();
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        Log.d("RES=", res);
                        JSONObject data = new JSONObject(res);
                        if (data.getString("statusCode").equals("200")) {
                            mView.setDataToAdapter(res);
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
                //mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void updateProfile(RequestBody name, RequestBody countryCode, RequestBody email, RequestBody number, RequestBody address, RequestBody postalcode, RequestBody city, RequestBody lat, RequestBody lng, MultipartBody.Part profile_pic) {

        mView.showLoader();
        ProfileContract.Service service = mRetrofit.create(ProfileContract.Service.class);
        Call<ResponseBody> call = service.updateProfileAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY, name, number, countryCode, email, lat, lng, address,city, postalcode, profile_pic);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        JSONObject data = new JSONObject(res);
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
