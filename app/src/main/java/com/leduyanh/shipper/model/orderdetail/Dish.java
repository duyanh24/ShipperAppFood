package com.leduyanh.shipper.model.orderdetail;

import com.google.gson.annotations.SerializedName;

public class Dish {
    @SerializedName("name")
    String name;

    @SerializedName("price")
    int price;

    @SerializedName("sale")
    int sale;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}
