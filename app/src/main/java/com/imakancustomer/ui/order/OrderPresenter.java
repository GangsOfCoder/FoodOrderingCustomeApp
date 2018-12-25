package com.imakancustomer.ui.order;

import android.content.Context;
import android.support.annotation.NonNull;

import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.model.BookingListPojo;
import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.ui.category_list.CategoryListContract;
import com.imakancustomer.utils.Constant;
import com.imakancustomer.utils.SharedPref;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderPresenter implements OrderContract.Action {

    private final OrderContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    OrderPresenter(OrderContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }


    @Override
    public void getOrderlist() {
        mView.showLoader();
        OrderContract.Service service = mRetrofit.create(OrderContract.Service.class);
        Call<BookingListPojo> call = service.getOrdersListAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY, "0", "100");
        call.enqueue(new Callback<BookingListPojo>() {
            @Override
            public void onResponse(@NonNull Call<BookingListPojo> call, @NonNull Response<BookingListPojo> response) {
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
            public void onFailure(@NonNull Call<BookingListPojo> call, @NonNull Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }
}
