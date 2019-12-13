package com.leduyanh.shipper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leduyanh.shipper.R;
import com.leduyanh.shipper.model.Shipper;
import com.leduyanh.shipper.model.api.RetrofitClient;
import com.leduyanh.shipper.model.api.SOSerivce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    TextView txtBtnLoginForgotPass;
    SOSerivce mSOSerivce;
    EditText edtLoginEmail, edtLoginPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        mSOSerivce = RetrofitClient.getClient().create(SOSerivce.class);

        btnLogin.setOnClickListener(this);
        txtBtnLoginForgotPass.setOnClickListener(this);
    }

    private void init() {
        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtBtnLoginForgotPass = (TextView)findViewById(R.id.txtBtnLoginForgotPass);
        edtLoginEmail = (EditText)findViewById(R.id.edtLoginEmail);
        edtLoginPass = (EditText)findViewById(R.id.edtLoginPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                checkLogin();
                break;
            case R.id.txtBtnLoginForgotPass:
                Intent intentForgotPass = new Intent(LogInActivity.this,ForgotPasswordActivity.class);
                startActivity(intentForgotPass);
                break;
        }
    }

    private void checkLogin() {
        String email = edtLoginEmail.getText().toString().trim();
        String password = edtLoginPass.getText().toString().trim();

        Call<Shipper> call = mSOSerivce.logIn(email,password);

        call.enqueue(new Callback<Shipper>() {
            @Override
            public void onResponse(Call<Shipper> call, Response<Shipper> response) {
                Shipper resultLogin = response.body();
                if(resultLogin.getSuccess()){
                    Intent intentHome = new Intent(LogInActivity.this,HomeActivity.class);
                    startActivity(intentHome);

                    SharedPreferences tokenCache = LogInActivity.this.getSharedPreferences("token",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = tokenCache.edit();
                    editor.putString("token","Bearer "+resultLogin.getToken());
                    editor.commit();

                    finish();
                }else{
                    Toast.makeText(LogInActivity.this,"Tài khoản hoặc mật khẩu không chính xác",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Shipper> call, Throwable t) {
                Toast.makeText(LogInActivity.this,"Lỗi! Xem lại đường truyền mạng",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
