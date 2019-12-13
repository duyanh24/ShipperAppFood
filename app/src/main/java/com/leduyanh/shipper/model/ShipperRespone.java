package com.leduyanh.shipper.model;

import com.google.gson.annotations.SerializedName;

public class ShipperRespone {

    @SerializedName("success")
    Boolean success;

    @SerializedName("data")
    Data data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

