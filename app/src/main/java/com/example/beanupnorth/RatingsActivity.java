package com.example.beanupnorth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RatingsActivity extends AppCompatActivity {

    RecyclerView ratRecyleView;
    TextView ratSummary;

    List<RatingFeedBack> ratingFeedBackList = new ArrayList<>();

    RatingItemAdapter ratingItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ratings2);
        ratRecyleView = findViewById(R.id.ratingsRecyleview);
        ratSummary = findViewById(R.id.ratingsSumText);
        ratRecyleView.setLayoutManager(new LinearLayoutManager(this));
        FetchRatings();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void FetchRatings(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Ratings");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ratingFeedBackList.clear();
                int numbersOfRating = 0;
                int ratingSum  = 0;
                for(DataSnapshot ds : snapshot.getChildren()){
                    RatingFeedBack rating = ds.getValue(RatingFeedBack.class);
                    if(rating!=null){
                        ratingFeedBackList.add(rating);
                        ratingSum+=rating.rating;
                        numbersOfRating++;
                    }
                }
                //Ratings count:12 | Average:
                double avg = numbersOfRating==0? 0: (double) ratingSum/numbersOfRating;
                ratSummary.setText("Ratings count: " + numbersOfRating +  " | Average: " + String.format("%.2f",avg)  );
                ratingItemAdapter = new RatingItemAdapter(ratingFeedBackList);
                ratRecyleView.setAdapter(ratingItemAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ONCLICKRETURNHOME(View v){
        startActivity(new Intent(RatingsActivity.this, HomeScreen.class));
    }
}