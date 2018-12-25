package com.imakancustomer.ui.category_list;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.stepsview.StepsView;
import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemSelectedListener;
import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.ui.home.HomeActivity;
import com.imakancustomer.ui.provider.ProviderListFragment;
import com.imakancustomer.utils.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryListFragment extends Fragment implements CategoryListContract.View, OnItemSelectedListener {

    @BindView(R.id.rv_categorylist)
    RecyclerView rv_categorylist;
    @BindView(R.id.ll_loader)
    LinearLayout ll_loader;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;
    @BindView(R.id.stepsView)
    StepsView stepsView;

    private View mView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CategoryListPojo.Datum> mCategoryList = new ArrayList<>();
    private CategoryListAdapter mCategoryListAdapter;
    private CategoryListContract.Action mCategoryListPresenter;
    private String arr[] = new String[]{"A", "B", "C", "D"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_select_category);
        mView = inflater.inflate(R.layout.fragment_category_list, container, false);
        ButterKnife.bind(this, mView);
        HomeActivity.hideShowLocationOption(false, "");
        mCategoryListPresenter = new CategoryListPresenter(this, getActivity());
        init();
        checkInternet();
        setHasOptionsMenu(true);

        stepsView.setLabels(arr)
                .setBarColorIndicator(getContext().getResources().getColor(R.color.material_blue_grey_800))
                .setProgressColorIndicator(getContext().getResources().getColor(R.color.primary))
                .setLabelColorIndicator(getContext().getResources().getColor(R.color.orange))
                .setCompletedPosition(2)
                .drawView();
        return mView;
    }

    private void checkInternet() {
        if (Function.isNetworkConnected(Objects.requireNonNull(getActivity()))) {

            mCategoryListPresenter.getCategoryList();
        } else {
            Function.showMessage(getString(R.string.no_internet), getActivity());
        }
    }


    private void init() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_categorylist.setLayoutManager(mLayoutManager);
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
    public void setDataToAdapter(List<CategoryListPojo.Datum> data) {

        if (data.size() > 0) {
            tv_no_data.setVisibility(View.GONE);
            mCategoryList = data;
            mCategoryListAdapter = new CategoryListAdapter(data.get(0).getCategoryList(), getActivity());
            mCategoryListAdapter.OnItemSelectedListener(this);
            rv_categorylist.setAdapter(mCategoryListAdapter);
            mCategoryListAdapter.notifyDataSetChanged();
        } else {
            tv_no_data.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method to handle the clicked on cateogry list
     *
     * @param position hold the position
     * @param type     hold the type of api
     */
    @Override
    public void onItemClick(int position, String type) {
        if (type.equalsIgnoreCase("CATEGORY_CLICKED")) {
            replaceFragment(new ProviderListFragment(), String.valueOf(mCategoryList.get(0).getCategoryList().get(position).getId()));
        }

    }

    public void replaceFragment(Fragment fragment, String categoryId) {
        Log.d("CATEGORY_ID=", categoryId);
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", categoryId);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
