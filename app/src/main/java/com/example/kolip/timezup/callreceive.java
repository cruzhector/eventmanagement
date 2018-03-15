package com.example.kolip.timezup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by kolip on 15-03-2018.
 */

public class callreceive extends BroadcastReceiver {

    static boolean ring=false;
    static boolean callReceived=false;
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
public static String savedNumber;
private FirebaseFirestore firebaseFirestore;
private FirebaseUser firebaseUser;
private FirebaseAuth firebaseAuth;
private String id;
public static String message;
DocumentReference documentReference;
    @Override
    public void onReceive(Context context, Intent intent) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        id=firebaseUser.getUid();
        documentReference=firebaseFirestore.collection("user").document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    message=documentSnapshot.getString("message");
                }

            }
        });

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

//                if (ring == true && callReceived == false) {
//                    Toast.makeText(context, "It was A MISSED CALL from : " + callerPhoneNumber, Toast.LENGTH_LONG).show();
//                }
                states = TelephonyManager.CALL_STATE_IDLE;
            }
            onCallStateChanged(context, states, callerPhoneNumber);
        }
    }
//    String ms = e1.getText().toString();

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
                if (ring == true && callReceived == false) {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(savedNumber,null,message,null,null);


                }
                break;
        }
        lastState = states;
    }
}
