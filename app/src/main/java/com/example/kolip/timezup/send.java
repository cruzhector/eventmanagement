package com.example.kolip.timezup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kolip on 28-03-2018.
 */

public class send extends BroadcastReceiver {
    NotificationManager notificationManager;
    Notification myNotication;

    String text,phn;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {


        SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
        text= pref.getString("text", "hi");
        phn=pref.getString("phnum","nonum");
        intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.putExtra("jid",phn + "@s.whatsapp.net");
        intent.setPackage("com.whatsapp");
//        try {
//            context.startActivity(intent);
//        } catch (android.content.ActivityNotFoundException ex) {
////            Snackbar.make(parent,"whatsapp is not installed",Snackbar.LENGTH_SHORT).show();
//
//        }


        notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

//        Intent intent1=new Intent(context,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        Notification.Builder builder = new Notification.Builder(context);

        builder.setAutoCancel(true);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("WhatsApp Notification");
        builder.setContentText("You have a new message");
        builder.setSmallIcon(R.drawable.android);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText("This is subtext...");   //API level 16
        builder.build();
        myNotication = builder.getNotification();
        notificationManager.notify(11, myNotication);


    }
}
