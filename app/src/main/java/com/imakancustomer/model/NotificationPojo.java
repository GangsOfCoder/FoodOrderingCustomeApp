package com.imakancustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationPojo {

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

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("sms_status")
        @Expose
        private String smsStatus;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("is_read")
        @Expose
        private Integer isRead;
        @SerializedName("type_id")
        @Expose
        private Integer typeId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("model_type")
        @Expose
        private String modelType;
        @SerializedName("model_id")
        @Expose
        private Integer modelId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getSmsStatus() {
            return smsStatus;
        }

        public void setSmsStatus(String smsStatus) {
            this.smsStatus = smsStatus;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getIsRead() {
            return isRead;
        }

        public void setIsRead(Integer isRead) {
            this.isRead = isRead;
        }

        public Integer getTypeId() {
            return typeId;
        }

        public void setTypeId(Integer typeId) {
            this.typeId = typeId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getModelType() {
            return modelType;
        }

        public void setModelType(String modelType) {
            this.modelType = modelType;
        }

        public Integer getModelId() {
            return modelId;
        }

        public void setModelId(Integer modelId) {
            this.modelId = modelId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}
