package com.example.beanupnorth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Ratings extends AppCompatActivity {

    RatingBar ratingbar;
    TextView starRatings;
    EditText comments;
    DatabaseReference dbRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ratings);

        dbRef = FirebaseDatabase.getInstance().getReference("Ratings");
        mAuth = FirebaseAuth.getInstance();
        ratingbar = findViewById(R.id.ratingBar);
        starRatings = findViewById(R.id.textView13);
        comments = findViewById(R.id.editTextTextMultiLine4);

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                starRatings.setText(rating+"");
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void SUBMIT_RATING(View v){

        ShowConfirmDialog();

    }

    private void ShowConfirmDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are your sure you want to submit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float rating = ratingbar.getRating();
                        String comm = comments.getText().toString();
                        FirebaseUser user = mAuth.getCurrentUser();

                        String email = user.getEmail();
                        Toast.makeText(Ratings.this, ""+rating+comm+email, Toast.LENGTH_SHORT).show();

                        RatingFeedBack ratingFeedBack = new RatingFeedBack(rating,email,comm);

                        String key = dbRef.push().getKey();
                        dbRef.child(key).setValue(ratingFeedBack).addOnSuccessListener(c->{
                            Toast.makeText(Ratings.this, "Feedback submitted", Toast.LENGTH_SHORT).show();
                            ratingbar.setRating(0);
                            comments.setText("");

                        }).addOnFailureListener(d->{
                            Toast.makeText(Ratings.this, "Error "+d.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public void RATINGSTOORDER(View v){
        finish();
    }
}