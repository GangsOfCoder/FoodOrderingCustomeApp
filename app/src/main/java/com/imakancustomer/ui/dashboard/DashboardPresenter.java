package com.imakancustomer.ui.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;

import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.model.BookingListPojo;
import com.imakancustomer.model.DashboardPojo;
import com.imakancustomer.ui.order.OrderContract;
import com.imakancustomer.utils.Constant;
import com.imakancustomer.utils.SharedPref;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardPresenter implements DashboardContract.Action {
    private final DashboardContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    DashboardPresenter(DashboardContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }


    @Override
    public void getDashboardlist() {
        mView.showLoader();
        DashboardContract.Service service = mRetrofit.create(DashboardContract.Service.class);
        Call<DashboardPojo> call = service.getDashboardListAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY);
        call.enqueue(new Callback<DashboardPojo>() {
            @Override
            public void onResponse(@NonNull Call<DashboardPojo> call, @NonNull Response<DashboardPojo> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == 200) {
                        mView.setDataToAdapter(response.body());
                    } else {
                        mView.showMessage(response.body().getMessage());
                    }
                } else {
                    mView.showMessage(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DashboardPojo> call, @NonNull Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }
}
