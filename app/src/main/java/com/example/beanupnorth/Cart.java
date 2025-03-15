package com.example.beanupnorth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity implements CartAdapter.OnCartItemRemovedListener{

    RecyclerView rvCart;
    CartAdapter cartAdapter;
    List<CartItem> cartItem;
    ArrayList<CartItem> removedCartItem = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.cartRecyleView);

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartItem = new ArrayList<>();

        cartItem = (ArrayList<CartItem>)getIntent().getSerializableExtra("cartItems");

        cartAdapter = new CartAdapter(cartItem,this);

        rvCart.setAdapter(cartAdapter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void BACKBUTTONG(View v){
        if(!removedCartItem.isEmpty()){
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("removedItems", removedCartItem);
            setResult(RESULT_OK,intent);
        }
        finish();
    }
    @Override
    public void onItemRemoved(CartItem removedItem) {
        if(removedItem!=null){
            removedCartItem.add(removedItem);
        }
    }


    public void CHECKOUT(View v){
        Intent intent = new Intent(Cart.this, CheckOut.class);
        intent.putExtra("cartItems", (ArrayList)cartItem);
        startActivity(intent);

    }
}