package com.imakancustomer.ui.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imakancustomer.R;
import com.imakancustomer.sociallogin.facebook.FacebookHelper;
import com.imakancustomer.sociallogin.facebook.FacebookListener;
import com.imakancustomer.sociallogin.google.GoogleHelper;
import com.imakancustomer.sociallogin.google.GoogleListener;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.ui.signup.SignUpActivity;
import com.imakancustomer.utils.Function;
import com.imakancustomer.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, FacebookListener, GoogleListener {

    //VIEWS
    @BindView(R.id.et_signin_email)
    EditText et_signin_email;
    @BindView(R.id.et_signin_password)
    EditText et_signin_password;
    private SharedPref mSharedPref;

    private LoginContract.Action mLoginPresenter;
    private ProgressDialog progress;

    private FacebookHelper mFacebook;
    private GoogleHelper mGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progress = new ProgressDialog(this);
        mSharedPref = new SharedPref(this);
        mLoginPresenter = new LoginPresenter(this, this);
        //mFacebook = new FacebookHelper(this);
        mGoogle = new GoogleHelper(this, this, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGoogle.onActivityResult(requestCode, resultCode, data);
        mFacebook.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.tv_signin_signup)
    public void onSignUpClicked() {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

    @OnClick(R.id.btn_google)
    public void onBtnGoogleClicked() {
        mGoogle.performSignIn(this);
    }

    @OnClick(R.id.btn_fb)
    public void onBtnFbClicked() {
        mFacebook.performSignIn(this);
    }

    @OnClick(R.id.tv_signin_forgot)
    public void onForgotClicked() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_forgot_password);
        final EditText et_forgot_email = dialog.findViewById(R.id.et_forgot_email);
        Button btn_forget_cancel = dialog.findViewById(R.id.btn_forget_cancel);
        Button btn_forgot_request = dialog.findViewById(R.id.btn_forgot_request);
        dialog.show();
        //ON FORGOT REQUEST CLICKED
        btn_forgot_request.setOnClickListener(v -> {
            dialog.dismiss();
            if (Function.isNetworkConnected(LoginActivity.this)) {
                if (!Patterns.EMAIL_ADDRESS.matcher(et_forgot_email.getText().toString().trim()).matches()) {
                    showMessage(getString(R.string.str_invalid_email));
                } else {
                    mLoginPresenter.onForgotPassword(et_forgot_email.getText().toString().trim());
                }
            } else {
                Function.customadialog(this, getString(R.string.check_internet));
            }
        });
        //ON FORGOT CANCEL CLICKED
        btn_forget_cancel.setOnClickListener(v -> dialog.dismiss());
    }

    @OnClick(R.id.btn_signin)
    public void onSignInClicked() {
        String mEmail = et_signin_email.getText().toString().trim();
        String mPassword = et_signin_password.getText().toString().trim();

        if (mEmail.isEmpty()) {
            showMessage(getString(R.string.str_enter_email));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            showMessage(getString(R.string.str_invalid_email));
        } else if (mPassword.isEmpty()) {
            showMessage(getString(R.string.str_enter_password));
        } else {
            try {
                JSONObject param = new JSONObject();
                param.put("email", mEmail);
                param.put("password", mPassword);
                param.put("userType", "CUSTOMER");
                param.put("device_type", "ANDROID");
                param.put("device_token", mSharedPref.getDeviceToken());
                mLoginPresenter.doLogin(param.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void showLoader() {
        progress.setMessage(getString(R.string.str_please_wait));
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    public void hideLoader() {
        progress.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbSignInFail(String errorMessage) {

    }

    @Override
    public void onFbSignInSuccess(String authToken, String userId) {

    }

    @Override
    public void onFBSignOut() {

    }

    @Override
    public void onGoogleAuthSignIn(String authToken, String userId) {

    }

    @Override
    public void onGoogleAuthSignInFailed(String errorMessage) {

    }

    @Override
    public void onGoogleAuthSignOut() {

    }
}
