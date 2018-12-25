package com.imakancustomer.common;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.imakancustomer.R;
import com.imakancustomer.utils.SharedPref;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private SharedPref sp;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = new SharedPref(getApplicationContext());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showmessage(remoteMessage.getNotification().getBody());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        sp.setDeviceToken(s);
    }

    public void showmessage(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("Imakan Customer");
        builder.setContentText("You got a notification");
        builder.setSubText(message);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
