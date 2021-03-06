package com.example.kolip.timezup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

Button b1,b2,b3;
RelativeLayout r1;
TextView t;
FloatingActionButton fa;
    int count=0;
    android.support.v4.app.FragmentManager fragmentManager,fragmentManager1,fragmentManager2;
    android.support.v4.app.FragmentTransaction fragmentTransaction,fragmentTransaction1,fragmentTransaction2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.frag2);
        b2=(Button)findViewById(R.id.frag1);
        b3=(Button)findViewById(R.id.frag3);
        r1=(RelativeLayout)findViewById(R.id.card);
        t=(TextView)findViewById(R.id.b1);
        fa=(FloatingActionButton)findViewById(R.id.fab);


        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,prototype.class);
//                startActivity(intent);
            }
        });
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


b3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    loadfrag2(new cusmsgfrag());
    }
});

    }


public void loadfrag(timeschedfrag fragment){

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fraganim,R.anim.fragright);
        fragmentTransaction.replace(R.id.card,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

}

    public void loadfrag1(missedcallfrag fragment){

         fragmentManager1 = getSupportFragmentManager();
         fragmentTransaction1 =fragmentManager1.beginTransaction();
         fragmentTransaction1.setCustomAnimations(R.anim.fraganim,R.anim.fragright);
         fragmentTransaction1.replace(R.id.card,fragment);
         fragmentTransaction1.addToBackStack(null);
         fragmentTransaction1.commit();

    }

    public void loadfrag2(cusmsgfrag fragment){

        fragmentManager2 = getSupportFragmentManager();
        fragmentTransaction2 =fragmentManager2.beginTransaction();
        fragmentTransaction2.setCustomAnimations(R.anim.fraganim,R.anim.fragright);
        fragmentTransaction2.replace(R.id.card,fragment);
        fragmentTransaction2.addToBackStack(null);
        fragmentTransaction2.commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        count++;
        if (count==1){
            Toast.makeText(this, "Press once again to exit", Toast.LENGTH_SHORT).show();
        }

        if (count>1) {
            finishAffinity();
        }
        }


}

