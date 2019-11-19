package com.leduyanh.shipper.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.leduyanh.shipper.OrderdetailActivity;
import com.leduyanh.shipper.R;

import java.util.List;

public class RecyclerViewOrderItem extends RecyclerView.Adapter<RecyclerViewOrderItem.OderItemViewHolder> {

    Context mContext;
    List<String> listOrder;

    public RecyclerViewOrderItem(Context mContext, List<String> listOrder) {
        this.mContext = mContext;
        this.listOrder = listOrder;
    }

    @NonNull
    @Override
    public OderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_oder,parent,false);
        OderItemViewHolder viewHolder = new OderItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OderItemViewHolder holder, final int position) {
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext.getApplicationContext(),"ms-"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext.getApplicationContext(), OrderdetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class OderItemViewHolder extends RecyclerView.ViewHolder{
        TextView txtLocation,txtStatus,txtTime;
        ImageView imgMotor;
        ConstraintLayout layoutItem;
        public OderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLocation = (TextView)itemView.findViewById(R.id.txtListOderLocation);
            txtStatus = (TextView)itemView.findViewById(R.id.txtListOderStatus);
            txtTime = (TextView)itemView.findViewById(R.id.txtListOderTime);
            imgMotor = (ImageView) itemView.findViewById(R.id.imgItemOderMotor);
            layoutItem = (ConstraintLayout) itemView.findViewById(R.id.layoutItemListOder);
        }
    }
}
