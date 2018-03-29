package com.example.kolip.timezup;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

public class timeschedule extends AppCompatActivity {

    EditText e1;
    ImageButton ib1;
    ListView lv;
    String n;
    int n1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeschedule);

        e1=(EditText)findViewById(R.id.num);
        ib1=(ImageButton)findViewById(R.id.listadd);
        lv=(ListView)findViewById(R.id.timelist);

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             n=e1.getText().toString().trim();
             try {
                 n1=Integer.parseInt(n);
             }catch (Exception e){
                 Log.d("tag", String.valueOf(e));
             }

            lv.setAdapter(new timeslotcus(timeschedule.this,n1));

            }
        });


    }
}
