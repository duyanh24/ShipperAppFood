package com.leduyanh.shipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leduyanh.shipper.fragment.FragmentHome;
import com.leduyanh.shipper.fragment.FragmentListOrder;
import com.leduyanh.shipper.fragment.FragmentProfile;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearHomeListOrder,linearHome,linearHomeProfile;
    TextView txtMenuHome,txtMenuListOrder,txtMenuProfile;

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        FragmentHome fragmentHome = new FragmentHome();
        moveScreen(fragmentHome);

        changeColorMenu(txtMenuHome);

        linearHomeListOrder.setOnClickListener(this);
        linearHome.setOnClickListener(this);
        linearHomeProfile.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linearHome:
                //FragmentHome fragmentHome = new FragmentHome();
                //moveScreen(fragmentHome);
                //changeColorMenu(txtMenuHome);

                Intent intent = new Intent(HomeActivity.this,DirectionActivity.class);
                startActivity(intent);

                break;
            case R.id.linearHomeListOrder:
                FragmentListOrder fragmentListOrder = new FragmentListOrder();
                moveScreen(fragmentListOrder);
                changeColorMenu(txtMenuListOrder);
                break;
            case R.id.linearHomeProfile:
                FragmentProfile fragmentProfile = new FragmentProfile();
                moveScreen(fragmentProfile);
                changeColorMenu(txtMenuProfile);
                break;
        }
    }

    public void moveScreen(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameHome,fragment);
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
