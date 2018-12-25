package com.imakancustomer.ui.profile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public class ProfileContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

        void setDataToAdapter(String data);

        void onSuccess();

    }

    public interface Action {
        void getProfileDetails();

        void updateProfile(RequestBody name, RequestBody countryCode, RequestBody email, RequestBody number, RequestBody address, RequestBody postalcode, RequestBody city, RequestBody lat, RequestBody lng, MultipartBody.Part profile_pic);
    }


    public interface Service {

        @GET("user/getDetails")
        Call<ResponseBody> getProfileDetailsAPI(
                @Header("authorization") String auth,
                @Header("api_key") String apikey);

        @Multipart
        @PUT("user/updateProfile")
        Call<ResponseBody> updateProfileAPI(
                @Header("authorization") String token,
                @Header("api_key") String api_key,
                @Part("name") RequestBody name,
                @Part("contact") RequestBody contact,
                @Part("country_code") RequestBody country_code,
                @Part("email") RequestBody email,
                @Part("lat") RequestBody lat,
                @Part("lng") RequestBody lng,
                @Part("address") RequestBody address,
                @Part("city") RequestBody city,
                @Part("pincode") RequestBody pincode,
                @Part MultipartBody.Part profile_pic);
    }
}
