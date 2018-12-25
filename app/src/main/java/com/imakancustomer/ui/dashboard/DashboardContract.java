package com.imakancustomer.ui.dashboard;

import com.imakancustomer.model.DashboardPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public class DashboardContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void setDataToAdapter(DashboardPojo categoryList);
    }

    public interface Action {

        void getDashboardlist();
    }


    public interface Service {
        @GET("customer/dashboard")
        Call<DashboardPojo>
        getDashboardListAPI(@Header("authorization") String authorization,
                            @Header("api_key") String api_key);

    }
}
