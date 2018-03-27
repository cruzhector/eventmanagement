package com.example.kolip.timezup;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class custommessage extends AppCompatActivity {


    private List<String> con = new ArrayList<String>();
    private List<String> name1=new ArrayList<>();
    TextView t1,t2,t3;
    EditText e1;
    AutoCompleteTextView at1;
    RelativeLayout r1;
LinearLayout l1,l2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custommessage);
        t1=(TextView)findViewById(R.id.set1);
        t2=(TextView)findViewById(R.id.appname);

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

                            }


                        }
                    }while (contact.moveToNext());{
                        contact.close();
                    }
                }


            }

        }



    }
}