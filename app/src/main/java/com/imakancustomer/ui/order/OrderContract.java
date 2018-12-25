package com.imakancustomer.ui.order;

import com.imakancustomer.model.BookingListPojo;
import com.imakancustomer.model.CategoryListPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class OrderContract {
    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void setDataToAdapter(List<BookingListPojo.Datum> categoryList);
    }

    public interface Action {

        void getOrderlist();
    }


    public interface Service {
        @GET("customer/booking/list")
        Call<BookingListPojo>
        getOrdersListAPI(@Header("authorization") String authorization,
                         @Header("api_key") String api_key,
                         @Query("skipBy") String skipBy,
                         @Query("limit") String limit);

    }
}
