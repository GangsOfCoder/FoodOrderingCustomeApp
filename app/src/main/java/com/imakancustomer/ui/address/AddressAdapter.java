package com.imakancustomer.ui.address;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnServiceItemSelectedListener;
import com.imakancustomer.model.AddressListPojo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressListPojo.Datum> mList;
    private Context mContext;
    private OnServiceItemSelectedListener selectedListener;

    public AddressAdapter(List<AddressListPojo.Datum> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void OnServiceItemSelectedListener(OnServiceItemSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_address_list, parent, false);
        return new ViewHolder(rowView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            AddressListPojo.Datum pojo = mList.get(position);
            holder.tv_al_namenumber.setText(pojo.getName() + "-" + pojo.getContact());
            if (pojo.getStatus().toString().equalsIgnoreCase("1")) {
                //INACTIVE
                holder.rb_al_tick.setChecked(true);
            } else {
                //ACTIVE
                holder.rb_al_tick.setChecked(false);
            }
            holder.tv_al_address.setText(pojo.getAddress());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    /**
     * View holder for recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_al_namenumber)
        TextView tv_al_namenumber;
        @BindView(R.id.tv_al_address)
        TextView tv_al_address;
        @BindView(R.id.img_al_delete)
        ImageView img_al_delete;
        @BindView(R.id.rb_al_tick)
        RadioButton rb_al_tick;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            //ON DELETE CLICKED
            img_al_delete.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (selectedListener != null) {
                    selectedListener.onItemClick(pos, "delete", String.valueOf(mList.get(pos).getId()));
                }
            });

            //ON SELECT PRIMARY ADDRESS
            rb_al_tick.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (selectedListener != null) {
                    selectedListener.onItemClick(pos, "selected", String.valueOf(mList.get(pos).getId()));
                }
            });

        }
    }

}
