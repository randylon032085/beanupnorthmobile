package com.example.beanupnorth;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private List<FoodItem> FoodList;
    private List<FoodItem> FoodListFull;
    private onAddtoCartListener addtoCartListener;

    public FoodAdapter(Context context, List<FoodItem> FoodList, onAddtoCartListener listener){
        this.context = context;
        this.FoodList = FoodList;
        this.FoodListFull = new ArrayList<>();
        this.addtoCartListener = listener;
        if(FoodList!=null){
        this.FoodListFull.addAll(FoodList);
        }



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
        Log.d("ImageURL", "Loading Image: " + food.getImgUrl());
//        holder.foodImage.setImageResource(food.getImageResId());
//        glide needs to add first dependencies in gradles
        Glide.with(holder.itemView.getContext())
                .load(food.getImgUrl())
                .apply(new RequestOptions()
                        .centerCrop())
                .into(holder.foodImage);
        holder.foodDescription.setText(food.getDescription());
        holder.foodName.setText(food.getName());
        holder.price.setText("$"+food.getPrice()+".00");
        holder.type.setText(food.getType());
        holder.addtoCartButton.setOnClickListener(v->{


            if(addtoCartListener!=null){
                if(food.getQuantity()==0){
                    Toast.makeText(context, "Please add quantity", Toast.LENGTH_SHORT).show();
                }else{
                    addtoCartListener.onAddtoCart(food);
                }

            }

        });
        holder.edtQuantity.setText(food.getQuantity()+"");

        holder.butAdd.setOnClickListener( v -> {

                food.setQuantity(food.getQuantity()+1);
                holder.edtQuantity.setText(food.getQuantity()+"");

        });
        holder.butMinus.setOnClickListener(v -> {

            if(food.getQuantity()>0){
                food.setQuantity(food.getQuantity()-1);
                holder.edtQuantity.setText(food.getQuantity()+"");
            }


        });


    }
        //User defined interface class for onAddtToCart Listener
    public interface onAddtoCartListener{
        void onAddtoCart(FoodItem foodItem);
    }

    @Override
    public int getItemCount() {
        return FoodList.size();
    }


    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView foodName, foodDescription, price, type;
        Button addtoCartButton;
        ImageButton butAdd, butMinus;
        EditText edtQuantity;


        public FoodViewHolder(@NonNull View itemView)
        {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodDescription = itemView.findViewById(R.id.foodDescription);
            addtoCartButton = itemView.findViewById(R.id.addToCartButton);
            price = itemView.findViewById((R.id.tvPrice));
            type = itemView.findViewById(R.id.tvType);
            butAdd = itemView.findViewById(R.id.btnAdd);
            butMinus = itemView.findViewById(R.id.btnMinus);
            edtQuantity = itemView.findViewById(R.id.etQuantity);


        }
    }

//A function that will call the foodFilter


    public Filter getFilter(){
        return foodFilter;
    }


    //this is the function for filtering fooditem
    private final Filter foodFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FoodItem> filteredList = new ArrayList<>();
            Log.d("FOOD ADAPTER", "filtering with query: "+constraint);
            Log.d("FOOD ADAPTER", "foodlistFull size: "+FoodListFull.size());
            if(constraint==null || constraint.length()==0){
                filteredList.addAll(FoodListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim(); //we make sure that the input has no white spaces and the keys are in lowered.

               for(FoodItem item: FoodListFull){
                if(item.getName().toLowerCase().contains(filterPattern)){
                            //cofee has cof
                    //pass the result of filter to filtered items
                    filteredList.add(item);
                }

               }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                FoodList.clear();
                if(results.values!=null){
                    FoodList.addAll((List<FoodItem>)results.values);
                }

                notifyDataSetChanged();
        }
    };


}


