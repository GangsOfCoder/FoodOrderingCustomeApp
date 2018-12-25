package com.imakancustomer.ui.address;

import com.imakancustomer.model.AddressListPojo;
import com.imakancustomer.model.CategoryListPojo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public class AddressContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void setDataToAdapter(List<AddressListPojo.Datum> categoryList);


    }

    public interface Action {
        void getAddressList();

        void deleteAddress(String addressId);

        void setPrimaryAddress(String s);
    }


    public interface Service {
        @GET("customer/address/list")
        Call<AddressListPojo>
        getAddressListAPI(@Header("authorization") String authorization,
                          @Header("api_key") String api_key);


        @DELETE("customer/address/delete")
        Call<ResponseBody>
        deleteAddressAPI(@Header("authorization") String authorization,
                         @Header("api_key") String api_key,
                         @Query("addressId") String addressId);


        @Headers("Content-Type: application/json")
        @PUT("customer/address/enable")
        Call<ResponseBody>
        primaryAddressAPI(@Header("authorization") String authorization,
                          @Header("api_key") String api_key,
                          @Body String body);
    }
}
