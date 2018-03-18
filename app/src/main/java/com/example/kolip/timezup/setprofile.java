package com.example.kolip.timezup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
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
    @Override
    public void onReceive(Context context, Intent intent) {

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        maxvol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, AudioManager.FLAG_SHOW_UI);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, AudioManager.FLAG_SHOW_UI);
        audioManager.adjustSuggestedStreamVolume(AudioManager.ADJUST_LOWER, AudioManager.STREAM_RING, AudioManager.FLAG_SHOW_UI);

        SharedPreferences pref = context.getSharedPreferences("user",MODE_PRIVATE);
        message=pref.getString("message","Im in meeting");




        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");

        }
        else {
            String state = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String callerPhoneNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int states = 0;
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

                states = TelephonyManager.CALL_STATE_RINGING;

            }


            else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                states = TelephonyManager.CALL_STATE_OFFHOOK;
            }

            else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                states = TelephonyManager.CALL_STATE_IDLE;
            }
            onCallStateChanged(context, states, callerPhoneNumber);
        }
    }

    public void onCallStateChanged(Context context, int states, String callerPhoneNumber){
        if(lastState == states){

            return;
        }
        switch (states) {
            case TelephonyManager.CALL_STATE_RINGING:
                ring = true;
                savedNumber=callerPhoneNumber;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                callReceived = true;
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if (ring == true && callReceived == false ) {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(savedNumber,null,message,null,null);


                }
                break;
        }
        lastState = states;

    }
}
