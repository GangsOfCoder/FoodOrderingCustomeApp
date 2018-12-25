package com.imakancustomer.ui.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.imakancustomer.R;
import com.imakancustomer.ui.login.LoginActivity;
import com.imakancustomer.ui.signup_map.SignUpMapActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    //VIEWS
    @BindView(R.id.et_signup_fn)
    EditText et_signup_fn;
    @BindView(R.id.et_signup_ln)
    EditText et_signup_ln;
    @BindView(R.id.et_signup_email)
    EditText et_signup_email;
    @BindView(R.id.et_signup_number)
    EditText et_signup_number;
    @BindView(R.id.et_signup_password)
    EditText et_signup_password;
    @BindView(R.id.et_signup_conpass)
    EditText et_signup_conpass;
    @BindView(R.id.ccp_signup_countrycode)
    CountryCodePicker ccp_signup_countrycode;

    private SignUpContract.Action mSignUpPresenter;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mSignUpPresenter = new SignUpPresenter(this, this);
    }


    @Override
    public void showLoader() {
        progress.setTitle(getString(R.string.str_loading));
        progress.setMessage(getString(R.string.str_please_wait));
        progress.setCancelable(false);
        progress.show();
    }

    @OnClick(R.id.btn_signup_next)
    public void onNextClicked() {
        String mFirstName = et_signup_fn.getText().toString();
        String mLastName = et_signup_ln.getText().toString();
        String mEmail = et_signup_email.getText().toString();
        String mNumber = et_signup_number.getText().toString();
        String mPassword = et_signup_password.getText().toString();
        String mConPassword = et_signup_conpass.getText().toString();
        String mPrimaryNoCode = ccp_signup_countrycode.getSelectedCountryCode();

        if (mSignUpPresenter.validation(mFirstName, mLastName, mEmail, mNumber, mPassword, mConPassword)) {
            Intent intent = new Intent(SignUpActivity.this, SignUpMapActivity.class);
            intent.putExtra("mFirstName", mFirstName);
            intent.putExtra("mLastName", mLastName);
            intent.putExtra("mEmail", mEmail);
            intent.putExtra("mNumber", mNumber);
            intent.putExtra("mCountryCode", mPrimaryNoCode);
            intent.putExtra("mPassword", mPassword);
            startActivity(intent);
        }
        //startActivity(new Intent(this, SignUpMapActivity.class));
    }

    @OnClick(R.id.tv_sign_here)
    public void onSignHereClicked() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    @Override
    public void hideLoader() {
        progress.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
