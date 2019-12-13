package com.leduyanh.shipper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leduyanh.shipper.R;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText edtNewPassword,edtConfirmNewPassword;
    Button btnChangePass;
    TextView txtChangePassEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        init();

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = edtNewPassword.getText().toString().trim();
                String comfirmNewPass = edtConfirmNewPassword.getText().toString().trim();
                if(newPass.length() < 8){
                    Toast.makeText(ChangePasswordActivity.this,"Mật khẩu ít nhất 8 kí tự",Toast.LENGTH_SHORT).show();
                }else if(!newPass.equals(comfirmNewPass)){
                    Toast.makeText(ChangePasswordActivity.this,"Mật khẩu xác nhận không đúng",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ChangePasswordActivity.this,"ok",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        txtChangePassEmail = (TextView)findViewById(R.id.txtChangePassEmail);
        edtNewPassword = (EditText)findViewById(R.id.edtChangeNewPassword);
        edtConfirmNewPassword = (EditText)findViewById(R.id.edtChangeConfirmNewPassword);
        btnChangePass = (Button)findViewById(R.id.btnChangePass);

    }
}
