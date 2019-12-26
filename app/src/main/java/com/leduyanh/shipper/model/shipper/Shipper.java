package com.leduyanh.shipper.model.shipper;

import com.google.gson.annotations.SerializedName;

public class Shipper {
    String name;

    @SerializedName("success")
    Boolean success;

    @SerializedName("token")
    String Token;

    @SerializedName("data")
    DataShipper data;

    public DataShipper getData() {
        return data;
    }

    public void setData(DataShipper data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
