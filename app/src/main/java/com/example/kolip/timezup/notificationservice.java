package com.example.kolip.timezup;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by kolip on 19-03-2018.
 */

public class notificationservice extends IntentService {

    Notification notification;

    public notificationservice() {
        super("notificationservice");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {



        Notification.Builder builder=new Notification.Builder(this);
        builder.setAutoCancel(true);
        builder.setTicker("Scheduled your meeting");
        builder.setContentTitle("Timezup notification");
        builder.setContentText("Scheduled");
        builder.setSmallIcon(R.drawable.ic_clock);
        Intent intent1=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,2,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        notification=builder.build();
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(3,notification);


    }
}
