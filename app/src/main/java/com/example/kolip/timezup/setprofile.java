package com.example.kolip.timezup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

/**
 * Created by kolip on 17-03-2018.
 */

public class setprofile extends BroadcastReceiver {
    AudioManager audioManager;
    private int maxvol;
    @Override
    public void onReceive(Context context, Intent intent) {

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        maxvol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, AudioManager.FLAG_SHOW_UI);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustSuggestedStreamVolume(AudioManager.ADJUST_LOWER, AudioManager.STREAM_RING, AudioManager.FLAG_SHOW_UI);


    }
}
