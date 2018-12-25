package com.imakancustomer.core;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.imakancustomer.core.dagger.AppComponent;
import com.imakancustomer.core.dagger.AppModule;
import com.imakancustomer.core.dagger.DaggerAppComponent;

public class ImakanCustomerApplication extends Application {
    
    private static AppComponent appComponent;
    private static ImakanCustomerApplication instance = new ImakanCustomerApplication();

    public static ImakanCustomerApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getAppComponent();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
