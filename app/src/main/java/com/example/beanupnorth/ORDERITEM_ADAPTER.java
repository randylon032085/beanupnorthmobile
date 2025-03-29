package com.example.beanupnorth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ORDERITEM_ADAPTER extends RecyclerView.Adapter<ORDERITEM_ADAPTER.ORDERITEMViewHolder> {

    private List<OrderItem> orderItemList;

    public ORDERITEM_ADAPTER(List<OrderItem> orderItemList){
       this.orderItemList = orderItemList;
    }
    @NonNull
    @Override
    public ORDERITEMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new ORDERITEMViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ORDERITEMViewHolder holder, int position) {
        OrderItem item = orderItemList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(String.format("$%.2f",item.getPrice()));
        Glide.with(holder.itemView.getContext())
                .load(item.getImgURl())
                .apply(new RequestOptions().placeholder(R.drawable.coffeemug))
                .error(R.drawable.add)
                .centerCrop().into(holder.imgView);

    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public class ORDERITEMViewHolder extends RecyclerView.ViewHolder{

            ImageView imgView;
            TextView tvPrice, tvName;

        public ORDERITEMViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
            tvName = itemView.findViewById(R.id.tvItemName);
            imgView = itemView.findViewById(R.id.ivItem);

        }
    }
}
