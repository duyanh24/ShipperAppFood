package com.leduyanh.shipper.api;

import com.leduyanh.shipper.model.order.Order;
import com.leduyanh.shipper.model.order.OrderCurrent;
import com.leduyanh.shipper.model.orderdetail.OrderDetail;
import com.leduyanh.shipper.model.shipper.Shipper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface SOSerivce {

    @POST("shipper/login")
    @FormUrlEncoded
    Call<Shipper> logIn(@Field("email") String email,@Field("password") String password);

    @GET("shipper/{id}")
    Call<Shipper> getInfoShipper(@Header("Authorization") String authorization,@Path("id") int id);

    @GET("order/shipper/{id}")
    Call<Order> getOrderShipper(@Header("Authorization") String authorization,@Path("id") int id);

    @GET("orderdetail/{id}")
    Call<OrderDetail> getOrderById(@Header("Authorization") String authorization, @Path("id") int id);

    @GET("neworder/shipper")
    Call<OrderCurrent> getOrderCurrent(@Header("Authorization") String authorization);

    @GET("changeshipper.php")
    Call<List<Order>> changeShipper();

    @GET("doneorder.php")
    Call<Order> doneOrder();

    @PUT("shipper/{id}")
    @FormUrlEncoded
    Call<Shipper> updateOnline (@Header("Authorization") String authorization,@Path("id") int id,@Field("isOnline") int isOnline);

    @PUT("order/{id}")
    @FormUrlEncoded
    Call<Order> updateStatusOrder (@Header("Authorization") String authorization,@Path("id") int id,@Field("status") int status);
}
