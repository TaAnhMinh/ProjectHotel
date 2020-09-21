package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GuestMain extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;
    Context context;
    EditText searchHotel;
    ListView lv;
    AdapterGuestHotelDisplay adapter;
    ArrayList<HotelProfile> hotelProfileArrayList;

    private void declare(View v) {
        context = v.getContext();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
        hotelProfileArrayList = new ArrayList<HotelProfile>();
        adapter = new AdapterGuestHotelDisplay(context, R.layout.adapter_guest_hotel_display, hotelProfileArrayList);
        searchHotel = v.findViewById(R.id.searchHotel);
        lv = v.findViewById(R.id.viewRoom);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guest_main, container, false);
        declare(v);

        //display all hotels
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Hotel")) {
                    myRef.child("Hotel").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            adapter.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                HotelProfile hotelProfile = ds.getValue(HotelProfile.class);
                                hotelProfileArrayList.add(hotelProfile);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //seach bar
        searchHotel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //when type something --> text change --> search
                (GuestMain.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //click on hotel --> display hotel profile. (Passing on name of the
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotelProfile hotelProfile = (HotelProfile) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putString("HotelUid", hotelProfile.getUid());

                GuestHotelProfileFragment fragment = new GuestHotelProfileFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.navHostFragmentGuest, fragment).commit();
            }
        });

        lv.setAdapter(adapter);
        return v;
    }
}