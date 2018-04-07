package com.example.kolip.timezup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class timeschedule extends AppCompatActivity {

    EditText e1;
    TextView t1,t2,t3;
    RelativeLayout rr1,rr2,rr3;
    ImageButton ib1,ib2,ib3;
    String tm1,tm2;
    String []sep1;
    String []sep2;
    Date tim1;
    Date tim2;
    long timemillis1,timemillis2,maintimemillis,subtime,subtime1;
    List<Long> list1= new ArrayList<>();
    List<Long> list2=new ArrayList<>();
    List<PendingIntent> penlist=new ArrayList<PendingIntent>();
    List<PendingIntent> penlist1=new ArrayList<PendingIntent>();

    PendingIntent pendingIntent1,pendingIntent2;
    public int count=0;
    public int set=1;
    String n;
    AlarmManager [] alarmManager;
    AlarmManager [] alarmManager1;
    int n1;
    View parentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeschedule);

        e1=(EditText)findViewById(R.id.num);
        ib1=(ImageButton)findViewById(R.id.listadd);
        ib2=(ImageButton)findViewById(R.id.list1);
        ib3=(ImageButton)findViewById(R.id.setalrm);

        parentLayout = findViewById(android.R.id.content);

        t1=(TextView) findViewById(R.id.sett1);
        t2=(TextView)findViewById(R.id.sett2);
        t3=(TextView)findViewById(R.id.cnt);

        rr1=(RelativeLayout)findViewById(R.id.t12);
        rr2=(RelativeLayout) findViewById(R.id.t22);
        rr3=(RelativeLayout) findViewById(R.id.vis);


        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                n=e1.getText().toString().trim();

           if (TextUtils.isEmpty(n)){
               Toast.makeText(timeschedule.this, "slot field is empty", Toast.LENGTH_SHORT).show();
           }
           else {


             try {
                 n1=Integer.parseInt(n);
                 rr3.setVisibility(View.VISIBLE);
                 ib2.setVisibility(View.VISIBLE);
                 t3.setVisibility(View.VISIBLE);

             }catch (Exception e){
                 Log.d("tag", String.valueOf(e));
             }
           }
            }
        });

rr1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        timer1();
    }
});

rr2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        timer2();
    }
});

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tm1=t1.getText().toString().trim();
                tm2=t2.getText().toString().trim();
                sep1=tm1.split(":");
                sep2=tm2.split(":");
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
                try {
                    tim1=sdf.parse(tm1);
                    tim2=sdf.parse(tm2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (TextUtils.isEmpty(tm1) || TextUtils.isEmpty(tm2)){
                    Toast.makeText(timeschedule.this, "check times", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.equals(tm1,tm2)){
                    Toast.makeText(timeschedule.this, "check times", Toast.LENGTH_SHORT).show();
                }
                else if (tim1.compareTo(tim2)>0){
                    Toast.makeText(timeschedule.this, "check times", Toast.LENGTH_SHORT).show();
                }
                else if(list1.contains(tm1)){
                    Toast.makeText(timeschedule.this, "time already exists", Toast.LENGTH_SHORT).show();
                }
                else if (list2.contains(tm2)){
                    Toast.makeText(timeschedule.this, "time already exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    count++;
                    set++;
                    if(count<=n1){

                            compute();

                            t1.setText("");
                            t2.setText("");
                            t3.setText(String.valueOf(set));
                        Toast.makeText(timeschedule.this, "select slot"+" "+set, Toast.LENGTH_SHORT).show();
                            if (count==n1){
                                rr3.setVisibility(View.GONE);
                                ib2.setVisibility(View.GONE);
                                t3.setVisibility(View.GONE);
                            }


                    }
                else {
                        Snackbar.make(parentLayout,"No more slots available",Snackbar.LENGTH_SHORT).show();
                    }

                }

            }
        });


    ib3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Toast.makeText(timeschedule.this, "have a nice day", Toast.LENGTH_SHORT).show();

            setalrm();


        }
    });

    }


    public void compute(){
        Calendar c1=Calendar.getInstance();
        c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sep1[0]));
        c1.set(Calendar.MINUTE, Integer.parseInt(sep1[1]));
        timemillis1=c1.getTimeInMillis();


        Calendar c2=Calendar.getInstance();
        c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sep2[0]));
        c2.set(Calendar.MINUTE, Integer.parseInt(sep2[1]));
        timemillis2=c2.getTimeInMillis();

        Calendar c3=Calendar.getInstance();
        maintimemillis=c3.getTimeInMillis();

        subtime=timemillis1-maintimemillis;
        subtime1=timemillis2-maintimemillis;

        list1.add(subtime);
        list2.add(subtime1);

        Log.d("tag", String.valueOf(list1));
        Log.d("tag", String.valueOf(list2));
    }

    public void timer1() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                    t1.setText( hourOfDay + ":" + minute);

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(timeschedule.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }


    public void timer2() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                    t2.setText( hourOfDay + ":" + minute);

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(timeschedule.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }


    public void setalrm(){


   alarmManager = new AlarmManager[n1];
        alarmManager1 = new AlarmManager[n1];

        for (int i=0;i<list1.size();i++){
            Intent intent=new Intent(timeschedule.this,setprofile.class);
            pendingIntent1=PendingIntent.getBroadcast(getApplicationContext(),i,intent,0);
            alarmManager[i]=(AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager[i].set(AlarmManager.RTC_WAKEUP,maintimemillis+list1.get(i),pendingIntent1);
            penlist.add(pendingIntent1);


            Intent intent1=new Intent(timeschedule.this,cancelprofile.class);
            intent1.putExtra("key",pendingIntent1);
            pendingIntent2=PendingIntent.getBroadcast(getApplicationContext(),i,intent1,0);
            alarmManager1[i]=(AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager1[i].set(AlarmManager.RTC_WAKEUP,maintimemillis+list2.get(i),pendingIntent2);
            penlist1.add(pendingIntent2);


        }








    }






}
