package com.example.beanupnorth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    private List<CartItem> cartItemList;

    public CartAdapter(List<CartItem> cartItemList){
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);
        holder.tvname.setText(item.getName());
        holder.tvquantity.setText("qty:"+item.getQuantity());
        holder.tvprice.setText("$"+item.getPrice()+"00");
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        TextView tvname, tvprice, tvquantity;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.itemName);
            tvprice = itemView.findViewById(R.id.itemPrice);
            tvquantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}
