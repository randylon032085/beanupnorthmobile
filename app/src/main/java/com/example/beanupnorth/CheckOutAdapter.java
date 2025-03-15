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

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.CheckOutViewHolder> {


    private List<CartItem> cartItemList;

    public CheckOutAdapter(List<CartItem> cartItemList){
        this.cartItemList=cartItemList;
    }
    @NonNull
    @Override
    public CheckOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_out,parent,false);

        return new CheckOutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutViewHolder holder, int position) {
    CartItem item = cartItemList.get(position);
    Glide.with(holder.itemView.getContext())
            .load(item.getImgURl())
                    .apply(new RequestOptions().placeholder(R.drawable.coffeemug).error(R.drawable.add).centerCrop())
                            .into(holder.imgCOproduct);

    holder.tvItemName.setText(item.getName());
    holder.tvQuantity.setText("Qty: "+item.getQuantity());
    holder.tvPrice.setText(String.format("$%.2f",item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CheckOutViewHolder extends RecyclerView.ViewHolder{

        TextView tvPrice, tvQuantity, tvItemName;
        ImageView imgCOproduct;
        public CheckOutViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPrice = itemView.findViewById(R.id.itemPrice);
            tvQuantity = itemView.findViewById(R.id.itemCOQuantity);
            tvItemName = itemView.findViewById(R.id.itemName);
            imgCOproduct = itemView.findViewById(R.id.iVCOproduct);
        }
    }

}
