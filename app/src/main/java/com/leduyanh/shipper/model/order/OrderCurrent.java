package com.leduyanh.shipper.model.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderCurrent {
    @SerializedName("success")
    Boolean success;

    @SerializedName("data")
    DataOrderCurrent data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public DataOrderCurrent getData() {
        return data;
    }

    public void setData(DataOrderCurrent data) {
        this.data = data;
    }
}
