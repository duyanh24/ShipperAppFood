package com.leduyanh.shipper.model.order;

import com.google.gson.annotations.SerializedName;
import com.leduyanh.shipper.model.order.DataOrder;

import java.util.List;

public class Order {
    @SerializedName("success")
    Boolean success;

    @SerializedName("data")
    List<DataOrder> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<DataOrder> getData() {
        return data;
    }

    public void setData(List<DataOrder> data) {
        this.data = data;
    }
}
