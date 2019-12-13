package com.leduyanh.shipper.model.api;

import com.leduyanh.shipper.model.Shipper;
import com.leduyanh.shipper.model.ShipperRespone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface SOSerivce {

    @GET("employees")
    Call<List<Shipper>> checkUser();

    @GET("employee/{id}")
    Call<Shipper> getEmployeeById(@Path("id") int id);

    @POST("shipper/login")
    @FormUrlEncoded
    Call<Shipper> logIn(@Field("email") String email,@Field("password") String password);

    @GET("shipper/{id}")
    Call<ShipperRespone> getInfoShipper(@Header("Authorization") String authorization,@Path("id") int id);

}
