package com.leduyanh.shipper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.leduyanh.shipper.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (Exception e){

                }finally {
                    SharedPreferences tokenCache = MainActivity.this.getSharedPreferences("token", Context.MODE_PRIVATE);
                    String token = tokenCache.getString("token","");
                    if(token.equals("") || token.equals(null)){
                        Intent intentLogin = new Intent(MainActivity.this, LogInActivity.class);
                        startActivity(intentLogin);
                        finish();
                    }else {
                        Intent intentHome = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intentHome);
                        finish();
                    }
                }
            }
        });
        thread.start();
    }
}
