package com.imakancustomer.ui.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemSelectedListener;
import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.model.ProviderListPojo;
import com.imakancustomer.ui.category_list.CategoryListAdapter;
import com.imakancustomer.utils.Function;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProviderListAdapter extends RecyclerView.Adapter<ProviderListAdapter.ViewHolder> {

    private List<ProviderListPojo.Datum> mList;
    private Context mContext;
    private OnItemSelectedListener itemSelectedListener;

    public ProviderListAdapter(List<ProviderListPojo.Datum> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void OnItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_result_list, parent, false);
        return new ViewHolder(rowView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            ProviderListPojo.Datum pojo = mList.get(position);
            Function.setCircularImageView(holder.img_rl_image, pojo.getImage(), mContext, R.drawable.dummyuser);
            holder.tv_rl_name.setText(pojo.getFirstName() + " " + pojo.getLastName());
            holder.rb_rl_rating.setRating((float) pojo.getAvgRating());
            holder.tv_rl_review.setText(pojo.getTotalReview().toString());
            holder.tv_rl_location.setText(pojo.getAddress());
        } catch (Exception e) {
            Toast.makeText(mContext, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_rl_image)
        ImageView img_rl_image;
        @BindView(R.id.tv_rl_name)
        TextView tv_rl_name;
        @BindView(R.id.rb_rl_rating)
        RatingBar rb_rl_rating;
        @BindView(R.id.tv_rl_review)
        TextView tv_rl_review;
        @BindView(R.id.tv_rl_location)
        TextView tv_rl_location;
        @BindView(R.id.rl_providers)
        RelativeLayout rl_providers;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rl_providers.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                //check if item exist
                if (pos != RecyclerView.NO_POSITION) {
                    itemSelectedListener.onItemClick(pos, "CATEGORY_CLICKED");
                }
            });
        }
    }

}

