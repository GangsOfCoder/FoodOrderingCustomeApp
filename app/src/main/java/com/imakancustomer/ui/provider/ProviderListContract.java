package com.imakancustomer.ui.provider;

import com.imakancustomer.model.ProviderListPojo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class ProviderListContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void setDataToAdapter(List<ProviderListPojo.Datum> categoryList);
    }

    public interface Action {
        void getProviderList(Double mlatitutde, Double mLongitude, String category_id);
    }


    public interface Service {

        @GET("customer/providerList")
        Call<ProviderListPojo>
        getProviderListAPI(@Header("authorization") String authorization,
                           @Header("api_key") String api_key,
                           @Query("skipBy") Double skip_by,
                           @Query("limit") Double limit,
                           @Query("latitude") Double lat,
                           @Query("longitude") Double lng,
                           @Query("categoryId") Double category_id);

    }
}

