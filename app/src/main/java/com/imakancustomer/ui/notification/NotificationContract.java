package com.imakancustomer.ui.notification;

import com.imakancustomer.model.NotificationPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public class NotificationContract {
    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void setDataToAdapter(List<NotificationPojo.Datum> eventList);
    }

    public interface Action {

        void getNotitficationList();
    }


    public interface Service {
        @GET("customer/notification/list")
        Call<NotificationPojo>
        getNotificationAPI(@Header("authorization") String authorization,
                           @Header("api_key") String api_key);

    }

}
