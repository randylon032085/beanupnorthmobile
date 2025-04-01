package com.example.beanupnorth;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "fcmservice";
    private static final String CHANNEL_ID = "default_channel";

 @Override
    public void onMessageReceived (RemoteMessage remoteMessage){
        if(remoteMessage.getNotification()!=null){
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d(TAG,"Message notification title"+ title);
            Log.d(TAG,"Message notification body" + body);

            //Create notification channel for android Oreo and above
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                //Create notification channel
                createNotificationChannel();
            }
            //Check notification permission and show notification
            if(isNotificationPermissionGranted()){
                showNotification(title,body);
            }


        }
    }

//Create notification channel(android 8+)
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name= "Default Channel";
            String description = "Channel for general notifications";
            int importace = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importace);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.enableVibration(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    //Check if notification permission is granted (android 13+)
    private boolean isNotificationPermissionGranted(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            return ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)== PackageManager.PERMISSION_GRANTED;
        }

        return true;//Permission is not required for older version
    }

//    Show notification if permission is granted
    private void showNotification(String title, String body){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager==null)return;


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.add)//Make sure you have this icon
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);//Automatic dismissed notification when click

        notificationManager.notify(0,builder.build());
    }
    @Override
    public void onNewToken(@NonNull String token){
        Log.d(TAG,"new fcm token" + token);
    }
}
