package com.imakancustomer.ui.add_address;

import com.imakancustomer.model.CategoryListPojo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class AddAddressContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void onSuccess();
    }

    public interface Action {
        void addAddress(String data);
    }


    public interface Service {
        @Headers("Content-Type: application/json")
        @POST("customer/address/create")
        Call<ResponseBody>
        addAddressAPI(@Header("authorization") String authorization,
                      @Header("api_key") String api_key,
                      @Body String body);
    }

}
