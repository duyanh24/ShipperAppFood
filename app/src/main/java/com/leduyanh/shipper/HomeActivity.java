package com.leduyanh.shipper;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leduyanh.shipper.fragment.FragmentCurrentOrder;
import com.leduyanh.shipper.fragment.FragmentHome;
import com.leduyanh.shipper.fragment.FragmentListOrder;
import com.leduyanh.shipper.fragment.FragmentProfile;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearHomeListOrder,linearHome,linearHomeProfile;
    TextView txtMenuHome,txtMenuListOrder,txtMenuProfile;
    SwipeRefreshLayout refreshLayout;

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        FragmentHome fragmentHome = new FragmentHome();
        moveScreen(fragmentHome,"home");

        changeColorMenu(txtMenuHome);

        linearHomeListOrder.setOnClickListener(this);
        linearHome.setOnClickListener(this);
        linearHomeProfile.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMessageNewOrder();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
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
                FragmentHome fragmentHome = new FragmentHome();
                moveScreen(fragmentHome,"home");
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

    private void showMessageNewOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_receive_order,null);
        builder.setView(viewDialog)
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
