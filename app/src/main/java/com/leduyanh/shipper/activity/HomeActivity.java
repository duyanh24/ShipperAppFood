package com.leduyanh.shipper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leduyanh.shipper.R;
import com.leduyanh.shipper.api.RetrofitClient;
import com.leduyanh.shipper.fragment.FragmentCurrentOrder;
import com.leduyanh.shipper.fragment.FragmentHome;
import com.leduyanh.shipper.fragment.FragmentListOrder;
import com.leduyanh.shipper.fragment.FragmentProfile;
import com.leduyanh.shipper.model.order.Order;
import com.leduyanh.shipper.api.SOSerivce;
import com.leduyanh.shipper.model.order.OrderCurrent;
import com.leduyanh.shipper.model.shipper.Shipper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linearHomeListOrder,linearHome,linearHomeProfile;
    TextView txtMenuHome,txtMenuListOrder,txtMenuProfile;

    FragmentManager fragmentManager = getSupportFragmentManager();

    SOSerivce mSoSerivce;

    public static Boolean CHECK_SHOW_DIALOG = false;
    public static Boolean CHECK_SHOW_FRAGMENT = false;

    String addressStore;
    String addressUser;
    int orderCurrentId;

    String tokenAuth;
    int idCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences tokenCache = HomeActivity.this.getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
        tokenAuth = tokenCache.getString("token","");
        idCache = Integer.parseInt(tokenCache.getString("id",""));

        init();
        mSoSerivce = RetrofitClient.getClient().create(SOSerivce.class);

        FragmentHome fragmentHome = new FragmentHome();
        moveScreen(fragmentHome,"home");

        changeColorMenu(txtMenuHome);

        linearHomeListOrder.setOnClickListener(this);
        linearHome.setOnClickListener(this);
        linearHomeProfile.setOnClickListener(this);

        CHECK_SHOW_DIALOG = false;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                if(!CHECK_SHOW_DIALOG){
                    callApiGetCurrentOrder();

                }
                handler.postDelayed(this, 5000);
            }
        }, 1500);
    }

    private void init() {
        linearHomeListOrder = (LinearLayout)findViewById(R.id.linearHomeListOrder);
        linearHome = (LinearLayout)findViewById(R.id.linearHome);
        linearHomeProfile = (LinearLayout)findViewById(R.id.linearHomeProfile);
        txtMenuHome = (TextView)findViewById(R.id.txtMenuHome);
        txtMenuListOrder = (TextView)findViewById(R.id.txtMenuListOrder);
        txtMenuProfile = (TextView)findViewById(R.id.txtMenuProfile);
    }

    @Override
    protected void onResume() {

        super.onResume();
        updateStatusShipper(1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSoSerivce.updateOnline(tokenAuth,idCache,0).enqueue(new Callback<Shipper>() {
            @Override
            public void onResponse(Call<Shipper> call, Response<Shipper> response) {

            }
            @Override
            public void onFailure(Call<Shipper> call, Throwable t) {
                Toast.makeText(HomeActivity.this,"Lỗi kết nối online",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linearHome:
                if(CHECK_SHOW_FRAGMENT){
                    FragmentCurrentOrder fragmentCurrentOrder = new FragmentCurrentOrder();
                    fragmentCurrentOrder.setAddressStore(addressStore);
                    fragmentCurrentOrder.setGetAddressUser(addressUser);
                    fragmentCurrentOrder.setIdOrder(orderCurrentId);
                    moveScreen(fragmentCurrentOrder,"home");
                }else{
                    FragmentHome fragmentHome = new FragmentHome();
                    moveScreen(fragmentHome,"home");
                }
                changeColorMenu(txtMenuHome);
                break;
            case R.id.linearHomeListOrder:
                FragmentListOrder fragmentListOrder = new FragmentListOrder();
                moveScreen(fragmentListOrder,"listOrder");
                changeColorMenu(txtMenuListOrder);
                break;
            case R.id.linearHomeProfile:
                FragmentProfile fragmentProfile = new FragmentProfile();
                moveScreen(fragmentProfile,"profile");
                changeColorMenu(txtMenuProfile);
                break;
        }
    }

    public void doneOrder(){
        CHECK_SHOW_DIALOG = false;
        CHECK_SHOW_FRAGMENT = false;
        mSoSerivce.doneOrder().enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
            }
        });
        updateStatusShipper(1);
        FragmentHome fragmentHome = new FragmentHome();
        moveScreen(fragmentHome,"home");
    }

    public void logOut(){
        SharedPreferences tokenCache = HomeActivity.this.getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = tokenCache.edit();
        editor.putString("token","");
        editor.putString("id","");
        editor.commit();
        CHECK_SHOW_DIALOG = true;
        Intent intentLogout = new Intent(HomeActivity.this,LogInActivity.class);
        startActivity(intentLogout);
        finish();
    }


    public void callApiGetCurrentOrder(){
        //SharedPreferences tokenCache = this.getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
        //String token = tokenCache.getString("token","");
        mSoSerivce.getOrderCurrent(tokenAuth).enqueue(new Callback<OrderCurrent>() {
            @Override
            public void onResponse(Call<OrderCurrent> call, Response<OrderCurrent> response) {
                OrderCurrent order = response.body();
                if(order.getSuccess()){
                    CHECK_SHOW_DIALOG = true;
                    showMessageNewOrder(order);
                }
            }
            @Override
            public void onFailure(Call<OrderCurrent> call, Throwable t) {
                Toast.makeText(HomeActivity.this,"Lỗi kết nối12",Toast.LENGTH_SHORT).show();
                Log.d("loi",t.getMessage());
            }
        });
    }

    private void showMessageNewOrder(final OrderCurrent order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_receive_order,null);
        final TextView txtOrderCurrentRestau = viewDialog.findViewById(R.id.txtOderDetailRestau);
        final TextView txtOderDetailCustomer = viewDialog.findViewById(R.id.txtOderDetailCustomer);
        final TextView txtDialogTotalPrice = viewDialog.findViewById(R.id.txtDialogTotalPrice);

        addressStore = order.getData().getStore().getName()+", "
                +order.getData().getStore().getAddress();

        addressUser = order.getData().getUser().getName()+", "
                +order.getData().getAddress();
        orderCurrentId = order.getData().getId();

        txtOrderCurrentRestau.setText(addressStore);
        txtOderDetailCustomer.setText(addressUser);
        txtDialogTotalPrice.setText(String.valueOf(order.getData().getTotalPrice())+" VNĐ");

        builder.setView(viewDialog)
                .setNegativeButton("Tôi bận rồi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CHECK_SHOW_DIALOG = false;
                        updateStatusOrder(0,orderCurrentId);
                    }
                })
                .setPositiveButton("Nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CHECK_SHOW_FRAGMENT = true;
                        updateStatusOrder(1,orderCurrentId);
                        updateStatusShipper(2);
                        FragmentCurrentOrder fragmentCurrentOrder = new FragmentCurrentOrder();
                        fragmentCurrentOrder.setAddressStore(addressStore);
                        fragmentCurrentOrder.setGetAddressUser(addressUser);
                        fragmentCurrentOrder.setIdOrder(orderCurrentId);
                        moveScreen(fragmentCurrentOrder,"currentOrder");
                        changeColorMenu(txtMenuHome);
                    }
                });
        builder.create().show();
    }

    public void updateStatusOrder(int status,int orderId){
        SharedPreferences tokenCache = HomeActivity.this.getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
        String token = tokenCache.getString("token","");
        mSoSerivce.updateStatusOrder(token,orderId,status).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(HomeActivity.this,"Lỗi kết nối update status Order",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateStatusShipper(int status){
        SharedPreferences tokenCache = HomeActivity.this.getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
        String token = tokenCache.getString("token","");
        int idShipper = Integer.parseInt(tokenCache.getString("id",""));
        mSoSerivce.updateOnline(token,idShipper,status).enqueue(new Callback<Shipper>() {
            @Override
            public void onResponse(Call<Shipper> call, Response<Shipper> response) {

            }

            @Override
            public void onFailure(Call<Shipper> call, Throwable t) {
                Toast.makeText(HomeActivity.this,"Lỗi kết nối online",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void moveScreen(Fragment fragment,String tag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameHome,fragment,tag);
        fragmentTransaction.commit();
    }

    public void changeColorMenu(TextView tv){
        txtMenuHome.setTextColor(Color.GRAY);
        txtMenuListOrder.setTextColor(Color.GRAY);
        txtMenuProfile.setTextColor(Color.GRAY);
        tv.setTextColor(Color.RED);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
    }
}
