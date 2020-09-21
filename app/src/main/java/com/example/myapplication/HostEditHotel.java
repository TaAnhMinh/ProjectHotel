package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class HostEditHotel extends Fragment {
    TextView hotelname, address, owner, phone, openC, closeC, checkinC;
    Spinner openH, openM, closeH, closeM, checkH, checkM;
    EditText nameNew, addressN, ownerN, phoneN;
    private CheckBox cbWorkingTime, cbCheckinTime;

    String name, addressE, ownerE, phoneE, oH, oM, cH, cM, ciH, ciM;
    ArrayAdapter<String> hours, mins;
    Button save, back;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;

    Context context;

    private void declareP(View v) {
        context = getContext();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        hotelname = v.findViewById(R.id.curHotelName);
        address = v.findViewById(R.id.curAddress);
        owner = v.findViewById(R.id.curOwner);
        phone = v.findViewById(R.id.curPhone);
        openC = v.findViewById(R.id.open);
        closeC = v.findViewById(R.id.close);
        checkinC = v.findViewById(R.id.checkin);

        nameNew = v.findViewById(R.id.hotelnameChange);
        addressN = v.findViewById(R.id.addressChange);
        ownerN = v.findViewById(R.id.ownerEdit);
        phoneN = v.findViewById(R.id.phone);

        openH = v.findViewById(R.id.openEditH);
        openM = v.findViewById(R.id.openEditM);
        closeH = v.findViewById(R.id.closingEditH);
        closeM = v.findViewById(R.id.closingEditM);
        checkH = v.findViewById(R.id.checkinEditH);
        checkM = v.findViewById(R.id.checkinEditM);

        save = v.findViewById(R.id.saveBtn);
        back = v.findViewById(R.id.backBtnn);

        cbCheckinTime = v.findViewById(R.id.cbCheckin);
        cbWorkingTime = v.findViewById(R.id.cbWorkingTime);

        hours = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1
                ,getResources().getStringArray(R.array.hours24));

        mins = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1
                ,getResources().getStringArray(R.array.min60));

        hours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mins.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        openH.setAdapter(hours);
        openM.setAdapter(mins);
        closeH.setAdapter(hours);
        closeM.setAdapter(mins);
        checkH.setAdapter(hours);
        checkM.setAdapter(mins);
    }

    private void enableSpinnerWorkingHour() {
        openH.getSelectedView().setEnabled(true);
        openH.setEnabled(true);

        openM.getSelectedView().setEnabled(true);
        openM.setEnabled(true);

        closeH.getSelectedView().setEnabled(true);
        closeH.setEnabled(true);

        closeM.getSelectedView().setEnabled(true);
        closeM.setEnabled(true);
    }

    private void enableSpinnerCheckin() {
        //checkH.getSelectedView().setEnabled(true);
        checkH.setEnabled(true);

        //checkM.getSelectedView().setEnabled(true);
        checkM.setEnabled(true);
    }

    private void disableWH() {
        //openH.getSelectedView().setEnabled(false);
        openH.setEnabled(false);

        //openM.getSelectedView().setEnabled(false);
        openM.setEnabled(false);

        //closeH.getSelectedView().setEnabled(false);
        closeH.setEnabled(false);

        //closeM.getSelectedView().setEnabled(false);
        closeM.setEnabled(false);
    }

    private void disableCT() {
        //checkH.getSelectedView().setEnabled(false);
        checkH.setEnabled(false);

        //checkM.getSelectedView().setEnabled(false);
        checkM.setEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_hotel, container, false);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        declareP(v);
        openH.setEnabled(false);
        openM.setEnabled(false);
        closeH.setEnabled(false);
        closeM.setEnabled(false);
        checkH.setEnabled(false);
        checkM.setEnabled(false);

        cbWorkingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbWorkingTime.isChecked()) {
                    enableSpinnerWorkingHour();
                } else {
                    disableWH();
                }
            }
        });

        cbCheckinTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCheckinTime.isChecked()) {
                    enableSpinnerCheckin();
                } else {
                    disableCT();
                }

            }
        });

        myRef.child("Hotel").child(mUser.getUid()).child("Detail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HotelProfile hotelProfile = dataSnapshot.getValue(HotelProfile.class);
                hotelname.setText(hotelProfile.getName());
                address.setText(hotelProfile.getAddress());
                owner.setText(hotelProfile.getOwner());
                phone.setText(hotelProfile.getPhone());
                openC.setText(hotelProfile.getOpen());
                closeC.setText(hotelProfile.getClosing());
                checkinC.setText(hotelProfile.getCheckin());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(v1 -> {
            saveInput();
            HostProfileFragment hostProfileFragment = new HostProfileFragment();
            fragmentTransaction.replace(R.id.navHostFragment, hostProfileFragment);
            fragmentTransaction.commit();
        });

        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                HostProfileFragment hostProfileFragment = new HostProfileFragment();
                fragmentTransaction.replace(R.id.navHostFragment, hostProfileFragment);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

    private void saveInput() {
        name = nameNew.getText().toString();
        addressE = addressN.getText().toString();
        ownerE = ownerN.getText().toString();
        phoneE = phoneN.getText().toString();
        oH = openH.getSelectedItem().toString();
        oM = openM.getSelectedItem().toString();
        cH = closeH.getSelectedItem().toString();
        cM = closeM.getSelectedItem().toString();
        ciH = checkH.getSelectedItem().toString();
        ciM = checkM.getSelectedItem().toString();

        myRef.child("Hotel").child(mUser.getUid()).child("Detail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HotelProfile hotelProfile = snapshot.getValue(HotelProfile.class);
                if (cbWorkingTime.isChecked()) {
                    hotelProfile.setOpen(oH, oM);
                    hotelProfile.setClosing(cH, cM);
                }
                if (cbCheckinTime.isChecked()) {
                    hotelProfile.setCheckin(ciH, ciM);
                }

                if (!name.matches("")) {
                    hotelProfile.setName(name);
                }
                if(!addressE.matches("")){
                    hotelProfile.setAddress(addressE);
                }
                if (!ownerE.matches("")){
                    hotelProfile.setOwner(ownerE);
                }
                if(!phoneE.matches("")){
                    hotelProfile.setPhone(phoneE);
                }

                myRef.child("Hotel").child(mUser.getUid()).child("Detail").setValue(hotelProfile); //update Hotel Profile
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nameNew.setText("");
        addressN.setText("");
        ownerN.setText("");
        phoneN.setText("");

        openH.setSelection(0);
        openM.setSelection(0);
        closeH.setSelection(0);
        closeM.setSelection(0);
        checkH.setSelection(0);
        checkM.setSelection(0);

        Toast.makeText(context, "Profile has been updated", Toast.LENGTH_LONG).show();

    }
}