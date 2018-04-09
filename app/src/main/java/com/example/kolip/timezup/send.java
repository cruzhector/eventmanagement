package com.example.kolip.timezup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kolip on 28-03-2018.
 */

public class send extends BroadcastReceiver {
    NotificationManager notificationManager;
    Notification myNotication;

    String text,phn,path;
    String appname;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
appname=intent.getExtras().getString("appname");
if (appname.equals("whatsapp")) {

    text = pref.getString("text", "hi");
    phn = pref.getString("phnum", "nonum");
    intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, text);
    intent.putExtra("jid", phn + "@s.whatsapp.net");
    intent.setPackage("com.whatsapp");
}
else if (appname.equals("instagram")){


    path=pref.getString("path","no path");
    File file=new File(path);
    Uri uri=Uri.fromFile(file);

    intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);
    intent.setType("image/*");
    intent.putExtra(Intent.EXTRA_STREAM,uri);
    intent.setPackage("com.instagram.android");

}

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
        builder.setContentTitle("Timezzup Notification");
        builder.setContentText(appname);
        builder.setSmallIcon(R.drawable.ic_stopwatch);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText("Click on the notification");   //API level 16
        builder.build();
        myNotication = builder.getNotification();
        notificationManager.notify(11, myNotication);


    }
}
