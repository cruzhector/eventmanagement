package com.example.kolip.timezup;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class missedcall extends AppCompatActivity {
ImageButton b,b1;

EditText e1;
TextView tv1,tv2;
    Date tm1;
    Date tm2;
CardView c1;
String time1,time2;
    static boolean ring = false;
    static boolean callReceived = false;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    RelativeLayout rl1,rl2;
    long timemillis1,timemillis2,maintimemillis;
    String []seperate;
    String []seperate1;
    long subtime,subtime1;
String id,mess;

    private NotificationManager mNotificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missedcall);

        b = (ImageButton) findViewById(R.id.setpro);

        b1 = (ImageButton) findViewById(R.id.store);
        e1=(EditText)findViewById(R.id.mess);

        c1 = (CardView) findViewById(R.id.setprocard1);
        rl1=(RelativeLayout)findViewById(R.id.t1);
        rl2=(RelativeLayout)findViewById(R.id.t2);
        tv1=(TextView)findViewById(R.id.settm1);
        tv2=(TextView)findViewById(R.id.settm2);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        id=firebaseUser.getUid();

        e1.setText("im in a meeting call u back later");

        mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c1.setVisibility(View.VISIBLE);
                b.setVisibility(View.GONE);
            }
        });








        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHourPicker1();

            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showHourPicker();

            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        mess=e1.getText().toString().trim();
        time1=tv1.getText().toString();
        time2=tv2.getText().toString();
        seperate=time1.split(":");
        seperate1=time2.split(":");
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        try {
            tm1=sdf.parse(time1);
            tm2=sdf.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

            if (TextUtils.isEmpty(time1)||TextUtils.isEmpty(time2)){
            Toast.makeText(missedcall.this, "time fields are empty", Toast.LENGTH_SHORT).show();
        }
            else if(tm1.compareTo(tm2)>0){
                Toast.makeText(missedcall.this, "in time is more than out time", Toast.LENGTH_SHORT).show();
        }
            else if (tm1.equals(tm2)){
                Toast.makeText(missedcall.this, "Times cannot be equal", Toast.LENGTH_SHORT).show();
            }
            else  if (TextUtils.isEmpty(mess)){
                Toast.makeText(missedcall.this, "Message box is empty", Toast.LENGTH_SHORT).show();
            }
            else {

                SharedPreferences.Editor editor=getSharedPreferences("user",MODE_PRIVATE).edit();
                editor.putString("message",mess);
                editor.commit();

    timer();

}
    }
});
    }


    public void showHourPicker() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                    tv2.setText( hourOfDay + ":" + minute);

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(missedcall.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }



    public void showHourPicker1() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                    tv1.setText( hourOfDay + ":" + minute);

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(missedcall.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

public void timer(){
    Calendar c1=Calendar.getInstance();
    c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(seperate[0]));
    c1.set(Calendar.MINUTE, Integer.parseInt(seperate[1]));
    timemillis1=c1.getTimeInMillis();


    Calendar c2=Calendar.getInstance();
    c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(seperate1[0]));
    c2.set(Calendar.MINUTE, Integer.parseInt(seperate1[1]));
    timemillis2=c2.getTimeInMillis();

    Calendar c3=Calendar.getInstance();
    maintimemillis=c3.getTimeInMillis();

    subtime=timemillis1-maintimemillis;
    subtime1=timemillis2-maintimemillis;

    Log.d("tag", String.valueOf(timemillis1));
    Log.d("tag", String.valueOf(timemillis2));
    Log.d("tag", String.valueOf(maintimemillis));
    Log.d("tag", String.valueOf(subtime));
    Log.d("tag", String.valueOf(subtime1));
    Log.d("tag", seperate[0]);
    Log.d("tag", seperate[1]);



    Intent intent = new Intent(missedcall.this, setprofile.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2343, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC_WAKEUP, maintimemillis+subtime, pendingIntent);


    Intent intent1 = new Intent(missedcall.this, cancelprofile.class);
    intent1.putExtra("key",pendingIntent);
    PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 2343, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
    alarmManager.set(AlarmManager.RTC_WAKEUP,maintimemillis+subtime1,pendingIntent1);

    Toast.makeText(this, "Scheduled at "+" "+time1, Toast.LENGTH_SHORT).show();
    Toast.makeText(this, "will cancel at "+" "+time2, Toast.LENGTH_SHORT).show();


}

}
