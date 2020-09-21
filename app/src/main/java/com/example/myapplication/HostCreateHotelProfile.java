package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HostCreateHotelProfile extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;
    EditText hotelName, hotelAddress, cityHotel, countryHotel, hotelOwner
            , phoneNumber, emailHotel;
    Spinner  openHour, openMin, openHour2, openMin2, checkInHour, checkinMin;
    Button createBtn, closeBtn;
    String name, address, city, country, owner, phone,
            email, openH1, openM1, openH2, openM2, check1, check2, uid;
    ArrayAdapter<String> hours, mins;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_hotel_profile, container, false);

        declarationPoint(v);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        createBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                saveInput();
                Toast.makeText(context, "Created Profile", Toast.LENGTH_SHORT).show();

                HostProfileFragment hostProfileFragment = new HostProfileFragment();
                fragmentTransaction.replace(R.id.navHostFragment, hostProfileFragment);
                fragmentTransaction.commit();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                HostProfileFragment hostProfileFragment = new HostProfileFragment();
                fragmentTransaction.replace(R.id.navHostFragment, hostProfileFragment);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

    private void declarationPoint(View v){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        hotelName = (EditText) v.findViewById(R.id.hotelName2);
        hotelAddress = (EditText) v.findViewById(R.id.hotelAddress2);
        cityHotel = (EditText) v.findViewById(R.id.city2);
        countryHotel = (EditText) v.findViewById(R.id.country2);
        hotelOwner = (EditText) v.findViewById(R.id.hotelOwner2);
        phoneNumber = (EditText) v.findViewById(R.id.phoneNumber2);
        emailHotel = (EditText) v.findViewById(R.id.email2);

        openHour = (Spinner) v.findViewById(R.id.OpenHour12);
        openMin = (Spinner) v.findViewById(R.id.OpenMin12);
        openHour2 = (Spinner) v.findViewById(R.id.OpenHour22);
        openMin2 = (Spinner) v.findViewById(R.id.OpenMin22);
        checkInHour = (Spinner) v.findViewById(R.id.checkinTimeHours12);
        checkinMin = (Spinner) v.findViewById(R.id.checkinTimeMin12);

        createBtn = v.findViewById(R.id.createB2);
        closeBtn = v.findViewById(R.id.backB2);

        context = v.getContext();

        hours = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1
                ,getResources().getStringArray(R.array.hours24));

        mins = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1
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
            openH2 = openHour2.getSelectedItem().toString();
            openM2 = openMin2.getSelectedItem().toString();
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

        Toast.makeText(context, "hotelProfile created", Toast.LENGTH_LONG).show();

    }
}