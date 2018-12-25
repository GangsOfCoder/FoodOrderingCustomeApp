package com.imakancustomer.ui.category_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.ui.notification.NotificationContract;
import com.imakancustomer.utils.Constant;
import com.imakancustomer.utils.SharedPref;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryListPresenter implements CategoryListContract.Action {

    private final CategoryListContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit mRetrofit;

    CategoryListPresenter(CategoryListContract.View mView, Context mContext) {
        this.mView = mView;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }


    @Override
    public void getCategoryList() {
        mView.showLoader();
        CategoryListContract.Service service = mRetrofit.create(CategoryListContract.Service.class);
        Call<CategoryListPojo> call = service.getCategoryListAPI("bearer " + mSharedPref.getCustomerToken(), Constant.API_KEY);
        call.enqueue(new Callback<CategoryListPojo>() {
            @Override
            public void onResponse(@NonNull Call<CategoryListPojo> call, @NonNull Response<CategoryListPojo> response) {
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
            public void onFailure(@NonNull Call<CategoryListPojo> call, @NonNull Throwable t) {
                mView.hideLoader();
                mView.showMessage(t.getMessage());
            }
        });
    }
}
