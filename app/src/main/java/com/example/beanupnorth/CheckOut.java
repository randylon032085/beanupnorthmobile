package com.example.beanupnorth;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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

    public void CHECKOUTBACK(View v){
        finish();
    }
}