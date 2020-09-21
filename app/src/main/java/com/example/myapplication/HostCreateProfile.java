package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HostCreateProfile extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;

    EditText hotelName, hotelAddress, cityHotel, countryHotel, hotelOwner
            , phoneNumber, emailHotel;

    Spinner  openHour, openMin, openHour2, openMin2, checkInHour, checkinMin;

    String name, address, city, country, owner, phone,
            email, openH1, openM1, openH2, openM2, check1, check2, uid;

    ArrayAdapter<String> hours, mins;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_create_hotel_profile);

        //declare and initialize
        declarationPoint();
        hotelOwner.setError("If the owner is empty then the name of the current user will be set as the owner!");
    }

    private void getText(){
        uid = mUser.getUid();

        name = hotelName.getText().toString().trim();
        address = hotelAddress.getText().toString().trim();
        city = cityHotel.getText().toString().trim();
        country = countryHotel.getText().toString().trim();
        owner = hotelOwner.getText().toString().trim();
        phone = phoneNumber.getText().toString().trim();
        email = emailHotel.getText().toString().trim();

        openH1 = openHour.getSelectedItem().toString();
        openM1 = openMin.getSelectedItem().toString();
        openH2 = openHour2.getSelectedItem().toString();
        openM2 = openMin2.getSelectedItem().toString();
        check1 = checkInHour.getSelectedItem().toString();
        check2 = checkinMin.getSelectedItem().toString();

        if(name.isEmpty()){
            hotelName.setError("Name of the hotel is required");
            hotelName.requestFocus();
            return ;
        }

        if(address.isEmpty()){
            hotelAddress.setError("Location of the hotel");
            hotelAddress.requestFocus();
            return ;
        }

        if(city.isEmpty()){
            cityHotel.setError("Need to have city");
            cityHotel.requestFocus();
            return ;
        }

        if(country.isEmpty()){
            countryHotel.setError("Country is required.");
            countryHotel.requestFocus();
            return ;
        }

        if(phone.isEmpty()){
            phoneNumber.setError("Phone number is required");
            phoneNumber.requestFocus();
            return ;
        }

        //if the user did change or set the opening time then make the default time to be 00:00 -> 23:59 (meaning open 24/7)
        if (openH1.equals(openH2) && openM1.equals(openM2) && openH1.equals("00") && openM1.equals("00")){
            openHour2.setSelection(23);
            openMin2.setSelection(59);
        }

        if (owner.isEmpty()){
            myRef.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Person person = dataSnapshot.getValue(Person.class);
                    String fullName = person.getFirstName() + " " +  person.getLastName();
                    owner = fullName;
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void declarationPoint(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        hotelName = (EditText) findViewById(R.id.hotelName);
        hotelAddress = (EditText) findViewById(R.id.hotelAddress);
        cityHotel = (EditText) findViewById(R.id.city);
        countryHotel = (EditText) findViewById(R.id.country);
        hotelOwner = (EditText) findViewById(R.id.hotelOwner);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        emailHotel = (EditText) findViewById(R.id.email);

        openHour = (Spinner) findViewById(R.id.OpenHour);
        openMin = (Spinner) findViewById(R.id.OpenMin);
        openHour2 = (Spinner) findViewById(R.id.OpenHour2);
        openMin2 = (Spinner) findViewById(R.id.OpenMin2);
        checkInHour = (Spinner) findViewById(R.id.checkinTimeHours);
        checkinMin = (Spinner) findViewById(R.id.checkinTimeMin);

        findViewById(R.id.backB).setOnClickListener(this);
        findViewById(R.id.createB).setOnClickListener(this);

        hours = new ArrayAdapter<String>(HostCreateProfile.this, android.R.layout.simple_list_item_1
        ,getResources().getStringArray(R.array.hours24));

        mins = new ArrayAdapter<String>(HostCreateProfile.this, android.R.layout.simple_list_item_1
                ,getResources().getStringArray(R.array.min60));

        hours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mins.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        openHour.setAdapter(hours);
        openHour2.setAdapter(hours);
        checkInHour.setAdapter(hours);

        openMin.setAdapter(mins);
        openMin2.setAdapter(mins);
        checkinMin.setAdapter(mins);
    }

    private void saveInput(){
        getText();

        final HotelProfile hotelProfile = new HotelProfile(name, address, city, country, owner, phone, email,
                openH1, openH2, openM1, openM2, check1, check2, uid);

        myRef.child("Hotel").child(uid).child("Detail").setValue(hotelProfile);

        hotelName.setText(""); hotelAddress.setText(""); cityHotel.setText("");
        countryHotel.setText(""); hotelOwner.setText(""); phoneNumber.setText("");
        emailHotel.setText("");

        openHour.setSelection(0); openHour.setSelection(0); openMin.setSelection(0);
        openMin2.setSelection(0);checkInHour.setSelection(0);checkinMin.setSelection(0);

        Toast.makeText(this, "hotelProfile created", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createB:
                saveInput();
                startActivity(new Intent(HostCreateProfile.this, HostCreateHotel.class));
                Toast.makeText(this, "Created Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.backB:
                startActivity(new Intent(HostCreateProfile.this, HostCreateHotel.class));
                break;

        }
    }
}






















