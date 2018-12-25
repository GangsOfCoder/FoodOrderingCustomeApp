package com.imakancustomer.ui.result_list;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imakancustomer.R;


public class SearchResultFragment extends Fragment {

    private View mView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_result, container, false);
        return mView;
    }

}
