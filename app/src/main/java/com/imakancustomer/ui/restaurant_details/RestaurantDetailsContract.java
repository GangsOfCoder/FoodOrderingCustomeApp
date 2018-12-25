package com.imakancustomer.ui.restaurant_details;

import com.imakancustomer.model.ProviderDetailsPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class RestaurantDetailsContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void setDataToAdapter(List<ProviderDetailsPojo.Datum> categoryList);
    }

    public interface Action {
        void getProviderDetails(String providerId);
    }


    public interface Service {
        ///api/customer/providerDetail
        @GET("customer/providerDetail")
        Call<ProviderDetailsPojo>
        getProviderDetailsAPI(@Header("authorization") String authorization,
                              @Header("api_key") String api_key,
                              @Query("providerId") String skipBy);
    }
}
