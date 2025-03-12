package com.example.beanupnorth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.TotpMultiFactorAssertion;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    private List<CartItem> cartItemList;
    private OnCartItemRemovedListener listener;

    public CartAdapter(List<CartItem> cartItemList, OnCartItemRemovedListener listener){
        this.cartItemList = cartItemList;
        this.listener = listener;
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
        Glide.with(holder.itemView.getContext())
                .load(item.getImgURl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.coffeemug)
                        .error(R.drawable.minus).centerCrop())
                .into(holder.imgProduct);
        holder.tvname.setText(item.getName());
        holder.tvquantity.setText("qty:"+item.getQuantity());
        holder.tvprice.setText("$"+item.getPrice()+"00");

        holder.tRemove.setOnClickListener(v ->{
            Toast.makeText(holder.itemView.getContext(), "hello", Toast.LENGTH_SHORT).show();
            int removedPosition = holder.getAdapterPosition();
            if(removedPosition!=RecyclerView.NO_POSITION){
                CartItem removedItem = cartItemList.get(removedPosition);
                cartItemList.remove(removedPosition);
                notifyItemRemoved(removedPosition);
                if(listener!=null){
                    listener.onItemRemoved(removedItem);

                }
            }
        });

    }

    public interface OnCartItemRemovedListener{

        void onItemRemoved(CartItem removedItem);

    }


    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        TextView tvname, tvprice, tvquantity, tRemove;
        ImageView imgProduct;



        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.itemName);
            tvprice = itemView.findViewById(R.id.itemPrice);
            tvquantity = itemView.findViewById(R.id.itemQuantity);
            imgProduct = itemView.findViewById(R.id.iVproduct);
            tRemove = itemView.findViewById(R.id.tvRemove);



        }
    }
}
