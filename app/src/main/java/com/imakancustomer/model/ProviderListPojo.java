package com.imakancustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProviderListPojo {

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

        @SerializedName("totalReview")
        @Expose
        private Integer totalReview;
        @SerializedName("likeUnlike")
        @Expose
        private Integer likeUnlike;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("avg_rating")
        @Expose
        private Integer avgRating;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;
        @SerializedName("distance")
        @Expose
        private Double distance;

        public Integer getTotalReview() {
            return totalReview;
        }

        public void setTotalReview(Integer totalReview) {
            this.totalReview = totalReview;
        }

        public Integer getLikeUnlike() {
            return likeUnlike;
        }

        public void setLikeUnlike(Integer likeUnlike) {
            this.likeUnlike = likeUnlike;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public Integer getAvgRating() {
            return avgRating;
        }

        public void setAvgRating(Integer avgRating) {
            this.avgRating = avgRating;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

    }
}
