package com.imakancustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardPojo {
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
        private List<RecentBooking> recentBooking = null;
        @SerializedName("completeBookings")
        @Expose
        private List<CompleteBooking> completeBookings = null;
        @SerializedName("totalFavourites")
        @Expose
        private List<TotalFavourite> totalFavourites = null;

        public List<RecentBooking> getRecentBooking() {
            return recentBooking;
        }

        public void setRecentBooking(List<RecentBooking> recentBooking) {
            this.recentBooking = recentBooking;
        }

        public List<CompleteBooking> getCompleteBookings() {
            return completeBookings;
        }

        public void setCompleteBookings(List<CompleteBooking> completeBookings) {
            this.completeBookings = completeBookings;
        }

        public List<TotalFavourite> getTotalFavourites() {
            return totalFavourites;
        }

        public void setTotalFavourites(List<TotalFavourite> totalFavourites) {
            this.totalFavourites = totalFavourites;
        }

    }

    public class RecentBooking {

        @SerializedName("customer_request")
        @Expose
        private String customerRequest;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("booking_datetime")
        @Expose
        private String bookingDatetime;
        @SerializedName("job_status")
        @Expose
        private String jobStatus;

        public String getCustomerRequest() {
            return customerRequest;
        }

        public void setCustomerRequest(String customerRequest) {
            this.customerRequest = customerRequest;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getBookingDatetime() {
            return bookingDatetime;
        }

        public void setBookingDatetime(String bookingDatetime) {
            this.bookingDatetime = bookingDatetime;
        }

        public String getJobStatus() {
            return jobStatus;
        }

        public void setJobStatus(String jobStatus) {
            this.jobStatus = jobStatus;
        }

    }

    public class TotalFavourite {

        @SerializedName("totalFavouriteCount")
        @Expose
        private Integer totalFavouriteCount;

        public Integer getTotalFavouriteCount() {
            return totalFavouriteCount;
        }

        public void setTotalFavouriteCount(Integer totalFavouriteCount) {
            this.totalFavouriteCount = totalFavouriteCount;
        }
    }

    public class CompleteBooking {

        @SerializedName("completeRequest")
        @Expose
        private Integer completeRequest;

        public Integer getCompleteRequest() {
            return completeRequest;
        }

        public void setCompleteRequest(Integer completeRequest) {
            this.completeRequest = completeRequest;
        }

    }
}
