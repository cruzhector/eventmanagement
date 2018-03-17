package com.example.kolip.timezup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

/**
 * Created by kolip on 17-03-2018.
 */

public class cancelprofile extends BroadcastReceiver {
    AudioManager audioManager;
    private int maxvol;
    @Override
    public void onReceive(Context context, Intent intent) {

        PendingIntent pendingIntent=intent.getParcelableExtra("key");
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        maxvol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,maxvol,AudioManager.FLAG_SHOW_UI);
        audioManager.setStreamVolume(AudioManager.STREAM_RING,maxvol,AudioManager.FLAG_SHOW_UI);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,maxvol,AudioManager.FLAG_SHOW_UI);


    }
}
