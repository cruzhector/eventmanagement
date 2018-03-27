package com.example.kolip.timezup;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kolip on 17-03-2018.
 */

public class setprofile extends BroadcastReceiver {
    static boolean ring=false;
    static boolean callReceived=false;
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    public static String savedNumber;
    public static String message;

    AudioManager audioManager;


    private int maxvol;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "started", Toast.LENGTH_SHORT).show();

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        maxvol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, AudioManager.FLAG_SHOW_UI);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustSuggestedStreamVolume(AudioManager.ADJUST_LOWER, AudioManager.STREAM_RING, AudioManager.FLAG_SHOW_UI);

        Intent intent1=new Intent(context,notificationservice.class);
        context.startService(intent1);




    }
}
