package com.imakancustomer.ui.basket;

import android.content.Context;
import android.util.Log;

import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.model.AddressListPojo;
import com.imakancustomer.ui.address.AddressContract;
import com.imakancustomer.utils.Constant;
import com.imakancustomer.utils.SharedPref;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BasketPresenter implements BasketContract.Action {

    private final BasketContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    BasketPresenter(BasketContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }


    @Override
    public void getPrimaryAddress() {
        mView.showLoader();
        BasketContract.Service service = mRetrofit.create(BasketContract.Service.class);
        Call<AddressListPojo> call = service.getPrimaryAddressAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY);
        call.enqueue(new Callback<AddressListPojo>() {
            @Override
            public void onResponse(Call<AddressListPojo> call, Response<AddressListPojo> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatusCode() == 200) {
                        assert response.body() != null;
                        mView.setDataToAdapter(response.body().getData());
                    } else {
                        assert response.body() != null;
                        mView.showMessage(response.body().getMessage());
                    }
                } else {
                    mView.showMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<AddressListPojo> call, Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }
}
