package com.imakancustomer.core.dagger;

import android.content.Context;

import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.utils.Constant;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class AppModule {

    private final ImakanCustomerApplication app;

    public AppModule(ImakanCustomerApplication app) {
        this.app = app;
    }


    @Provides
    @Singleton
    ImakanCustomerApplication provideApp() {
        return app;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(40, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
