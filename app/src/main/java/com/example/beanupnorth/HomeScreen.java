package com.example.beanupnorth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
public class HomeScreen extends AppCompatActivity implements FoodAdapter.onAddtoCartListener {
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<FoodItem> foodList;
    TextView cBadge;

    /////////
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    TextView tviewProduct;
    DatabaseReference dbRef;
    ArrayList<CartItem> cartItems = new ArrayList<>();


    SearchView searchView;

    private final ActivityResultLauncher<Intent> cartLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result ->{
        if(result.getResultCode()==RESULT_OK && result.getData()!=null){
            ArrayList<CartItem> removedItems = result.getData().getParcelableArrayListExtra("removedItems");

            if(removedItems!=null && !removedItems.isEmpty()){

                for(CartItem removedItem : removedItems){
                    updateFoodItemQuantity(removedItem);
                }
            }

        }
    });

    private void updateFoodItemQuantity(CartItem removedItem){
        //Remove the item from the foodlist(for UI update).
        for(FoodItem foodItem : foodList){
            if(foodItem.getName().equals(removedItem.getName())){
                foodItem.setQuantity(0);
                break;
            }
        }
        //Remove the item from CartItem based on removedItem for tracking
        for(int x = 0; x < cartItems.size(); x++){
            if(cartItems.get(x).getName().equals(removedItem.getName())){
                cartItems.remove(x);
                break;
            }
        }
        //Update the badge after remove
        cBadge.setText(String.valueOf(cartItems.size()));
        foodAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        cBadge = findViewById(R.id.cartBadge);

        cBadge.setText(cartItems.size()+"");
    recyclerView = findViewById(R.id.recycleView);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    searchView = findViewById(R.id.searchView);
    //Sample Data
        foodList = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference("products");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear first the items of Arraylist before fetchinh new data
                foodList.clear();

                //fetch data from firebase
                for(DataSnapshot items: snapshot.getChildren()){
                    if(!items.hasChild("prod_price")){
                        Log.e("Firebase","Missing prod_price field for "+items.getKey());
                        continue;
                    }

                    String name = items.child("prod_name" ).getValue(String.class);
                    String description = items.child("prod_desc").getValue(String.class);
                    String type = items.child("prod_type").getValue(String.class);
                    Long longprice = items.child("prod_price").getValue(Long.class);
                    int price = (longprice!=null)?longprice.intValue():0;
                    String imgUrl = items.child("image_url").getValue(String.class);
                    foodList.add(new FoodItem( name,description, type, price, imgUrl));
                }

                foodAdapter = new FoodAdapter(HomeScreen.this, foodList,  HomeScreen.this);
                Log.d("FOODLIST",foodList.size()+"");
                recyclerView.setAdapter(foodAdapter);
                    Log.d("FOOFLIST ONDATA CHANGE",foodList.size()+"");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        foodAdapter.getFilter().filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        foodAdapter.getFilter().filter(newText);
                        return false;

                    }

                });
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    public void cofeeButton(View v){
            String query = "coffee";
            searchView.setQuery(query,true);
    }

    public void cakeButton(View v){
        String query = "cake";
        searchView.setQuery(query,true);
    }

    public void breadsButton(View V){
        String query = "bread";
        searchView.setQuery(query,true);
    }

    public void packagesButton(View v){
        String query = "package";
        searchView.setQuery(query,true);
    }

    public void openCart(View v){
            if(!(cartItems.size()<1)){
                Intent intent = new Intent(HomeScreen.this, Cart.class);

                intent.putExtra("cartItems", cartItems);
                cartLauncher.launch(intent);
            }else{
                Toast.makeText(this, "You have no item into your cart", Toast.LENGTH_SHORT).show();
            }





    }

    @Override
    public void onAddtoCart(FoodItem foodItem) {
        //Checking items if existed already
        for(CartItem item : cartItems){
            if(item.getName().equals(foodItem.getName())){
                item.setQuantity(foodItem.getQuantity());
                Toast.makeText(this, foodItem.getName()+" is added into you cart", Toast.LENGTH_SHORT).show();

                return;
            }
        }


        cartItems.add(new CartItem(foodItem.getName(), foodItem.getPrice(), foodItem.getQuantity(), foodItem.getImgUrl()));
        cBadge.setText(cartItems.size()+"");
    }

    public void ONCLICKMYORDERS(View v){
        startActivity(new Intent(HomeScreen.this, MyOrder.class));
    }

//    public void logout(View v){
//
//        Intent intent = new Intent(HomeScreen.this, Login.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
//    }

    public void ONCLICKMAP(View v){
        startActivity(new Intent(HomeScreen.this, Maps.class));
    }

    public void GOINGTORATINGS(View v){
        startActivity(new Intent(HomeScreen.this, RatingsActivity.class));
    }
}