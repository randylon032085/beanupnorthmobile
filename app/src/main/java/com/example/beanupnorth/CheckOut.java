package com.example.beanupnorth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CheckOut extends AppCompatActivity {

    RecyclerView recyclerView;
    CheckOutAdapter checkOutAdapter;
    List<CartItem> checkOutItemList;

    TextView tVSubtotal, tVDiscount, tVTax, tVtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_out);

        recyclerView = findViewById(R.id.checkOutRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkOutItemList = (ArrayList<CartItem>)getIntent().getSerializableExtra("cartItems");
        checkOutAdapter = new CheckOutAdapter(checkOutItemList);
        recyclerView.setAdapter(checkOutAdapter);


        tVSubtotal = findViewById(R.id.tvSubtotal);
        tVDiscount = findViewById(R.id.tvDiscount);
        tVTax = findViewById(R.id.tvTax);
        tVtotal = findViewById(R.id.tvTotal);

        updateCheckoutTotal();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void updateCheckoutTotal(){
        double subTotal = 0.0;
        for(CartItem item : checkOutItemList){
            subTotal+=item.getTotalPrice();
        }
//        double discount = subTotal * 0.10;
        double discount = 0;
        double tax = (subTotal-discount)* 0.12;
        double total = subTotal-discount+tax;
        tVSubtotal.setText(String.format("$%.2f",subTotal ));
        tVDiscount.setText(String.format("$%.2f", discount));
        tVTax.setText(String.format("S%.2f",tax));
        tVtotal.setText(String.format("$%.2f",total));
    }

    public void ONCLICKPLACEHOLDER (View v){
        //get reference to the firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference orderRef = database.getReference("orders");

        //get the current user firebase auth.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null){
            //Create an order object
            Order order = new Order();
            order.setOrderId(orderRef.push().getKey());
            order.setCustomerId(currentUser.getEmail());

            //formating the current date and time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String formatedDate = sdf.format(new Date());

            //setting formatted date
            order.setDate(formatedDate);

            //setting other order details
            order.setItem(checkOutItemList);
            order.setSubTotal(Double.parseDouble(tVSubtotal.getText().toString().substring(1)));
            order.setDiscount(Double.parseDouble(tVDiscount.getText().toString().substring(1)));
            order.setTax(Double.parseDouble(tVTax.getText().toString().substring(1)));
            order.setTotal(Double.parseDouble(tVtotal.getText().toString().substring(1)));
            order.setTimestamp(System.currentTimeMillis());
            order.setStatus("pending");



            //push order to firebase
            orderRef.child(order.getOrderId()).setValue(order)
                    .addOnSuccessListener(Void ->{
                        //navigate to place order activity on success
                        Intent intent = new Intent(CheckOut.this, PlaceOrder.class);
                        intent.putExtra("orderId", order.getOrderId());
                        startActivity(intent);
                    }).addOnFailureListener(e->{
                        Toast.makeText(this, "Failed to place the order" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            ;

        }else{
            //handle the case where there is no authenticated user.
            Toast.makeText(this, "Use must be login to place an order", Toast.LENGTH_SHORT).show();
        }


    }
    public void CHECKOUTBACK(View v){
        finish();
    }


}

