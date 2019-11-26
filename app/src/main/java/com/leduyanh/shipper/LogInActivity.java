package com.leduyanh.shipper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    TextView txtBtnLoginForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnLogin.setOnClickListener(this);
        txtBtnLoginForgotPass.setOnClickListener(this);
    }

    private void init() {
        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtBtnLoginForgotPass = (TextView)findViewById(R.id.txtBtnLoginForgotPass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                Intent intentHome = new Intent(LogInActivity.this,HomeActivity.class);
                startActivity(intentHome);
                break;
            case R.id.txtBtnLoginForgotPass:
                Intent intentForgotPass = new Intent(LogInActivity.this,ForgotPasswordActivity.class);
                startActivity(intentForgotPass);
                break;
        }
    }
}
