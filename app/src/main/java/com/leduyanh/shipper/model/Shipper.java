package com.leduyanh.shipper.model;

import com.google.gson.annotations.SerializedName;

public class Shipper {
    String name;

    @SerializedName("success")
    Boolean success;

    @SerializedName("token")
    String Token;

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
