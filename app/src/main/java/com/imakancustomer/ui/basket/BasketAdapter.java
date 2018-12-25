package com.imakancustomer.ui.basket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemSelectedListener;
import com.imakancustomer.model.ServiceListPojo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private List<ServiceListPojo> mList;
    private Context mContext;
    private OnItemSelectedListener itemSelectedListener;

    public BasketAdapter(Context mContext, List<ServiceListPojo> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void OnItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_basket_item, parent, false);
        return new ViewHolder(rowView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tv_bi_item_name.setText(mList.get(position).getName());
            holder.tv_bi_item_price.setText("(RM " + mList.get(position).getPrice() + ")");
            holder.tv_bi_item_count.setText(String.valueOf(mList.get(position).getCount()));

            Float mTotalPricePerItem = Float.parseFloat(mList.get(position).getPrice()) * mList.get(position).getCount();
            holder.tv_bi_item_total_price.setText("RM " + mTotalPricePerItem);
        } catch (Exception e) {
            Toast.makeText(mContext, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_bi_item_name)
        TextView tv_bi_item_name;
        @BindView(R.id.tv_bi_item_price)
        TextView tv_bi_item_price;
        @BindView(R.id.tv_bi_item_count)
        TextView tv_bi_item_count;
        @BindView(R.id.tv_bi_item_total_price)
        TextView tv_bi_item_total_price;
        @BindView(R.id.tv_bi_item_delete)
        ImageView tv_bi_item_delete;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tv_bi_item_delete.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                //check if item exist
                if (pos != RecyclerView.NO_POSITION) {
                    itemSelectedListener.onItemClick(pos, "DELETE");
                    removeAt(pos);
                }
            });
        }
    }

    /**
     * Method to remove item from list
     *
     * @param position hold the position of item to remove
     */
    private void removeAt(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size());
    }

}

