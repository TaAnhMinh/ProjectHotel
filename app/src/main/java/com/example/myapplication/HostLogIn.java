package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HostLogIn extends AppCompatActivity {

    EditText editTextUserName, editTextPassword;
    FirebaseAuth mAuth;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_sign_in);

        editTextUserName = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);

        Button signIn = findViewById(R.id.log);
        Button register = findViewById(R.id.register);

        signIn.setOnClickListener(v -> {
            String username = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty()){
                if (username.isEmpty()){
                    editTextUserName.setError("Email is required");
                    editTextUserName.requestFocus();
                    return;
                }
                else {
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                    return;
                }
            } else {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(HostLogIn.this,
                        task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(HostLogIn.this, "Success!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(HostLogIn.this, HostMainScreen.class));
                            } else {
                                Toast.makeText(HostLogIn.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        register.setOnClickListener(v -> startActivity(new Intent(HostLogIn.this, HostCreate.class)));
    }
}
