package com.imakancustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProviderDetailsPojo {

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

        @SerializedName("images")
        @Expose
        private Object images;
        @SerializedName("totalReview")
        @Expose
        private Integer totalReview;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("country_code")
        @Expose
        private String countryCode;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("likeUnlike")
        @Expose
        private Integer likeUnlike;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("serviceName")
        @Expose
        private String serviceName;
        @SerializedName("subCategoryName")
        @Expose
        private String subCategoryName;
        @SerializedName("subCategoryIds")
        @Expose
        private String subCategoryIds;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("gallery")
        @Expose
        private List<Object> gallery = null;

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public Integer getTotalReview() {
            return totalReview;
        }

        public void setTotalReview(Integer totalReview) {
            this.totalReview = totalReview;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getLikeUnlike() {
            return likeUnlike;
        }

        public void setLikeUnlike(Integer likeUnlike) {
            this.likeUnlike = likeUnlike;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getSubCategoryIds() {
            return subCategoryIds;
        }

        public void setSubCategoryIds(String subCategoryIds) {
            this.subCategoryIds = subCategoryIds;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<Object> getGallery() {
            return gallery;
        }

        public void setGallery(List<Object> gallery) {
            this.gallery = gallery;
        }

    }
}
