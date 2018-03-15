package com.example.kolip.timezup;

import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class missedcall extends AppCompatActivity {
ImageButton b,b1;
RadioButton r1,r2,r3;
EditText e1;
CardView cardView;
    static boolean ring = false;
    static boolean callReceived = false;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
String id,mess;
    AudioManager audioManager;
    private int maxvol;
    private NotificationManager mNotificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missedcall);

        b = (ImageButton) findViewById(R.id.setpro);
        b1 = (ImageButton) findViewById(R.id.store);
        e1=(EditText)findViewById(R.id.mess);
        r1 = (RadioButton) findViewById(R.id.radmet);
        r2 = (RadioButton) findViewById(R.id.raddrv);
        r3 = (RadioButton) findViewById(R.id.radplay);
        cardView = (CardView) findViewById(R.id.setprocard);
firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        id=firebaseUser.getUid();


        mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        maxvol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(View.VISIBLE);
            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                r2.setChecked(false);
                r3.setChecked(false);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
                audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, AudioManager.FLAG_SHOW_UI);
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, AudioManager.FLAG_SHOW_UI);
                audioManager.adjustSuggestedStreamVolume(AudioManager.ADJUST_LOWER, AudioManager.STREAM_RING, AudioManager.FLAG_SHOW_UI);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

            }
        });



b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        mess=e1.getText().toString().trim();
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("message",mess);
        firebaseFirestore.collection("user").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(missedcall.this, "Your Message is successfully stored", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(missedcall.this, "try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
});
    }




}
