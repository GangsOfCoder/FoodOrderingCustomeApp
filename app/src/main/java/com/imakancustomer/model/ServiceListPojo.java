package com.imakancustomer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceListPojo implements Parcelable {
    private String sub_cat_name;
    private String sub_category_id;
    private String type_id;
    private String name;
    private String description;
    private String price;
    private String id;
    private String duration;
    private String image;
    private int count = 0;


    public ServiceListPojo(Parcel in) {
        sub_cat_name = in.readString();
        sub_category_id = in.readString();
        type_id = in.readString();
        name = in.readString();
        id = in.readString();
        description = in.readString();
        duration = in.readString();
        image = in.readString();
        count = in.readInt();
        price = in.readString();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static final Creator<ServiceListPojo> CREATOR = new Creator<ServiceListPojo>() {
        @Override
        public ServiceListPojo createFromParcel(Parcel in) {
            return new ServiceListPojo(in);
        }

        @Override
        public ServiceListPojo[] newArray(int size) {
            return new ServiceListPojo[size];
        }
    };

    public ServiceListPojo(String sub_cat_name, String sub_category_id, String type_id, String name, String description, String price, String id, String duration, String image) {
        this.sub_cat_name = sub_cat_name;
        this.sub_category_id = sub_category_id;
        this.type_id = type_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
        this.duration = duration;
        this.image = image;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sub_cat_name);
        parcel.writeString(sub_category_id);
        parcel.writeString(type_id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(price);
        parcel.writeString(id);
        parcel.writeString(duration);
        parcel.writeString(image);
        parcel.writeInt(count);
    }


    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
