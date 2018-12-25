package com.imakancustomer.ui.basket;

import com.imakancustomer.model.AddressListPojo;
import com.imakancustomer.model.ProviderListPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;


public class BasketContract {
    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void setDataToAdapter(List<AddressListPojo.Datum> categoryList);

    }

    public interface Action {
        void getPrimaryAddress();
    }


    public interface Service {

        @GET("customer/address/list")
        Call<AddressListPojo>
        getPrimaryAddressAPI(@Header("authorization") String authorization,
                             @Header("api_key") String api_key);


    }
}
