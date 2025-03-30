package com.example.beanupnorth;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Spliterator;

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
//Define the input format
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        //Output format define outpur format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        try{
            //Convert String to date object
        Date date = inputFormat.parse(order.getDate());
        //Format date and time separately
            String formatedDate = dateFormat.format(date);
            String formatTime = timeFormat.format(date);
    holder.txtDate.setText("Date "+formatedDate+ "\nTime: " + formatTime);
    }catch (ParseException e){
        Log.e("date error", "Invalid date and time format");
    }


//    holder.txtOrderId.setText(order.getOrderId());
    holder.txtTotal.setText("Total: "+String.format("$%.2f",order.getTotal()) );
    holder.txtStatus.setText("Status: "+order.getStatus());
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

        //Setting lister to button
        holder.clickViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),PlaceOrder.class);
                intent.putExtra("myorder", order.getOrderId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return orderItems.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        ImageView imgItem;
        TextView txtItemName, txtItemPrice, txtDate, txtStatus, txtOrderId, txtTotal;
        RecyclerView rv_orderItem;

        Button clickViewHolder;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.ivItem);
            txtItemName = itemView.findViewById(R.id.tvItemName);
            txtItemPrice = itemView.findViewById(R.id.tvItemPrice);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
//            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            rv_orderItem = itemView.findViewById(R.id.rv_OrderItem);
            clickViewHolder = itemView.findViewById(R.id.button4);
        }


    }
}
