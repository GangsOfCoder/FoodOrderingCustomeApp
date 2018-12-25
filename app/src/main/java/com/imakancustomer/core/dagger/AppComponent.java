package com.imakancustomer.core.dagger;

import com.imakancustomer.ui.add_address.AddAddressPresenter;
import com.imakancustomer.ui.address.AddressPresenter;
import com.imakancustomer.ui.basket.BasketPresenter;
import com.imakancustomer.ui.category_list.CategoryListPresenter;
import com.imakancustomer.ui.dashboard.DashboardPresenter;
import com.imakancustomer.ui.help.HelpPresenter;
import com.imakancustomer.ui.login.LoginContract;
import com.imakancustomer.ui.login.LoginPresenter;
import com.imakancustomer.ui.notification.NotificationPresenter;
import com.imakancustomer.ui.order.OrderPresenter;
import com.imakancustomer.ui.payment.PaymentPresenter;
import com.imakancustomer.ui.profile.ProfilePresenter;
import com.imakancustomer.ui.promo_code.PromoCodePresenter;
import com.imakancustomer.ui.provider.ProviderListPresenter;
import com.imakancustomer.ui.restaurant_details.RestaurantDetailsPresenter;
import com.imakancustomer.ui.result_list.SearchResultPresenter;
import com.imakancustomer.ui.select_order.SelectOrderPresenter;
import com.imakancustomer.ui.signup.SignUpPresenter;
import com.imakancustomer.ui.signup_map.SignUpMapPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class
        }
)
public interface AppComponent {
    void inject(LoginPresenter loginPresenter);

    void inject(AddAddressPresenter addAddressPresenter);

    void inject(BasketPresenter basketPresenter);

    void inject(CategoryListPresenter categoryListPresenter);

    void inject(DashboardPresenter dashboardPresenter);

    void inject(HelpPresenter helpPresenter);

    void inject(NotificationPresenter notificationPresenter);

    void inject(OrderPresenter orderPresenter);

    void inject(PaymentPresenter paymentPresenter);

    void inject(ProfilePresenter profilePresenter);

    void inject(PromoCodePresenter promoCodePresenter);

    void inject(RestaurantDetailsPresenter restaurantDetailsPresenter);

    void inject(SearchResultPresenter searchResultPresenter);

    void inject(SelectOrderPresenter selectOrderPresenter);

    void inject(SignUpPresenter signUpPresenter);

    void inject(SignUpMapPresenter signUpMapPresenter);

    void inject(AddressPresenter addressPresenter);

    void inject(ProviderListPresenter providerListPresenter);
}

