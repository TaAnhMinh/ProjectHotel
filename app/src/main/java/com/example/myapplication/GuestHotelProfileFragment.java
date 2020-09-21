package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

import java.util.ArrayList;


public class GuestHotelProfileFragment extends Fragment {

    TextView hotelname, address, city, country, owner, phone, email;
    ListView lv;
    Button booking, back;
    ArrayList<Room> roomArrayList;
    RoomListAdapter adapter;
    Context context;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_guest_hotel_profile, container, false);

        declare(v);
        Bundle bundle = this.getArguments();
        String data = bundle.getString("HotelUid");

        myRef.child("Hotel").child(data).child("Detail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HotelProfile hotelProfile = snapshot.getValue(HotelProfile.class);
                hotelname.setText(hotelProfile.getName());
                address.setText(hotelProfile.getAddress());
                city.setText(hotelProfile.getCity());
                country.setText(hotelProfile.getCountry());
                owner.setText(hotelProfile.getOpen());
                phone.setText(hotelProfile.getPhone());
                email.setText(hotelProfile.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("Hotel").child(data).child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Room room = ds.getValue(Room.class);
                    roomArrayList.add(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("bookingHotelUid", data);

                GuestBookingFragment fragment = new GuestBookingFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.navHostFragmentGuest, fragment).commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                GuestMain guest_main = new GuestMain();
                fragmentTransaction.replace(R.id.navHostFragmentGuest, guest_main);
                fragmentTransaction.commit();
            }
        });

        lv.setAdapter(adapter);
        return v;
    }

    private void declare(View v) {
        context = v.getContext();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        hotelname = v.findViewById(R.id.hotelNameGuest);
        address = v.findViewById(R.id.hagShow);
        city = v.findViewById(R.id.hcgShow);
        country = v.findViewById(R.id.hcgShow2);
        owner = v.findViewById(R.id.hogShow);
        phone = v.findViewById(R.id.hpgShow);
        email = v.findViewById(R.id.hegShow);

        lv = v.findViewById(R.id.roomGuestShow);

        booking = v.findViewById(R.id.bookingBtn);
        back = v.findViewById(R.id.backHotelGuest);

        roomArrayList = new ArrayList<Room>();
        adapter = new RoomListAdapter(context, R.layout.adapter_hotel_main_screen, roomArrayList);

    }
}