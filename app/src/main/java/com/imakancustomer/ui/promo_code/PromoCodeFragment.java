package com.imakancustomer.ui.promo_code;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.imakancustomer.R;
import com.imakancustomer.ui.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PromoCodeFragment extends Fragment {


    @BindView(R.id.et_promo_code)
    EditText et_promo_code;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_promo_code, container, false);
        ButterKnife.bind(this, mView);
        HomeActivity.hideShowLocationOption(false, "");
        return mView;
    }


    @OnClick(R.id.btn_promo_apply)
    public void onPromoBtnClicked() {

    }

}
