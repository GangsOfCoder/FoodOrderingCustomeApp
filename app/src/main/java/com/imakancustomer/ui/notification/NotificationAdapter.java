package com.imakancustomer.ui.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemSelectedListener;
import com.imakancustomer.model.NotificationPojo;
import com.imakancustomer.utils.Function;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationPojo.Datum> mList;
    private Context mContext;
    private OnItemSelectedListener itemSelectedListener;

    public NotificationAdapter(List<NotificationPojo.Datum> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void OnItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_notification, parent, false);
        return new ViewHolder(rowView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationPojo.Datum pojo = mList.get(position);
        holder.tv_notification_orderstatus.setText(pojo.getName());
        holder.tv_notification_orderDate.setText(Function.getMonthFromDate(pojo.getCreatedAt()) + " " + Function.getDayFromDate(pojo.getCreatedAt()));
        holder.tv_notification_orderdetail.setText(pojo.getMessage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_notification_orderstatus)
        TextView tv_notification_orderstatus;
        @BindView(R.id.tv_notification_orderDate)
        TextView tv_notification_orderDate;
        @BindView(R.id.tv_notification_orderdetail)
        TextView tv_notification_orderdetail;
        @BindView(R.id.ll_notification)
        LinearLayout ll_notification;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ll_notification.setOnClickListener(v -> {
                /*int pos = getAdapterPosition();
                //check if item exist
                if (pos != RecyclerView.NO_POSITION) {
                    NotificationPojo.Datum data = mList.get(pos);
                    itemSelectedListener.onItemClick(pos, "TEST");
                }*/
            });
        }
    }
}
