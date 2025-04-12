package com.example.beanupnorth;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyOrder extends AppCompatActivity {

    RecyclerView rvOrder;
    OrderItemAdapter orderItemAdapter;

    private static final String CHANNEL_ID = "order_status_channel";// notification channel id
    private Map<String,String> orderStatusMap = new HashMap<>();//to track status of each order
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_order);
        rvOrder = findViewById(R.id.rv_MyOrder);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        FetchOrder();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
    public void ONCLICKRETURNHOME(View v){
        startActivity(new Intent(MyOrder.this, HomeScreen.class));
    }
    public void FetchOrder (){
        Log.d("Firebase Orders","Fetch ordered called");
        //getting current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            Log.e("FirebaseOrder", "No Authenticated user found");
            return;
        }

        //Getting user email from current user.
        String userEmail = currentUser.getEmail();
        if(userEmail == null){
            Log.e("FirebaseOrder", "User email is null");
            return;
        }

        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("orders");

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseOrder", "FetchingOrders");
                List<ORDERS> orders = new ArrayList<>();
                //retrieving first leve data
                for(DataSnapshot firstLevel : snapshot.getChildren()){
                  //getting customer id from second level
                    String customerId = firstLevel.child("customerId").getValue(String.class);
                    String orderId = firstLevel.child("orderId").getValue(String.class);
                    Double total = firstLevel.child("total").getValue(Double.class);
                    String date = firstLevel.child("date").getValue(String.class);
                    String status = firstLevel.child("status").getValue(String.class);
                    Long timestamp = firstLevel.child("timestamp").getValue(Long.class);

                    Log.d("Firstlevel",""+customerId+orderId+total+date+status);
                    //checking customerid if matched with the user email.
                    if(customerId!=null && customerId.equals(userEmail)){
                        List<OrderItem> orderItems = new ArrayList<>();
                       //assuming there is an item is node that contains order items
                       for(DataSnapshot itemSnapShot : firstLevel.child("item").getChildren()){
                             OrderItem item = itemSnapShot.getValue(OrderItem.class);
                             Log.d("FirebaseOrder", "Fetching Items");
                             if(item!=null){
                                 orderItems.add(item);
                                 Log.d("FirebaseOrder", "added: "+ orderItems.get(0));
                             }
                       }

                        ORDERS Orders = new ORDERS();
                        Orders.setStatus(status);
                        Orders.setOrderId(orderId);
                        Orders.setTotal(total);
                        Orders.setDate(date);
                        Orders.setItem(orderItems);
                        Orders.setTimestamp(timestamp);

                        //check if timestamp is null
                        if(timestamp==null){
                            timestamp = 0L; // default value
                        }
                        //adding orders to order
                        orders.add(Orders);

                        //check if order status has change
                        //1.Pending
                        //2.Preparing
                        //3.Completed

                        if(status!=null && !status.equals(orderStatusMap.get(orderId))){
                            //send notification if the status has change
                            if(status.equals("pending")){
                                sendStatusNotificastion(orderId,status);
                            }else if(status.equals("preparing"))
                            {
                                sendStatusNotificastion(orderId,status);
                            }else if(status.equals("complete"))
                            {
                                sendStatusNotificastion(orderId,status);
                                Toast.makeText(MyOrder.this, "Complete", Toast.LENGTH_SHORT).show();
                                TemporaryVariables.qrCodeBtn="RATE US";
                            }

                                //update status in the map
                            orderStatusMap.put(orderId,status);
                        }
                    }

                }

                //Checking if order item is empty
                if(orders.isEmpty()){
                    Log.d("FirebaseOrder","No order found for this user");
                }

                //Ensure recycleview update the main thread.
                runOnUiThread(()->{
                    orderItemAdapter = new OrderItemAdapter(orders,MyOrder.this);
                    rvOrder.setAdapter(orderItemAdapter);
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to read data", error.toException());
            }

        });



    }

    public void sendStatusNotificastion(String orderId, String status){
        Log.d("Notification", "Sendig notification for order" +  orderId+"With Status: " +status);
        //create notification manager
        NotificationManager ntfManger = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //Create notification channel for devices running android Oreo and above.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Order Status Channel";
            String description = "Notification For Order Status Update";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            ntfManger.createNotificationChannel(channel);

        }

        String transform = transformId(orderId);
        //Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.coffe)
                .setContentTitle("Order Status Updated")
                .setContentText("Your order "+transform+" Status is now: "+status)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                ;

        //send the notification channel
        ntfManger.notify(0,builder.build());
    }
    public static String transformId(String input){
        return input.length()>=6?input.substring(0,6):input;

    };

}