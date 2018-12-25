package com.imakancustomer.ui.login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class LoginContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

    }

    public interface Action {
        void onBtnSignInClicked(String email, String password);

        void onForgotPassword(String email);

        void doLogin(String s);
    }

    public interface Service {

        @Headers("Content-Type: application/json")
        @POST("user/login")
        Call<ResponseBody> login(@Header("api_key") String apikey, @Body String body);


        @Headers("Content-Type: application/json")
        @POST("user/forgotPassword")
        Call<ResponseBody> forgotPassword(@Body String body, @Header("api_key") String apikey);

    }
}
