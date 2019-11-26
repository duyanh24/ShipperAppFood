package com.leduyanh.shipper.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leduyanh.shipper.DirectionActivity;
import com.leduyanh.shipper.R;
import com.leduyanh.shipper.adapter.AdapterRecycOrderDetail;
import com.leduyanh.shipper.adapter.RecyclerViewOrderItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentCurrentOrder extends Fragment implements View.OnClickListener {

    View view;

    RecyclerView recyclerListItem;
    RecyclerViewOrderItem recyclerViewOrderItemAdapter;

    Button btnDirection, btnCall;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_current,container,false);

        init();

        setInfoOrder();

        btnDirection.setOnClickListener(this);
        btnCall.setOnClickListener(this);

        return view;
    }

    private void init() {
        recyclerListItem = (RecyclerView)view.findViewById(R.id.recycOderCurrent);
        btnDirection = (Button)view.findViewById(R.id.btnOrderCurrentDirection);
        btnCall = (Button)view.findViewById(R.id.btnOrderCurrentCall);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOrderCurrentDirection:
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }else {
                    Intent intentDirectionActivity = new Intent(getActivity(), DirectionActivity.class);
                    startActivity(intentDirectionActivity);
                }

                break;
            case R.id.btnOrderCurrentCall:

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                }else{
                    Uri uri = Uri.parse("tel:"+"05452156256");
                    Intent i = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(i);
                }
                break;
        }
    }

    public void setInfoOrder(){
        List<String> demo = new ArrayList<>();
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");

        AdapterRecycOrderDetail adapterRecycOrderDetail = new AdapterRecycOrderDetail(demo, getActivity());
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerListItem.setAdapter(adapterRecycOrderDetail);
        recyclerListItem.setLayoutManager(linearLayoutManager);
    }
}
