package com.imakancustomer.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemSelectedListener;
import com.imakancustomer.model.DashboardPojo;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<DashboardPojo.RecentBooking> mList;
    private Context mContext;
    private OnItemSelectedListener itemSelectedListener;

    public DashboardAdapter(List<DashboardPojo.RecentBooking> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void OnItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recent_booking, parent, false);
        return new ViewHolder(rowView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardPojo.RecentBooking pojo = mList.get(position);
        holder.tv_rb_provider_name.setText(pojo.getName());
        holder.tv_rb_booking_id.setText("Order #" + pojo.getToken());
        holder.tv_rb_booking_time.setText(pojo.getBookingDatetime());
        //holder.tv_orderdetail_orderdetail.setText(pojo.getJobStatus());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rb_provider_name)
        TextView tv_rb_provider_name;
        @BindView(R.id.tv_rb_booking_time)
        TextView tv_rb_booking_time;
        @BindView(R.id.tv_rb_booking_id)
        TextView tv_rb_booking_id;
        @BindView(R.id.tv_rb_booking_details)
        TextView tv_rb_booking_details;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            /*ll_orderlist.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                //check if item exist
                if (pos != RecyclerView.NO_POSITION) {
                    NotificationPojo.Datum data = mList.get(pos);
                    itemSelectedListener.onItemClick(pos, "TEST");
                }
            });*/
        }
    }

}

