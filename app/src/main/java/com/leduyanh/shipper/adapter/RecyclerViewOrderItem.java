package com.leduyanh.shipper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.leduyanh.shipper.activity.OrderdetailActivity;
import com.leduyanh.shipper.R;
import com.leduyanh.shipper.model.order.DataOrder;

import java.util.List;

public class RecyclerViewOrderItem extends RecyclerView.Adapter<RecyclerViewOrderItem.OderItemViewHolder> {

    Context mContext;
    List<DataOrder> listOrder;

    public RecyclerViewOrderItem(Context mContext, List<DataOrder> listOrder) {
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull OderItemViewHolder holder, final int position) {
        holder.txtLocation.setText(listOrder.get(position).getAddress());
        holder.txtTime.setText(listOrder.get(position).getTime());

        int statusOrder = listOrder.get(position).getStatus();

        if(statusOrder == 1){
            holder.txtStatus.setText("Đang giao");
        }else if(statusOrder == 2){
            holder.txtStatus.setText("Đã hoàn thành");
        }else if(statusOrder == 3){
            holder.txtStatus.setText("Đã hủy");
            holder.txtLocation.setTextColor(R.color.colorGray);
        }

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), OrderdetailActivity.class);
                intent.putExtra("order_id",String.valueOf(listOrder.get(position).getId()));
                intent.putExtra("infoUser",listOrder.get(position).getUser().getName()+", "+
                        listOrder.get(position).getAddress());

                intent.putExtra("infoStore",listOrder.get(position).getStore().getName()+", "+
                        listOrder.get(position).getStore().getAddress());

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
