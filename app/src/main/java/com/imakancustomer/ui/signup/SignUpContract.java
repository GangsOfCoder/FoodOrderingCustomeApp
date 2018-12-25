package com.imakancustomer.ui.signup;


public class SignUpContract {

    public interface View {
        void showLoader();

        void hideLoader();

        void showMessage(String msg);

    }

    public interface Action {

        boolean validation(String mFirstName, String mLastName, String mEmail, String mNumber, String mPassword, String mConPassword);
    }

    public interface Service {


    }
}
