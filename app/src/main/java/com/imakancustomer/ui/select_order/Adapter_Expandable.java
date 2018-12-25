package com.imakancustomer.ui.select_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.imakancustomer.R;
import com.imakancustomer.core.listeners.OnItemAddedListener;
import com.imakancustomer.model.ServiceListPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Adapter_Expandable extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    ArrayList<ArrayList<ServiceListPojo>> serviceslistitemsPojos;
    private ArrayList<String> arrayList;
    private TextView totalprice;
    private ArrayList<HashMap<String, String>> arr_hash;
    private int total_price;
    OnItemAddedListener onItemAddedListener;

    public Adapter_Expandable(Context context, List<String> listDataHeader, ArrayList<ArrayList<ServiceListPojo>> serviceslistitemsPojos, OnItemAddedListener onItemAddedListener) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.serviceslistitemsPojos = serviceslistitemsPojos;
        this.totalprice = totalprice;
        arrayList = new ArrayList<>();
        arr_hash = new ArrayList<>();
        this.onItemAddedListener = onItemAddedListener;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return serviceslistitemsPojos.get(groupPosition).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ServiceListPojo serviceDetail = (ServiceListPojo) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_parent_list, null);
        }
        TextView txtListChild = convertView.findViewById(R.id.lblListHeader);
        TextView mTvCount = convertView.findViewById(R.id.tv_itemcount);
        mTvCount.setText(String.valueOf(serviceslistitemsPojos.get(groupPosition).get(childPosition).getCount()));

        final TextView txt_price = convertView.findViewById(R.id.txt_price);
        ImageView mIvPlus = convertView.findViewById(R.id.ib_plus);
        ImageView mIvMinus = convertView.findViewById(R.id.ib_minus);
        ImageView mIvQty = convertView.findViewById(R.id.iv_plus_qty);


        mIvQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (serviceslistitemsPojos.get(groupPosition).get(childPosition).getCount() == 0) {
                    addItem(groupPosition, childPosition);
                    mTvAdd.setText("ADDED");
                    mIvQty.setBackground(_context.getResources().getDrawable(R.drawable.ic_remove));
                } else if (serviceslistitemsPojos.get(groupPosition).get(childPosition).getCount() > 0) {
                    reduceItem(groupPosition, childPosition);
                    mTvAdd.setText("ADD");
                    mIvQty.setBackground(_context.getResources().getDrawable(R.drawable.ic_add));
                }*/
            }
        });


        mIvMinus.setOnClickListener(view -> {
            if (serviceslistitemsPojos.get(groupPosition).get(childPosition).getCount() > 0) {
                reduceItem(groupPosition, childPosition);
            }
        });


        mIvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(groupPosition, childPosition);
            }
        });

        txtListChild.setText(serviceDetail.getName());
        //txt_price.setText(GlobalSingleton.getInstance().currencysymbol + " " + GlobalSingleton.getInstance().changefomat(Integer.valueOf(serviceDetail.getPrice())));
        txt_price.setText(serviceDetail.getPrice());
        return convertView;
    }

    private void reduceItem(int groupPosition, int childPosition) {
        total_price = total_price - Integer.parseInt(serviceslistitemsPojos.get(groupPosition).get(childPosition).getPrice());
        onItemAddedListener.onItemAdded(total_price + "", serviceslistitemsPojos.get(childPosition));
        serviceslistitemsPojos.get(groupPosition).get(childPosition).setCount(serviceslistitemsPojos.get(groupPosition).get(childPosition).getCount() - 1);
        notifyDataSetChanged();
    }

    private void addItem(int groupPosition, int childPosition) {
        total_price = total_price + Integer.parseInt(serviceslistitemsPojos.get(groupPosition).get(childPosition).getPrice());
        onItemAddedListener.onItemAdded(total_price + "", serviceslistitemsPojos.get(childPosition));
        serviceslistitemsPojos.get(groupPosition).get(childPosition).setCount(serviceslistitemsPojos.get(groupPosition).get(childPosition).getCount() + 1);
        notifyDataSetChanged();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return serviceslistitemsPojos.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_child_list, null);
        }
        TextView lblListHeader = convertView.findViewById(R.id.lblListItem);
        lblListHeader.setText(headerTitle);
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}