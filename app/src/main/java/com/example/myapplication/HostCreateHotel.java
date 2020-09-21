package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HostCreateHotel extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hotel);

        findViewById(R.id.createProfile).setOnClickListener(this);
        findViewById(R.id.createRoomBtn).setOnClickListener(this);
        findViewById(R.id.createServiceBtn).setOnClickListener(this);
        findViewById(R.id.SignOut).setOnClickListener(this);
        findViewById(R.id.skip).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createProfile:
                startActivity(new Intent(this, HostCreateProfile.class));
                break;
            case R.id.createRoomBtn:
                startActivity(new Intent(this, HostCreateRoom.class));
                break;
            case R.id.createServiceBtn:
                startActivity(new Intent(this, HostCreateService.class));
                break;
            case R.id.skip:
                startActivity(new Intent(this, HostMainScreen.class));
                break;
            case R.id.SignOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                break;
        }
    }
}
