package com.imakancustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryListPojo {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("recentBooking")
        @Expose
        private List<Object> recentBooking = null;
        @SerializedName("categoryList")
        @Expose
        private List<CategoryList> categoryList = null;

        public List<Object> getRecentBooking() {
            return recentBooking;
        }

        public void setRecentBooking(List<Object> recentBooking) {
            this.recentBooking = recentBooking;
        }

        public List<CategoryList> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<CategoryList> categoryList) {
            this.categoryList = categoryList;
        }

    }

    public class CategoryList {

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("id")
        @Expose
        private Integer id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }

}
