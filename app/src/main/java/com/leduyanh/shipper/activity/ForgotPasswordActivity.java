package com.leduyanh.shipper.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leduyanh.shipper.R;
import com.leduyanh.shipper.utils.GMailSender;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button btnGetCodeChangePass,btnConfirmCode;
    EditText edtGetCodeEmail,edtConfirmCode;
    ConstraintLayout layoutConfirmCode,layoutGetCode;

    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btnGetCodeChangePass = (Button)findViewById(R.id.btnGetCodeChangePass);
        btnConfirmCode = (Button)findViewById(R.id.btnConfirmCode);

        edtConfirmCode = (EditText)findViewById(R.id.edtConfirmCode);
        edtGetCodeEmail = (EditText) findViewById(R.id.edtGetCodeEmail);
        layoutConfirmCode = (ConstraintLayout)findViewById(R.id.layoutConfirmCode);
        layoutGetCode = (ConstraintLayout)findViewById(R.id.layoutSendCode);

        btnGetCodeChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtGetCodeEmail.getText().toString().trim();
                if(email == null || email.equals("")){
                    Toast.makeText(ForgotPasswordActivity.this,"vui lòng nhập email",Toast.LENGTH_SHORT).show();
                }else{
                    Random rnd = new Random();
                    int number = rnd.nextInt(999999);
                    code = String.valueOf(number);
                    senMail(email);
                    layoutGetCode.setVisibility(View.GONE);
                    layoutConfirmCode.setVisibility(View.VISIBLE);
                }
            }
        });
        btnConfirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmCode = edtConfirmCode.getText().toString().trim();
                if(confirmCode.equals(code)){
                    Intent intent = new Intent(ForgotPasswordActivity.this,ChangePasswordActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ForgotPasswordActivity.this,"Mã xác nhận không chính xác!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void senMail(final String email){
        final ProgressDialog dialog = new ProgressDialog(ForgotPasswordActivity.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("leduyanh24071998@gmail.com", "anhduy96");
                    sender.sendMail("MÃ XÁC NHẬN QUÊN MẬT KHẨU",
                            code,
                            "GoShip",
                            email);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }
}
