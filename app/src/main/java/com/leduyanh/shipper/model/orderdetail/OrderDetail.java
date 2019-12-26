package com.leduyanh.shipper.model.orderdetail;

import com.google.gson.annotations.SerializedName;
import com.leduyanh.shipper.model.order.DataOrder;

import java.util.List;

public class OrderDetail {
    @SerializedName("success")
    Boolean success;

    @SerializedName("data")
    List<DataOrderDetail> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<DataOrderDetail> getData() {
        return data;
    }

    public void setData(List<DataOrderDetail> data) {
        this.data = data;
    }
}
