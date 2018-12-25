package com.imakancustomer.ui.category_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemSelectedListener;
import com.imakancustomer.model.CategoryListPojo;
import com.imakancustomer.model.CategoryListPojo.Datum;
import com.imakancustomer.ui.notification.NotificationAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private List<CategoryListPojo.CategoryList> mList;
    private Context mContext;
    private OnItemSelectedListener itemSelectedListener;

    public CategoryListAdapter(List<CategoryListPojo.CategoryList> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void OnItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category_list, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryListPojo.CategoryList pojo = mList.get(position);
        holder.tv_cl_name.setText(pojo.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cl_name)
        TextView tv_cl_name;
        @BindView(R.id.rl_categories)
        RelativeLayout rl_categories;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rl_categories.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                //check if item exist
                if (pos != RecyclerView.NO_POSITION) {
                    itemSelectedListener.onItemClick(pos, "CATEGORY_CLICKED");
                }
            });
        }
    }

}
