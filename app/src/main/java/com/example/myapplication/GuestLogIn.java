package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class GuestLogIn extends AppCompatActivity implements View.OnClickListener {
    EditText UserName, Password;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginguest);

        findViewById(R.id.signIn).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);

        UserName = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
    }

    public void checkLogin(final View view) {
        String username = UserName.getText().toString();
        String password = Password.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            if (username.isEmpty()) {
                UserName.setError("Email is required");
                UserName.requestFocus();
                return;
            } else {
                Password.setError("Password is required");
                Password.requestFocus();
                return;
            }
        } else {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(GuestLogIn.this,
                    task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(GuestLogIn.this, "Success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(GuestLogIn.this, GuestScreen.class));
                        } else {
                            Toast.makeText(GuestLogIn.this, "Wrong password or username, please re-enter.", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(GuestLogIn.this, GuestCreate.class));
                break;
            case R.id.signIn:
                checkLogin(v);
                break;
        }
    }
}
