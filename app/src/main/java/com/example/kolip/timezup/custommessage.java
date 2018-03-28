package com.example.kolip.timezup;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class custommessage extends AppCompatActivity {


    private List<String> con = new ArrayList<String>();
    private List<String> name1=new ArrayList<>();
    TextView t1,t2,t3;
    EditText e1;
    ImageButton ib1,ib2;
    View parent;
    long timemillis1;
    AutoCompleteTextView at1;
    String s1,num,num1,num2,time;
    RelativeLayout r1;
String appname;
ProgressDialog progressDialog;
    String []seperate;
LinearLayout l1,l2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custommessage);

        t1=(TextView)findViewById(R.id.set1);
        t3=(TextView)findViewById(R.id.appname);
        l1=(LinearLayout)findViewById(R.id.social);
        l2=(LinearLayout)findViewById(R.id.sendmess);
        parent = findViewById(android.R.id.content);
        t2=(TextView)findViewById(R.id.appname);
        ib1=(ImageButton)findViewById(R.id.store1);
        ib2=(ImageButton)findViewById(R.id.watsapp);

        at1=(AutoCompleteTextView)findViewById(R.id.numbers);
        e1=(EditText)findViewById(R.id.textmsg);
        r1=(RelativeLayout)findViewById(R.id.t2);

        ContentResolver cr = this.getContentResolver();

        Cursor contact = cr.query(ContactsContract.RawContacts.CONTENT_URI, new String[]{ContactsContract.RawContacts._ID, ContactsContract.RawContacts.CONTACT_ID}, ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?", new String[]{"com.whatsapp"}, null);

        if (contact != null) {
            if (contact.getCount() > 0) {
                if (contact.moveToFirst()) {
                    do {

                        String whatsappContactId = contact.getString(contact.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));

                        if (whatsappContactId != null) {
                            //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID
                            Cursor whatsAppContactCursor = cr.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{whatsappContactId}, null);

                            if (whatsAppContactCursor != null) {
                                whatsAppContactCursor.moveToFirst();
                                String id = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                                String name = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String number = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                whatsAppContactCursor.close();

                                //Add Number to ArrayList
                                con.add(number);
                                name1.add(name);

                            ArrayAdapter arrayAdapter=new ArrayAdapter(custommessage.this,android.R.layout.simple_dropdown_item_1line,con);
                            at1.setAdapter(arrayAdapter);
                            at1.setThreshold(1);

                            }


                        }
                    }while (contact.moveToNext());{
                        contact.close();
                    }
                }


            }

        }



        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showHourPicker();
               l1.setVisibility(View.VISIBLE);
            }
        });

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l2.setVisibility(View.VISIBLE);
                appname="Whatsapp";
                t3.setText(appname);

            }
        });

        ib1.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {


        time=t1.getText().toString().trim();
        seperate=time.split(":");
        num=at1.getText().toString().trim();
        num1=num.replace(" ","");
        num2=num1.replace("+","");
        Log.d("tag",num2);
        s1=e1.getText().toString().trim();


if (TextUtils.isEmpty(time)){
    Snackbar.make(parent,"time field cannot be empty",Snackbar.LENGTH_SHORT).show();
}
else if(TextUtils.isEmpty(num)){
    Snackbar.make(parent,"number cannot be  empty",Snackbar.LENGTH_SHORT).show();
}
else if(TextUtils.isEmpty(num)){
    Snackbar.make(parent,"message cannot be  empty",Snackbar.LENGTH_SHORT).show();
}


else {


    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
    editor.putString("text", s1);
    editor.putString("phnum", num2);
    editor.commit();


    Calendar c3 = Calendar.getInstance();
    long maintimemillis = c3.getTimeInMillis();

    Calendar c1 = Calendar.getInstance();
    c1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(seperate[0]));
    c1.set(Calendar.MINUTE, Integer.parseInt(seperate[1]));
    timemillis1 = c1.getTimeInMillis();
    long subtime = timemillis1 - maintimemillis;

    Intent intent = new Intent(custommessage.this, send.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC_WAKEUP, maintimemillis + subtime, pendingIntent);

    Toast.makeText(custommessage.this, "you will be notified", Toast.LENGTH_SHORT).show();


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
                    t1.setText( hourOfDay + ":" + minute);

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(custommessage.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
}