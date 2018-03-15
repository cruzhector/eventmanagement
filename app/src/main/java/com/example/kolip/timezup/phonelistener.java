package com.example.kolip.timezup;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by kolip on 16-03-2018.
 */

public class phonelistener extends PhoneStateListener {
    static boolean ring=false;
    static boolean callReceived=false;
    phonelistener context;
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        context=this;
        switch (state){
            case TelephonyManager.CALL_STATE_RINGING:
                ring =true;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                callReceived=true;
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if(ring==true&&callReceived==false)
                {

                }
                break;
        }
    }

}
