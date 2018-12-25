package com.imakancustomer.ui.order;

import android.annotation.SuppressLint;
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
import com.imakancustomer.model.BookingListPojo;
import com.imakancustomer.model.NotificationPojo;
import com.imakancustomer.ui.category_list.CategoryListAdapter;
import com.imakancustomer.utils.Function;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<BookingListPojo.Datum> mList;
    private Context mContext;
    private OnItemSelectedListener itemSelectedListener;

    public OrderAdapter(List<BookingListPojo.Datum> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void OnItemSelectedListener(OnItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_list, parent, false);
        return new ViewHolder(rowView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingListPojo.Datum pojo = mList.get(position);
        holder.tv_orderlist_orderid.setText("Order  #" + pojo.getToken());
        holder.tv_orderlist_orderdate.setText(Function.getMonthFromDate(pojo.getBookingDatetime()) + "  " + Function.getDayFromDate(pojo.getBookingDatetime()));
        holder.tv_orderdetail_orderdetail.setText(pojo.getJobStatus());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_orderlist_orderid)
        TextView tv_orderlist_orderid;
        @BindView(R.id.tv_orderlist_orderdate)
        TextView tv_orderlist_orderdate;
        @BindView(R.id.tv_orderdetail_orderdetail)
        TextView tv_orderdetail_orderdetail;
        @BindView(R.id.ll_orderlist)
        LinearLayout ll_orderlist;

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
