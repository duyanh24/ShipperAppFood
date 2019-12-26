package com.leduyanh.shipper.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leduyanh.shipper.activity.DirectionActivity;
import com.leduyanh.shipper.R;
import com.leduyanh.shipper.activity.HomeActivity;
import com.leduyanh.shipper.activity.LogInActivity;
import com.leduyanh.shipper.activity.OrderdetailActivity;
import com.leduyanh.shipper.adapter.AdapterRecycOrderDetail;
import com.leduyanh.shipper.adapter.RecyclerViewOrderItem;
import com.leduyanh.shipper.api.RetrofitClient;
import com.leduyanh.shipper.api.RetrofitClientTest;
import com.leduyanh.shipper.api.SOSerivce;
import com.leduyanh.shipper.model.order.Order;
import com.leduyanh.shipper.model.orderdetail.DataOrderDetail;
import com.leduyanh.shipper.model.orderdetail.OrderDetail;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCurrentOrder extends Fragment implements AdapterView.OnClickListener {

    View view;

    RecyclerView recyclerListItem;
    RecyclerViewOrderItem recyclerViewOrderItemAdapter;

    Button btnDirection, btnCall,btnDone,btnCancelOrder;
    TextView txtOderCurrentRestau,txtOderCurrentCustomer;

    SOSerivce mSoSerivce;

    String addressStore;
    String getAddressUser;

    int idOrder;


    public void setAddressStore(String addressStore) {
        this.addressStore = addressStore;
    }

    public void setGetAddressUser(String getAddressUser) {
        this.getAddressUser = getAddressUser;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_current,container,false);

        init();
        mSoSerivce = RetrofitClient.getClient().create(SOSerivce.class);

        setInfoOrder();

        btnDirection.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnCancelOrder.setOnClickListener(this);

        return view;
    }

    private void init() {
        recyclerListItem = (RecyclerView)view.findViewById(R.id.recycOderCurrent);
        btnDirection = (Button)view.findViewById(R.id.btnOrderCurrentDirection);
        btnCall = (Button)view.findViewById(R.id.btnOrderCurrentCall);
        btnDone = (Button)view.findViewById(R.id.btnDone);
        txtOderCurrentRestau = (TextView)view.findViewById(R.id.txtOderCurrentRestau);
        txtOderCurrentCustomer = (TextView)view.findViewById(R.id.txtOderCurrentCustomer);
        btnCancelOrder = (Button)view.findViewById(R.id.btnCancelOrder);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOrderCurrentDirection:
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }else {
                    Intent intentDirectionActivity = new Intent(getActivity(), DirectionActivity.class);
                    startActivity(intentDirectionActivity);
                }

                break;
            case R.id.btnOrderCurrentCall:

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                }else{
                    Uri uri = Uri.parse("tel:"+"2313132154");
                    Intent i = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(i);
                }
                break;
            case R.id.btnDone:
                SharedPreferences tokenCache = getActivity().getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
                String token = tokenCache.getString("token","");

                mSoSerivce.updateStatusOrder(token,idOrder,2).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        ((HomeActivity)getContext()).doneOrder();
                    }
                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Toast.makeText(getActivity(),"Lỗi kết nối update status Order",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btnCancelOrder:
                showDialogCanCelOrder();
                break;
        }
    }

    public void setInfoOrder(){
        txtOderCurrentRestau.setText(addressStore);
        txtOderCurrentCustomer.setText(getAddressUser);

        SharedPreferences tokenCache = getActivity().getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
        String token = tokenCache.getString("token","");

        mSoSerivce.getOrderById(token,idOrder).enqueue(new Callback<OrderDetail>() {
            @Override
            public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                OrderDetail orderDetails = response.body();
                List<DataOrderDetail> listDish = orderDetails.getData();
                if(listDish.size() != 0){
                    AdapterRecycOrderDetail adapterRecycOrderDetail = new AdapterRecycOrderDetail(listDish, getActivity());
                    @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager =
                            new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                    recyclerListItem.setAdapter(adapterRecycOrderDetail);
                    recyclerListItem.setLayoutManager(linearLayoutManager);
                }
            }

            @Override
            public void onFailure(Call<OrderDetail> call, Throwable t) {
                Toast.makeText(getActivity(),"Lỗi kết nối!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialogCanCelOrder(){
        AlertDialog.Builder builderMessage = new AlertDialog.Builder(getActivity());

        builderMessage.setTitle("Bạn có muốn hủy đơn hàng?")
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getApiCancelOrder();
                    }
                });
        builderMessage.create().show();
    }

    public void getApiCancelOrder(){
        SharedPreferences tokenCache = getActivity().getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
        String token = tokenCache.getString("token","");
        mSoSerivce.updateStatusOrder(token,idOrder,3).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                ((HomeActivity)getContext()).doneOrder();
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(getActivity(),"Lỗi kết nối update status Order hủy đơn hàng",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
