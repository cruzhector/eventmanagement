package com.example.kolip.timezup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

Button b1,b2;
RelativeLayout r1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      b1=(Button)findViewById(R.id.frag2);
        b2=(Button)findViewById(R.id.frag1);
        r1=(RelativeLayout)findViewById(R.id.card);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               loadfrag1(new missedcallfrag());

            }
        });
b2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    loadfrag(new timeschedfrag());
    }
});

}
public void loadfrag(timeschedfrag fragment){

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fraganim,R.anim.fragright);
        fragmentTransaction.replace(R.id.card,fragment);
        fragmentTransaction.commit();

}

    public void loadfrag1(missedcallfrag fragment){

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fraganim,R.anim.fragright);
        fragmentTransaction.replace(R.id.card,fragment);
        fragmentTransaction.commit();

    }

}
