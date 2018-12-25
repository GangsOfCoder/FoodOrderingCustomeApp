package com.imakancustomer.ui.signup;

import android.content.Context;
import android.util.Patterns;

import com.imakancustomer.R;
import com.imakancustomer.core.ImakanCustomerApplication;
import com.imakancustomer.utils.SharedPref;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class SignUpPresenter implements SignUpContract.Action {

    private final SignUpContract.View mView;
    private Context mContext;
    private SharedPref mSharedPref;
    @Inject
    Retrofit retrofit;

    public SignUpPresenter(SignUpContract.View mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
        mSharedPref = new SharedPref(mContext);
        ImakanCustomerApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public boolean validation(String mFirstName, String mLastName, String mEmail, String mNumber, String mPassword, String mConPassword) {
        if (mFirstName.isEmpty()) {
            mView.showMessage(mContext.getString(R.string.valid_first_name));
            return false;
        } else if (mLastName.isEmpty()) {
            mView.showMessage(mContext.getString(R.string.valid_first_name));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mView.showMessage(mContext.getString(R.string.str_invalid_email));
            return false;
        } else if (mPassword.isEmpty()) {
            mView.showMessage(mContext.getString(R.string.valid_pass));
            return false;
        } else if (mConPassword.isEmpty()) {
            mView.showMessage(mContext.getString(R.string.valid_conpass));
            return false;
        } else if (!mPassword.equals(mConPassword)) {
            mView.showMessage(mContext.getString(R.string.valid_notmatch));
            return false;
        }
        return true;
    }

}
