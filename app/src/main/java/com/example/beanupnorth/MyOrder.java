package com.example.beanupnorth;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_order);
        FetchOrder();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void FetchOrder (){
        Log.d("Firebase Orders","Fetch ordered called");
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("orders");
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Firebase Orders","Data received from Firebase");
                if(!snapshot.exists()){
                    Log.d("Firebase Orders","No data found in orders node");
                    return;
                }

                StringBuilder ordersInfo = new StringBuilder();
                for(DataSnapshot orderSnapShot : snapshot.getChildren()){
                    Log.d("Firebase Orders","Checking order: " + orderSnapShot.getKey());
                    for(DataSnapshot orderSnapShot2 : orderSnapShot.getChildren()){
                        Log.d("Firebase Orders","OrderSnapShot2: "  + orderSnapShot2);
                        for(DataSnapshot orderSnapShot3 : orderSnapShot2.getChildren()){
                            String customerId = orderSnapShot3.child("customerId").getValue(String.class);
                            Log.d("Firebase Orders","Customer ID: " + customerId);
                            if(customerId!=null && customerId.equals("dylan032085@gmail.com")){
                                Log.d("Firebase Orders","Order matched for dylan032085@gmail.com");

                                String orderId = orderSnapShot3.getKey();
                                String date = orderSnapShot3.child("date").getValue(String.class);
                                String status = orderSnapShot3.child("status").getValue(String.class);
                                Double total = orderSnapShot3.child("total").getValue(Double.class);

                                ordersInfo.append("orderId: ").append(orderId)
                                        .append("\ndate: ").append(date)
                                        .append("\nstatus: ").append(status)
                                        .append("\\ntotal:").append(total)
                                        .append("\n\n")
                                ;

                            }
                        }
                    }

                }

                if(ordersInfo.length()==0){
                    Log.d("Firebase Orders","No matching orders found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Firebase Orders","Failed to read orders");
            }
        });

    }
}