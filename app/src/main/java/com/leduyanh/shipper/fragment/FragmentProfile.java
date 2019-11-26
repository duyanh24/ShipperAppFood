package com.leduyanh.shipper.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.leduyanh.shipper.R;

public class FragmentProfile extends Fragment{
    TextView txtChangePass,txtCurrentPass,txtNewPass,txtComfirmPass;
    Button btnComfirmChangePass, btnDestroyChangePass;

    View view;
    View viewDialog;

    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        init();

        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChangePass();
            }
        });
        return view;
    }

    public void init(){
        txtChangePass = (TextView)view.findViewById(R.id.txtProfileChangePass);
    }

    private void showDialogChangePass() {
        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = this.getLayoutInflater();
        viewDialog = inflater.inflate(R.layout.custom_dialog_change_pass,null);

        initViewDialog();
        builder.setView(viewDialog);
        final AlertDialog alertDialog = builder.show();

        btnComfirmChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNull()){
                    if (checkPassComfirm()){
                        Toast.makeText(getActivity(),"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }else{
                        Toast.makeText(getActivity(),"Mật khẩu xác nhận không đúng!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Vui lòng nhập đủ thông tin!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDestroyChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void initViewDialog(){
        txtCurrentPass = (TextView)viewDialog.findViewById(R.id.txtCurrentPassword);
        txtNewPass = (TextView)viewDialog.findViewById(R.id.txtNewPassword);
        txtComfirmPass = (TextView)viewDialog.findViewById(R.id.txtConfirmPassword);

        btnComfirmChangePass = (Button)viewDialog.findViewById(R.id.btnComfirmChangePass);
        btnDestroyChangePass = (Button)viewDialog.findViewById(R.id.btnDestroyChangePass);
    }

    public boolean checkNull(){
        String currenPass = txtCurrentPass.getText().toString().trim();
        String newPass = txtNewPass.getText().toString().trim();
        String confirmPass = txtComfirmPass.getText().toString().trim();
        if(currenPass.equals("") || currenPass.equals(null) ||
                newPass.equals("") || newPass.equals(null) ||
                confirmPass.equals("") || confirmPass.equals(null)
        ){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkPassComfirm(){
        String newPass = txtNewPass.getText().toString().trim();
        String confirmPass = txtComfirmPass.getText().toString().trim();
        if(newPass.equals(confirmPass)){
           return true;
        }else{
            return false;
        }
    }
}
