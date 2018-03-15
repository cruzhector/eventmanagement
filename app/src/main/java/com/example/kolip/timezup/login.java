package com.example.kolip.timezup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    CardView c;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        c = (CardView) findViewById(R.id.gsign);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    Intent inten = new Intent(login.this, MainActivity.class);
                    startActivity(inten);
                }

            }
        };


        c.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(login.this,"","Please wait",true);

                firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            Intent inten = new Intent(login.this, MainActivity.class);
                            startActivity(inten);
                        }
                        else{
                            Toast.makeText(login.this, "couldnt move in", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }

    protected void onStart() {



        firebaseAuth.addAuthStateListener(authStateListener);
        super.onStart();
    }

}
