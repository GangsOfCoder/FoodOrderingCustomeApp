package com.imakancustomer.core.listeners;


import com.imakancustomer.model.ServiceListPojo;

import java.util.ArrayList;

public interface OnItemAddedListener {

    void onItemAdded(String totalamount, ArrayList<ServiceListPojo> serviceListPojos);

    void onItemAdded(String totalamount, ServiceListPojo serviceListPojo);

}
