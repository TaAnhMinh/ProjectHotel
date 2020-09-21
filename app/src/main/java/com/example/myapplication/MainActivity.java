package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.GuestSignIn).setOnClickListener(this);
        findViewById(R.id.UserSignIn).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.GuestSignIn:
                startActivity(new Intent(MainActivity.this, GuestLogIn.class ));
                break;
            case R.id.UserSignIn:
                startActivity(new Intent(MainActivity.this, HostLogIn.class));
                break;
        }
    }
}
