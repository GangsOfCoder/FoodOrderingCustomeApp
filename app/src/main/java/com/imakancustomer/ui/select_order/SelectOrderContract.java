package com.imakancustomer.ui.select_order;

import org.json.JSONArray;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class SelectOrderContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void apiResponse(JSONArray res);

    }

    public interface Action {
        void getServiceList(String providerId, String subCategoryIds);
    }


    public interface Service {
        ///api/customer/serviceListing
        @GET("customer/serviceListing")
        Call<ResponseBody>
        getServiceListAPI(@Header("authorization") String authorization,
                         @Header("api_key") String api_key,
                         @Query("skipBy") String skipBy,
                         @Query("limit") String limit,
                         @Query("providerId") String providerId,
                         @Query("subCategoryIds") String subCategoryIds);

    }
}
