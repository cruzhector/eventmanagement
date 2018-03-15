package com.example.kolip.timezup;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sound extends AppCompatActivity {
Button b1,b2,b3;
AudioManager audioManager;
FirebaseUser firebaseUser;
FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        b1=(Button)findViewById(R.id.sil);
        b2=(Button)findViewById(R.id.loud);
        b3=(Button)findViewById(R.id.sout);
firebaseAuth=FirebaseAuth.getInstance();
firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
String email = firebaseUser.getUid();
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();



        audioManager=(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        final int media_max_volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,AudioManager.FLAG_SHOW_UI);
                audioManager.setStreamVolume(AudioManager.STREAM_RING,0,AudioManager.FLAG_SHOW_UI);
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,0,AudioManager.FLAG_SHOW_UI);
                audioManager.adjustSuggestedStreamVolume(AudioManager.ADJUST_LOWER,AudioManager.STREAM_RING,AudioManager.FLAG_SHOW_UI);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,media_max_volume,AudioManager.FLAG_SHOW_UI);
                audioManager.setStreamVolume(AudioManager.STREAM_RING,media_max_volume,AudioManager.FLAG_SHOW_UI);
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,media_max_volume,AudioManager.FLAG_SHOW_UI);

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(sound.this,login.class);
                startActivity(intent);
            }
        });

    }


}
