package com.leduyanh.shipper.model.order;

import com.google.gson.annotations.SerializedName;

public class Store {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
