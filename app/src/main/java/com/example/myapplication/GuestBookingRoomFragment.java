package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuestBookingRoomFragment extends Fragment {
    DatabaseReference myRef;
    TextView room1, size, type, quantity, table, towel, max , smoking, wifi;
    Button book, back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_booking_room, container, false);

        Bundle bundle = this.getArguments();
        String uid = bundle.getString("uid");
        String roomid = bundle.getString("roomSelected");

        myRef = FirebaseDatabase.getInstance().getReference();
        room1 = v.findViewById(R.id.roomBooking);
        size = v.findViewById(R.id.roomSizeGuest);
        type = v.findViewById(R.id.bedType);
        quantity = v.findViewById(R.id.bedQuanGuest);
        table = v.findViewById(R.id.bathroomQ);
        towel = v.findViewById(R.id.TowelQ);
        max = v.findViewById(R.id.maxQuan);
        smoking = v.findViewById(R.id.smokingTorFGuest);
        wifi = v.findViewById(R.id.wifiTorFGuest);
        book = v.findViewById(R.id.bookingSet);
        back = v.findViewById(R.id.bookingBack);

        myRef.child("Hotel").child(uid).child("Rooms").child(roomid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Room room = snapshot.getValue(Room.class);
                room1.setText(room.getRoomnumber());
                size.setText(room.getRoomSize());
                type.setText(room.getBedT());
                quantity.setText(room.getBedQ());
                table.setText(room.getTable());
                towel.setText(room.getTowel());
                max.setText(room.getMax());
                smoking.setText(room.getSmoking());
                wifi.setText(room.getWifi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        book.setOnClickListener(v12 -> {
            Bundle bundle2 = new Bundle();
            bundle2.putString("roomSelected", roomid);
            bundle2.putString("uid", uid);

            GuestBookingFragment2 fragment = new GuestBookingFragment2();
            fragment.setArguments(bundle2);

            getFragmentManager().beginTransaction().replace(R.id.navHostFragmentGuest, fragment).commit();
        });

        back.setOnClickListener(v1 -> {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            GuestBookingFragment bookingGuestFragment = new GuestBookingFragment();
            fragmentTransaction.replace(R.id.navHostFragmentGuest, bookingGuestFragment);
            fragmentTransaction.commit();
        });

        return v;
    }
}