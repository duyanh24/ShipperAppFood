package com.leduyanh.shipper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.leduyanh.shipper.fragment.FragmentCurrentOrder;
import com.leduyanh.shipper.fragment.FragmentHome;
import com.leduyanh.shipper.fragment.FragmentListOrder;
import com.leduyanh.shipper.fragment.FragmentProfile;
import com.leduyanh.shipper.model.Shipper;
import com.leduyanh.shipper.model.api.RetrofitClient;
import com.leduyanh.shipper.model.api.SOSerivce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearHomeListOrder,linearHome,linearHomeProfile;
    TextView txtMenuHome,txtMenuListOrder,txtMenuProfile;
    SwipeRefreshLayout refreshLayout;

    FragmentManager fragmentManager = getSupportFragmentManager();

    SOSerivce mSoSerivce;

    public static Boolean CHECK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        mSoSerivce = RetrofitClient.getClient().create(SOSerivce.class);

        FragmentHome fragmentHome = new FragmentHome();
        moveScreen(fragmentHome,"home");

        changeColorMenu(txtMenuHome);

        linearHomeListOrder.setOnClickListener(this);
        linearHome.setOnClickListener(this);
        linearHomeProfile.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                if(!CHECK){
                    callApiGetCurrentOrder();
                    CHECK = true;
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
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linearHome:
                if(CHECK){
                    FragmentCurrentOrder fragmentHome = new FragmentCurrentOrder();
                    moveScreen(fragmentHome,"home");
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


    public void callApiGetCurrentOrder(){
//        Call<List<Shipper>> call = mSoSerivce.checkUser();
//        call.enqueue(new Callback<List<Shipper>>() {
//            @Override
//            public void onResponse(Call<List<Shipper>> call, Response<List<Shipper>> response) {
//                List<Shipper> user = response.body();
//                if (user.size() != 0){
//                    showMessageNewOrder(user.get(0).getId());
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            refreshLayout.setRefreshing(false);
//                        }
//                    },500);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Shipper>> call, Throwable t) {
//                Toast.makeText(HomeActivity.this,"Lỗi kết nối",Toast.LENGTH_SHORT).show();
//                Log.d("bug2",t.getMessage());
//            }
//        });
    }

    private void showMessageNewOrder(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_receive_order,null);
        final TextView txtOrderCurrentRestau = viewDialog.findViewById(R.id.txtOderDetailRestau);
        final TextView txtOderDetailCustomer = viewDialog.findViewById(R.id.txtOderDetailCustomer);

//        mSoSerivce.getEmployeeById(id).enqueue(new Callback<Shipper>() {
//            @Override
//            public void onResponse(Call<Shipper> call, Response<Shipper> response) {
//                Shipper shipper = response.body();
//                txtOrderCurrentRestau.setText(shipper.getEmail());
//            }
//
//            @Override
//            public void onFailure(Call<Shipper> call, Throwable t) {
//
//            }
//        });

//        mSoSerivce.getEmployeeById(id).enqueue(new Callback<Shipper>() {
//            @Override
//            public void onResponse(Call<Shipper> call, Response<Shipper> response) {
//                Shipper shipper = response.body();
//                txtOderDetailCustomer.setText(shipper.getPassword());
//            }
//
//            @Override
//            public void onFailure(Call<Shipper> call, Throwable t) {
//
//            }
//        });

        builder.setView(viewDialog)
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CHECK = false;
                    }
                })
                .setPositiveButton("Nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FragmentCurrentOrder fragmentCurrentOrder = new FragmentCurrentOrder();
                        moveScreen(fragmentCurrentOrder,"currentOrder");
                        changeColorMenu(txtMenuHome);
                    }
                });
        builder.create().show();
    }

    public void moveScreen(Fragment fragment,String tag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameHome,fragment,tag);
        //fragmentTransaction.replace(R.id.frameHome,fragment);
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
