package com.leduyanh.shipper.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leduyanh.shipper.R;
import com.leduyanh.shipper.adapter.AdapterRecycOrderDetail;
import com.leduyanh.shipper.api.RetrofitClient;
import com.leduyanh.shipper.api.SOSerivce;
import com.leduyanh.shipper.model.orderdetail.DataOrderDetail;
import com.leduyanh.shipper.model.orderdetail.OrderDetail;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderdetailActivity extends AppCompatActivity {

    RecyclerView recycOrderDetail;
    TextView txtOderDetailRestau,txtOderDetailCustomer,txtTotalPrice;

    int orderId;
    String infoUser;
    String infoStore;

    SOSerivce mSoSerivce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_detail);

        recycOrderDetail = (RecyclerView)findViewById(R.id.recyceOderDetail);
        txtOderDetailRestau = (TextView)findViewById(R.id.txtOderDetailRestau);
        txtOderDetailCustomer = (TextView)findViewById(R.id.txtOderDetailCustomer);
        txtTotalPrice = (TextView)findViewById(R.id.txtTotalPrice);

        mSoSerivce = RetrofitClient.getClient().create(SOSerivce.class);

        Intent intent = getIntent();
        orderId = Integer.parseInt(intent.getStringExtra("order_id"));
        infoUser = intent.getStringExtra("infoUser");
        infoStore = intent.getStringExtra("infoStore");

        txtOderDetailRestau.setText(infoStore);
        txtOderDetailCustomer.setText(infoUser);

        getDataOrderDetail();

    }

    public void getDataOrderDetail(){
        SharedPreferences tokenCache = this.getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
        final String token = tokenCache.getString("token","");

        mSoSerivce.getOrderById(token,orderId).enqueue(new Callback<OrderDetail>() {
            @Override
            public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                OrderDetail orderDetails = response.body();
                List<DataOrderDetail> listDish = orderDetails.getData();
                if(listDish.size() != 0){
                    int totalPrice = 0;
                    for(DataOrderDetail item:listDish){
                        totalPrice+= item.getCurrent_price()*item.getQuantity();
                    }
                    NumberFormat formatter = NumberFormat.getCurrencyInstance();
                    String moneyString = formatter.format(totalPrice);

                    txtTotalPrice.setText(moneyString+" VNĐ");
                    AdapterRecycOrderDetail adapterRecycOrderDetail = new AdapterRecycOrderDetail(listDish, OrderdetailActivity.this);
                    @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager =
                            new LinearLayoutManager(OrderdetailActivity.this,LinearLayoutManager.VERTICAL,false);
                    recycOrderDetail.setAdapter(adapterRecycOrderDetail);
                    recycOrderDetail.setLayoutManager(linearLayoutManager);
                }
            }

            @Override
            public void onFailure(Call<OrderDetail> call, Throwable t) {
                Toast.makeText(OrderdetailActivity.this,"Lỗi kết nối",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
