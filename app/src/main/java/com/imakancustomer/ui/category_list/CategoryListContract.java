package com.imakancustomer.ui.category_list;

import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.model.NotificationPojo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class CategoryListContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void setDataToAdapter(List<CategoryListPojo.Datum> categoryList);
    }

    public interface Action {

        void getCategoryList();
    }


    public interface Service {
        @GET("customer/category/list")
        Call<CategoryListPojo>
        getCategoryListAPI(@Header("authorization") String authorization,
                           @Header("api_key") String api_key);

    }
}
