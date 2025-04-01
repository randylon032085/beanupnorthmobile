package com.example.beanupnorth;

import android.Manifest;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    ImageView imageLogo;
    TextView slogan;

    Animation topanimation, bottomnanimation;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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