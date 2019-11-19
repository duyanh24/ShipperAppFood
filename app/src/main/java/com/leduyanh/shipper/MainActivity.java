package com.leduyanh.shipper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnLogin.setOnClickListener(this);
    }

    private void init() {
        btnLogin = (Button)findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                Intent intentHome = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intentHome);
                break;
        }
    }
}
