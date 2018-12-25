package com.imakancustomer.ui.signup_map;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class SignUpMapContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void onSuccess(String type);

    }

    public interface Action {


        void doRegister(String body);

        boolean validation(String address, String city, String postalcode);
    }


    public interface Service {
        @Headers("Content-Type: application/json")
        @POST("user/register")
        Call<ResponseBody> register(@Header("api_key") String userkey, @Body String body);
    }
}
