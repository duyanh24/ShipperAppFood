package com.leduyanh.shipper.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.leduyanh.shipper.activity.HomeActivity;
import com.leduyanh.shipper.activity.LogInActivity;
import com.leduyanh.shipper.model.shipper.Shipper;
import com.leduyanh.shipper.api.RetrofitClient;
import com.leduyanh.shipper.api.SOSerivce;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment{
    TextView txtChangePass,txtCurrentPass,txtNewPass,txtComfirmPass,txtLogout;
    TextView txtName, txtEmail, txtPhone, txtIdent;

    Button btnComfirmChangePass, btnDestroyChangePass;

    View view;
    View viewDialog;

    AlertDialog.Builder builder;

    SOSerivce mSoSerivce;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        init();
        mSoSerivce = RetrofitClient.getClient().create(SOSerivce.class);

        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChangePass();
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        getInfoShipper();
        return view;
    }

    public void init(){
        txtChangePass = (TextView)view.findViewById(R.id.txtProfileChangePass);
        txtLogout = (TextView)view.findViewById(R.id.txtBtnLogout);
        txtName = (TextView)view.findViewById(R.id.txtProfileName);
        txtEmail = (TextView)view.findViewById(R.id.txtProfileEmail);
        txtPhone = (TextView)view.findViewById(R.id.txtProfilePhone);
        txtIdent = (TextView)view.findViewById(R.id.txtProfileIdent);
    }

    public void getInfoShipper(){
        SharedPreferences tokenCache = getActivity().getSharedPreferences("infoShipper", Context.MODE_PRIVATE);
        String token = tokenCache.getString("token","");
        int idShipper = Integer.parseInt(tokenCache.getString("id",""));

        mSoSerivce.getInfoShipper(token,idShipper).enqueue(new Callback<Shipper>() {
            @Override
            public void onResponse(Call<Shipper> call, Response<Shipper> response) {
                Shipper result = response.body();
                if(result != null){
                    txtName.setText(result.getData().getName());
                    txtEmail.setText(result.getData().getEmail());
                    txtPhone.setText(String.valueOf(result.getData().getPhone()));
                    txtIdent.setText(result.getData().getIdentification());
                }
            }

            @Override
            public void onFailure(Call<Shipper> call, Throwable t) {
                Toast.makeText(getActivity(),"Lỗi kết nối",Toast.LENGTH_SHORT).show();

            }
        });
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

    public void logout(){
        AlertDialog.Builder builderMessage = new AlertDialog.Builder(getActivity());

        builderMessage.setTitle("Bạn có muốn đăng xuất?")
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((HomeActivity)getContext()).logOut();
                    }
                });
        builderMessage.create().show();
    }
}
