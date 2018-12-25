package com.imakancustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingListPojo {

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

        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("is_delivery_accepted")
        @Expose
        private Integer isDeliveryAccepted;
        @SerializedName("is_provider_accepted")
        @Expose
        private Integer isProviderAccepted;
        @SerializedName("completed_provider_approval")
        @Expose
        private String completedProviderApproval;
        @SerializedName("completed_customer_approval")
        @Expose
        private String completedCustomerApproval;
        @SerializedName("booking_datetime")
        @Expose
        private String bookingDatetime;
        @SerializedName("end_utc_time")
        @Expose
        private String endUtcTime;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("job_status")
        @Expose
        private String jobStatus;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Integer getIsDeliveryAccepted() {
            return isDeliveryAccepted;
        }

        public void setIsDeliveryAccepted(Integer isDeliveryAccepted) {
            this.isDeliveryAccepted = isDeliveryAccepted;
        }

        public Integer getIsProviderAccepted() {
            return isProviderAccepted;
        }

        public void setIsProviderAccepted(Integer isProviderAccepted) {
            this.isProviderAccepted = isProviderAccepted;
        }

        public String getCompletedProviderApproval() {
            return completedProviderApproval;
        }

        public void setCompletedProviderApproval(String completedProviderApproval) {
            this.completedProviderApproval = completedProviderApproval;
        }

        public String getCompletedCustomerApproval() {
            return completedCustomerApproval;
        }

        public void setCompletedCustomerApproval(String completedCustomerApproval) {
            this.completedCustomerApproval = completedCustomerApproval;
        }

        public String getBookingDatetime() {
            return bookingDatetime;
        }

        public void setBookingDatetime(String bookingDatetime) {
            this.bookingDatetime = bookingDatetime;
        }

        public String getEndUtcTime() {
            return endUtcTime;
        }

        public void setEndUtcTime(String endUtcTime) {
            this.endUtcTime = endUtcTime;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getJobStatus() {
            return jobStatus;
        }

        public void setJobStatus(String jobStatus) {
            this.jobStatus = jobStatus;
        }

    }
}
