package com.leduyanh.shipper.model.order;

import com.google.gson.annotations.SerializedName;

public class DataOrder {
    @SerializedName("id")
    int id;
    @SerializedName("address")
    String address;

    @SerializedName("time")
    String time;

    @SerializedName("status")
    int status;

    @SerializedName("user")
    User user;

    @SerializedName("store")
    Store store;

    @SerializedName("totalPrice")
    int totalPrice;

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
