package com.imakancustomer.ui.restaurant_details;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.imakancustomer.R;
import com.imakancustomer.model.BookingListPojo;
import com.imakancustomer.model.ProviderDetailsPojo;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.ui.order.OrderAdapter;
import com.imakancustomer.ui.order.OrderContract;
import com.imakancustomer.ui.order.OrderFragment;
import com.imakancustomer.ui.order.OrderPresenter;
import com.imakancustomer.ui.select_order.SelectOrderNewFragment;
import com.imakancustomer.utils.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestaurantDetailsFragment extends Fragment implements RestaurantDetailsContract.View {

    @BindView(R.id.img_rd_image)
    ImageView img_rd_image;
    @BindView(R.id.tv_rd_name)
    TextView tv_rd_name;
    @BindView(R.id.tv_rd_rating)
    TextView tv_rd_rating;
    @BindView(R.id.rb_rd_rating)
    RatingBar rb_rd_rating;
    @BindView(R.id.tv_rd_type)
    TextView tv_rd_type;
    @BindView(R.id.tv_rd_about_restaurant)
    TextView tv_rd_about_restaurant;
    @BindView(R.id.tv_rd_address)
    TextView tv_rd_address;
    @BindView(R.id.tv_rd_contact)
    TextView tv_rd_contact;
    @BindView(R.id.tv_rd_expertise)
    TextView tv_rd_expertise;


    private View mView;
    private List<ProviderDetailsPojo.Datum> mRestaurantDetail = new ArrayList<>();
    private RestaurantDetailsContract.Action mRestaurantPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_my_orders);
        ButterKnife.bind(this, mView);
        HomeActivity.hideShowLocationOption(false, "");
        mRestaurantPresenter = new RestaurantDetailsPresenter(this, getActivity());
        checkInternet();
        setHasOptionsMenu(true);
        return mView;
    }

    private void checkInternet() {
        if (Function.isNetworkConnected(Objects.requireNonNull(getActivity()))) {
            mRestaurantPresenter.getProviderDetails(getArguments().getString("categoryId"));
        } else {
            Function.showMessage(getString(R.string.no_internet), getActivity());
        }
    }


    @OnClick(R.id.btn_rd_place_order)
    public void onPlaceOrderCliced() {

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setDataToAdapter(List<ProviderDetailsPojo.Datum> categoryList) {
        mRestaurantDetail = categoryList;
        Function.setCircularImageView(img_rd_image, categoryList.get(0).getImage(), getActivity(), R.drawable.dummyuser);
        tv_rd_name.setText(categoryList.get(0).getFirstName() + " " + categoryList.get(0).getLastName());
        tv_rd_address.setText(categoryList.get(0).getAddress() != null && !categoryList.get(0).getAddress().equalsIgnoreCase("null") ? categoryList.get(0).getAddress() : "");
        tv_rd_contact.setText(categoryList.get(0).getContact() != null && !categoryList.get(0).getContact().equalsIgnoreCase("null") ? categoryList.get(0).getContact() : "");
        tv_rd_type.setText(categoryList.get(0).getServiceName() != null && !categoryList.get(0).getServiceName().equalsIgnoreCase("null") ? categoryList.get(0).getServiceName() : "");
        String totalReviews = categoryList.get(0).getTotalReview().toString();
        tv_rd_rating.setText(totalReviews + " Reviews");
    }

    @OnClick(R.id.btn_rd_place_order)
    public void onPlaceOrderClicked() {
        Fragment fragment = new SelectOrderNewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("providerId", mRestaurantDetail.get(0).getUserId().toString());
        bundle.putString("subCategoryIds", mRestaurantDetail.get(0).getSubCategoryIds());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}