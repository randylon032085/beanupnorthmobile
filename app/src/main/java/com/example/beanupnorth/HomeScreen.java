package com.example.beanupnorth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<FoodItem> foodList;
    /////////
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    TextView tviewProduct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

    recyclerView = findViewById(R.id.recycleView);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    //Sample Data
        foodList = new ArrayList<>();
        foodList.add(new FoodItem(R.drawable.beans, "Beaf cheese burger", "with melted cheddar cheese" ));
        foodList.add(new FoodItem(R.drawable.beanlogo, "Beaf logo", "with melted logo cheese" ));
        foodList.add(new FoodItem(R.drawable.padlock, "padlock", "with melted cheddar padlock" ));
        foodList.add(new FoodItem(R.drawable.beans, "Beaf cheese burger", "with melted cheddar cheese" ));
        foodList.add(new FoodItem(R.drawable.beanlogo, "Beaf logo", "with melted logo cheese" ));
        foodList.add(new FoodItem(R.drawable.padlock, "padlock", "with melted cheddar padlock" ));
        foodList.add(new FoodItem(R.drawable.beans, "Beaf cheese burger", "with melted cheddar cheese" ));
        foodList.add(new FoodItem(R.drawable.beanlogo, "Beaf logo", "with melted logo cheese" ));
        foodList.add(new FoodItem(R.drawable.padlock, "padlock", "with melted cheddar padlock" ));
        foodList.add(new FoodItem(R.drawable.beans, "Beaf cheese burger", "with melted cheddar cheese" ));
        foodList.add(new FoodItem(R.drawable.beanlogo, "Beaf logo", "with melted logo cheese" ));
        foodList.add(new FoodItem(R.drawable.padlock, "padlock", "with melted cheddar padlock" ));
        foodList.add(new FoodItem(R.drawable.beans, "Beaf cheese burger", "with melted cheddar cheese" ));
        foodList.add(new FoodItem(R.drawable.beanlogo, "Beaf logo", "with melted logo cheese" ));
        foodList.add(new FoodItem(R.drawable.padlock, "padlock", "with melted cheddar padlock" ));

        foodAdapter = new FoodAdapter(this, foodList);

        recyclerView.setAdapter(foodAdapter);

//        tviewProduct = findViewById(R.id.tvProduct);
//
//        mDatabase = FirebaseDatabase.getInstance().getReference("products");
////        fetchProductByType("beverages");
//         mDatabase.addValueEventListener(new ValueEventListener() {
//             @Override
//             public void onDataChange(@NonNull DataSnapshot snapshot) {
//                 StringBuilder productList = new StringBuilder();
//                 for(DataSnapshot productSnopshot : snapshot.getChildren()){
//
//                     Products products= productSnopshot.getValue(Products.class);
//                     if(products!=null){
//                        productList.append("Product Name: ").append(products.getProductName()).append("Product Type: ").append(products.getProductType()).append("\n");
//                     }
//                 }
//                 tviewProduct.setText(productList.toString());
//             }
//
//             @Override
//             public void onCancelled(@NonNull DatabaseError error) {
//                 Toast.makeText(HomeScreen.this, "Firebase Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
//             }
//         });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Function for Fetching Product by Type
    private void fetchProductByType(String productType){
        Query query = mDatabase.orderByChild("productType").equalTo(productType);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapShot : snapshot.getChildren()){
                    String name = snapShot.child("productName").getValue(String.class);
                    String type = snapShot.child("productType").getValue(String.class);
                    Log.e("ProductLists", "ProductName: " + name + ", ProductType: " +  type);
                    Toast.makeText(HomeScreen.this, "" + name + type, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeScreen.this, "Firebase error"+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserData(String userId){
        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue(String.class);
                    Toast.makeText(HomeScreen.this, "Welcome" + name, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeScreen.this, "welcome", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void logout(View v){
//
//        Intent intent = new Intent(HomeScreen.this, Login.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
//    }
}