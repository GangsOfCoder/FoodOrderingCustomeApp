package com.imakancustomer.ui.notification;

import android.content.Context;

import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.model.NotificationPojo;
import com.imakancustomer.utils.Constant;
import com.imakancustomer.utils.SharedPref;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NotificationPresenter implements NotificationContract.Action {

    private final NotificationContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    NotificationPresenter(NotificationContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void getNotitficationList() {
        mView.showLoader();
        NotificationContract.Service service = mRetrofit.create(NotificationContract.Service.class);
        Call<NotificationPojo> call = service.getNotificationAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY);
        call.enqueue(new Callback<NotificationPojo>() {
            @Override
            public void onResponse(Call<NotificationPojo> call, Response<NotificationPojo> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == 200) {
                        mView.setDataToAdapter(response.body().getData());
                    } else {
                        mView.showMessage(response.body().getMessage());
                    }
                } else {
                    mView.showMessage(response.message());
                }

            }

            @Override
            public void onFailure(Call<NotificationPojo> call, Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }
}
