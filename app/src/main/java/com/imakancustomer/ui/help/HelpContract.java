package com.imakancustomer.ui.help;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class HelpContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void onSuccess();
    }

    public interface Action {
        void onBtnSendClicked(String body);
    }

    public interface Service {

        @Headers("Content-Type: application/json")
        @POST("customer/hepl/send")
        Call<ResponseBody> sendHelpRequestAPI(
                @Header("authorization") String authorization,
                @Header("api_key") String apikey,
                @Body String body);

    }

}
