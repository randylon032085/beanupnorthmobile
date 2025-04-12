package com.example.beanupnorth;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.RectF;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    ImageView imageLogo;
    TextView slogan;
    private static final String CHANNEL_ID = "order_status_channel";// notification channel id
    Animation topanimation, bottomnanimation;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        DatabaseReference orderChange = FirebaseDatabase.getInstance().getReference("Ratings");
        orderChange.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Dylan", "changes");


                sendStatusNotificastion("Hello", "RATINFSSSS");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Initialized firebase
        FirebaseApp.initializeApp(this);

        //get the fcm token and log it for now
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(!task.isSuccessful()){

                Log.d("fcm","Fetching fcm registration failed",task.getException());
                return;

            }
            //get the fcm registration token
            String token = task.getResult();
            Log.d("fcm", "Token:" + token);
        });


        ///
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View decorView = getWindow().getDecorView();
            int UIoptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(UIoptions);
        }
        ///

        imageLogo = findViewById(R.id.imageView2);
        slogan = findViewById(R.id.tviewSlogan);
        topanimation = AnimationUtils.loadAnimation(this,R.anim.topanimation);
        bottomnanimation = AnimationUtils.loadAnimation(this,R.anim.bottomanimation);
        imageLogo.setAnimation(topanimation);
        slogan.setAnimation(bottomnanimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this  , Login.class));
                finish();
            }
        },5000);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        //request notification permission for (android +)

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)!=
                    PackageManager.PERMISSION_GRANTED)
            {
//Request permission
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.POST_NOTIFICATIONS
                }, REQUEST_NOTIFICATION_PERMISSION);
            }
        }

    }

    public void sendStatusNotificastion(String orderId, String status){
//            Log.d("Notification", "Sendig notification for order" +  orderId+"With Status: " +status);
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




        //Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.coffe)
                .setContentTitle("Order Status Updated")
                .setContentText("Your order "+orderId + " Status is now: "+status)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                ;

        //send the notification channel
        ntfManger.notify(0,builder.build());
    }

    //Handle permission request result//
    //onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults ){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode==REQUEST_NOTIFICATION_PERMISSION){

            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}