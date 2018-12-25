package com.imakancustomer.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.utils.Constant;
import com.imakancustomer.utils.Function;
import com.imakancustomer.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPresenter implements LoginContract.Action {

    private final LoginContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit retrofit;

    public LoginPresenter(LoginContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }


    @Override
    public void onBtnSignInClicked(String email, String password) {

    }

    @Override
    public void onForgotPassword(String email) {
        mView.showLoader();
        JSONObject param = new JSONObject();
        try {
            param.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LoginContract.Service service = retrofit.create(LoginContract.Service.class);
        Call<ResponseBody> call = service.forgotPassword(param.toString(), Constant.API_KEY);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    try {
                        JSONObject data = new JSONObject(response.body().string());
                        if (data.getString("statusCode").equals("200")) {
                            mView.showMessage(data.getString("message"));
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

    @Override
    public void doLogin(String data) {

        mView.showLoader();
        LoginContract.Service service = retrofit.create(LoginContract.Service.class);
        Call<ResponseBody> call = service.login(Constant.API_KEY, data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    try {
                        //String res = response.body().string();
                        JSONObject data = new JSONObject(response.body().string());
                        if (data.getString("statusCode").equalsIgnoreCase("200")) {
                            mView.showMessage(data.getString("message"));
                            JSONArray res = data.getJSONArray("data");
                            JSONObject jsonObject = (JSONObject) res.get(0);
                            String email = jsonObject.optString("email");
                            String username = jsonObject.optString("name");
                            String accessToken = jsonObject.optString("acceessToken");

                            mSharedPref.saveUserData(accessToken, username, email, true);
                            Intent intent = new Intent(mContext, HomeActivity.class);
                            mContext.startActivity(intent);
                            ((Activity) mContext).finish();

                        } else {
                            mView.showMessage("message");
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    } finally {
                        mView.hideLoader();
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mView.hideLoader();
                mView.showMessage("Please try again later");
            }
        });
    }
}
