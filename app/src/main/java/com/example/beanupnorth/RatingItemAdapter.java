package com.example.beanupnorth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RatingItemAdapter extends RecyclerView.Adapter<RatingItemAdapter.RatingViewHolder>{


    private List<RatingFeedBack> ratingsList;

    public RatingItemAdapter(List<RatingFeedBack> ratingsList){
        this.ratingsList = ratingsList;

    }
    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_details,parent,false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {

        RatingFeedBack ratings = ratingsList.get(position);
        holder.textViewEmail.setText("From: "+ratings.email);
        holder.textViewComment.setText(ratings.comment);
    }

    @Override
    public int getItemCount() {
        return ratingsList.size();
    }

    public static class RatingViewHolder extends RecyclerView.ViewHolder{

        TextView textViewEmail, textViewComment;

        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmail = itemView.findViewById(R.id.emailText);
            textViewComment = itemView.findViewById(R.id.commentText);
        }
    }
}
