package com.example.beanupnorth;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderViewHolder> {

    private List<ORDERS> orderItems;
    Context context;

    public OrderItemAdapter(List<ORDERS> orderItems, Context context){
        this.orderItems = orderItems;
        this.context = context;

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
    ORDERS order = orderItems.get(position);
    holder.txtDate.setText(order.getDate());
    holder.txtOrderId.setText(order.getOrderId());
    holder.txtTotal.setText(String.format("$%.2f",order.getTotal()) );
    holder.txtStatus.setText(order.getStatus());
        Log.d("NUMBER OF ITEMS",""+orderItems.size());


        //Calling orderItemAdapter

        ORDERITEM_ADAPTER orderitemAdapter = new ORDERITEM_ADAPTER(order.getItem());
        holder.rv_orderItem.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rv_orderItem.setAdapter(orderitemAdapter);
//    Glide.with(holder.itemView.getContext())
//                    .load(item.getImgURl())
//                            .apply(new RequestOptions().placeholder(R.drawable.coffeemug).error(R.drawable.add).centerCrop())
//                                    .into(holder.imgItem);
//    holder.txtItemName.setText(item.getName());
//    holder.txtItemPrice.setText(String.format("$%.2f",item.getPrice()));

    }

    @Override
    public int getItemCount() {

        return orderItems.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        ImageView imgItem;
        TextView txtItemName, txtItemPrice, txtDate, txtStatus, txtOrderId, txtTotal;
        RecyclerView rv_orderItem;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.ivItem);
            txtItemName = itemView.findViewById(R.id.tvItemName);
            txtItemPrice = itemView.findViewById(R.id.tvItemPrice);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            rv_orderItem = itemView.findViewById(R.id.rv_OrderItem);
        }
    }
}
