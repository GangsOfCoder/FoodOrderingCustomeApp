package com.imakancustomer.ui.help;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.imakancustomer.R;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.utils.Function;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpFragment extends Fragment implements HelpContract.View {

    //VIEWS
    @BindView(R.id.spinner_help_reason)
    Spinner spinner_help_reason;
    @BindView(R.id.et_help_email)
    EditText et_help_email;
    @BindView(R.id.et_help_briefus)
    EditText et_help_briefus;
    @BindView(R.id.ll_loader)
    LinearLayout ll_loader;

    private View mView;
    private HelpContract.Action mHelpPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_help);
        mView = inflater.inflate(R.layout.fragment_help, container, false);
        mHelpPresenter = new HelpPresenter(this, getActivity());
        HomeActivity.hideShowLocationOption(false,"");
        ButterKnife.bind(this, mView);
        return mView;
    }

    @OnClick(R.id.btn_help_send)
    public void onButtonSendClicked() {
        if (validation()) {
            if (Function.isConnectedInternet(getActivity())) {
                JSONObject objectj = new JSONObject();
                try {
                    objectj.put("request_msg", et_help_briefus.getText().toString());
                    objectj.put("email", et_help_email.getText().toString());
                    objectj.put("issue", spinner_help_reason.getSelectedItem().toString());
                    mHelpPresenter.onBtnSendClicked(objectj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                showMessage(getString(R.string.no_internet));
            }
        }
    }

    private boolean validation() {
        if (et_help_email.getText().toString().isEmpty()) {
            showMessage(getString(R.string.str_invalid_email));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(et_help_email.getText().toString().trim()).matches()) {
            showMessage(getString(R.string.str_invalid_email));
            return false;
        } else if (et_help_briefus.getText().toString().isEmpty()) {
            showMessage(getString(R.string.str_enter_brief_msg));
            return false;
        }
        return true;
    }

    @Override
    public void showLoader() {
        ll_loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        ll_loader.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        et_help_briefus.setText("");
    }
}
