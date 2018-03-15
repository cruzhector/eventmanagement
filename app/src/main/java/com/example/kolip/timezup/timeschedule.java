package com.example.kolip.timezup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class timeschedule extends AppCompatActivity {

    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeschedule);
    e1=(EditText)findViewById(R.id.num);

    }
}
