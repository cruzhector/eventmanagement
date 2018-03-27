package com.example.kolip.timezup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kolip on 19-03-2018.
 */

public class callrecieve extends BroadcastReceiver {
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;
    static boolean ring=false;
    static boolean callReceived=false;

    public static String message;

    AudioManager audioManager;
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
        message = pref.getString("message", "Im in meeting");
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);


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
                    if ((audioManager.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE)||(audioManager.getRingerMode()==AudioManager.RINGER_MODE_SILENT)) {

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(savedNumber, null, message, null, null);
                    }

                }
                break;
        }
        lastState = states;


    }
}
