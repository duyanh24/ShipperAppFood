package com.leduyanh.shipper.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leduyanh.shipper.R;
import com.leduyanh.shipper.adapter.RecyclerViewOrderItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentListOrder extends Fragment {

    RecyclerView recyclerListOder;
    RecyclerViewOrderItem recyclerViewOrderItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_oder,container,false);

        recyclerListOder = (RecyclerView)view.findViewById(R.id.recyclerListOrder);

        List<String> demo = new ArrayList<>();
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");
        demo.add("leduyanh");

        recyclerViewOrderItemAdapter = new RecyclerViewOrderItem(getActivity(),demo);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerListOder.setAdapter(recyclerViewOrderItemAdapter);
        recyclerListOder.setLayoutManager(linearLayoutManager);


        return view;
    }
}
