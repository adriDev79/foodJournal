package com.example.appfood;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

public class MessagingEventFirebaseService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String my_message = remoteMessage.getNotification().getBody();
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "notification");
        notification.setContentTitle("super notif !!!!");
        notification.setContentText(my_message);
        notification.setSmallIcon(R.drawable.burger);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channel_id = "my canal";
            String channel_title = "my canal";
            String channel_desc = "desc";
            NotificationChannel channel = new NotificationChannel(channel_id, channel_title, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channel_desc);
            notificationManager.createNotificationChannel(channel);
            notification.setChannelId(channel_id);

        }
        notification.notify();

    }
}
