package com.leduyanh.shipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.leduyanh.shipper.utils.GMailSender;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button btnGetCodeChangePass;
    EditText edtGetCodeEmail;
    ConstraintLayout layoutConfirmCode,layoutGetCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btnGetCodeChangePass = (Button)findViewById(R.id.btnGetCodeChangePass);
        edtGetCodeEmail = (EditText) findViewById(R.id.edtGetCodeEmail);
        layoutConfirmCode = (ConstraintLayout)findViewById(R.id.layoutConfirmCode);
        layoutGetCode = (ConstraintLayout)findViewById(R.id.layoutSendCode);

        btnGetCodeChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senMail();
                layoutGetCode.setVisibility(View.GONE);
                layoutConfirmCode.setVisibility(View.VISIBLE);
            }
        });
    }

    public void senMail(){
        final ProgressDialog dialog = new ProgressDialog(ForgotPasswordActivity.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("leduyanh24071998@gmail.com", "anhduy96");
                    sender.sendMail("EmailSender App",
                            "adasd",
                            "leduyanh24071998@gmail.com",
                            "leduyanh.ict@gmail.com");
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }
}
