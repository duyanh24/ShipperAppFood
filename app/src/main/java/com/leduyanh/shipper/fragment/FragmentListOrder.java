package com.leduyanh.shipper.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leduyanh.shipper.R;
import com.leduyanh.shipper.adapter.RecyclerViewOrderItem;
import com.leduyanh.shipper.api.RetrofitClient;
import com.leduyanh.shipper.model.order.DataOrder;
import com.leduyanh.shipper.model.order.Order;
import com.leduyanh.shipper.api.SOSerivce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListOrder extends Fragment {

    RecyclerView recyclerListOder;
    RecyclerViewOrderItem recyclerViewOrderItemAdapter;
    SOSerivce mSoSerivce;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_oder,container,false);
        mSoSerivce = RetrofitClient.getClient().create(SOSerivce.class);

        recyclerListOder = (RecyclerView)view.findViewById(R.id.recyclerListOrder);
        getApiListOrder();

        return view;
    }

    public void getApiListOrder(){
        SharedPreferences tokenCache = getActivity().getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
        String token = tokenCache.getString("token","");
        int idShipper = Integer.parseInt(tokenCache.getString("id",""));

        mSoSerivce.getOrderShipper(token,idShipper).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Order order = response.body();
                if(order != null){
                    if (order.getSuccess()){
                        List<DataOrder> listDataOrder = order.getData();
                        recyclerViewOrderItemAdapter = new RecyclerViewOrderItem(getActivity(),listDataOrder);
                        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager =
                                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                        recyclerListOder.setAdapter(recyclerViewOrderItemAdapter);
                        recyclerListOder.setLayoutManager(linearLayoutManager);
                    }
                    else {
                        Toast.makeText(getActivity(),"Chưa có đơn hàng nào!",Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(getActivity(),"Chưa có đơn hàng nào!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
//        mSoSerivce.getOrderShipper().enqueue(new Callback<List<Order>>() {
//            @Override
//            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//                List<Order> listOrder = response.body();
//                recyclerViewOrderItemAdapter = new RecyclerViewOrderItem(getActivity(),listOrder);
//                @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager =
//                        new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//                recyclerListOder.setAdapter(recyclerViewOrderItemAdapter);
//                recyclerListOder.setLayoutManager(linearLayoutManager);
//            }
//
//            @Override
//            public void onFailure(Call<List<Order>> call, Throwable t) {
//                Toast.makeText(getActivity(),"Lỗi kết nối",Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
