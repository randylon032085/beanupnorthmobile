package com.example.beanupnorth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private List<FoodItem> FoodList;

    public FoodAdapter(Context context, List<FoodItem> FoodList){
        this.context = context;
        this.FoodList = FoodList;

    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_food,parent,false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {

        FoodItem food = FoodList.get(position);
        holder.foodImage.setImageResource(food.getImageResId());
        holder.foodDescription.setText(food.getDescription());
        holder.foodName.setText(food.getName());
        holder.addtoCartButton.setOnClickListener(v->{

        });
    }

    @Override
    public int getItemCount() {
        return FoodList.size();
    }


    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView foodName, foodDescription;
        Button addtoCartButton;

        public FoodViewHolder(@NonNull View itemView)
        {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodDescription = itemView.findViewById(R.id.foodDescription);
            addtoCartButton = itemView.findViewById(R.id.addToCartButton);


        }
    }


}


