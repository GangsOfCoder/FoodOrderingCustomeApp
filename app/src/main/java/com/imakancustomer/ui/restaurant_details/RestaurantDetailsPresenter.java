package com.imakancustomer.ui.restaurant_details;

import android.content.Context;
import android.support.annotation.NonNull;

import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.model.BookingListPojo;
import com.imakancustomer.model.ProviderDetailsPojo;
import com.imakancustomer.ui.order.OrderContract;
import com.imakancustomer.utils.Constant;
import com.imakancustomer.utils.SharedPref;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestaurantDetailsPresenter implements RestaurantDetailsContract.Action {


    private final RestaurantDetailsContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    RestaurantDetailsPresenter(RestaurantDetailsContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void getProviderDetails(String providerId) {
        mView.showLoader();
        RestaurantDetailsContract.Service service = mRetrofit.create(RestaurantDetailsContract.Service.class);
        Call<ProviderDetailsPojo> call = service.getProviderDetailsAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY, providerId);
        call.enqueue(new Callback<ProviderDetailsPojo>() {
            @Override
            public void onResponse(@NonNull Call<ProviderDetailsPojo> call, @NonNull Response<ProviderDetailsPojo> response) {
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
            public void onFailure(@NonNull Call<ProviderDetailsPojo> call, @NonNull Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }
}
