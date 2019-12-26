package com.leduyanh.shipper.model.shipper;

import com.google.gson.annotations.SerializedName;

public class DataShipper {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("phone")
    int phone;
    @SerializedName("identification")
    String identification;
    @SerializedName("address")
    String address;
    @SerializedName("url_image")
    String urlImage;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
