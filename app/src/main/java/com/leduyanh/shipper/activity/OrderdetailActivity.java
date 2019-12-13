package com.leduyanh.shipper.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leduyanh.shipper.R;
import com.leduyanh.shipper.adapter.AdapterRecycOrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderdetailActivity extends AppCompatActivity {

    RecyclerView recycOrderDetail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_detail);

        recycOrderDetail = (RecyclerView)findViewById(R.id.recyceOderDetail);

        List<String> demo = new ArrayList<>();
        demo.add("duyanh");
        demo.add("duyanh");
        demo.add("duyanh");
        demo.add("duyanh");
        AdapterRecycOrderDetail adapterRecycOrderDetail = new AdapterRecycOrderDetail(demo, OrderdetailActivity.this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycOrderDetail.setAdapter(adapterRecycOrderDetail);
        recycOrderDetail.setLayoutManager(linearLayoutManager);
    }
}
