package com.imakancustomer.ui.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.model.ProviderListPojo;
import com.imakancustomer.ui.category_list.CategoryListContract;
import com.imakancustomer.utils.Constant;
import com.imakancustomer.utils.SharedPref;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProviderListPresenter implements ProviderListContract.Action {


    private final ProviderListContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    ProviderListPresenter(ProviderListContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }


    @Override
    public void getProviderList(Double mlatitutde, Double mLongitude, String categoryId) {
        mView.showLoader();
        Log.d("auth2=", "bearer " + mSharedPref.getCustomerToken());
        ProviderListContract.Service service = mRetrofit.create(ProviderListContract.Service.class);
        Call<ProviderListPojo> call = service.getProviderListAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY, (double) 0, (double) 1000, mlatitutde, mLongitude, Double.parseDouble(categoryId));

        call.enqueue(new Callback<ProviderListPojo>() {
            @Override
            public void onResponse(Call<ProviderListPojo> call, @NonNull Response<ProviderListPojo> response) {
                mView.hideLoader();
                if (response.isSuccessful()) {
                    assert response.body() != null;
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
            public void onFailure(@NonNull Call<ProviderListPojo> call, @NonNull Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });

    }
}
