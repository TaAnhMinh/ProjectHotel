package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GuestBookingFragment extends Fragment {

    ListView lv;
    Button back;
    RoomListAdapter adapter;
    ArrayList<Room> roomArrayList;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_booking_guest, container, false);
        Bundle bundle = this.getArguments();
        String data = bundle.getString("bookingHotelUid");
        myRef = FirebaseDatabase.getInstance().getReference();
        lv = v.findViewById(R.id.lvHotelRoomsGuest);
        back = v.findViewById(R.id.backBookingRoom);
        roomArrayList = new ArrayList<Room>();
        adapter = new RoomListAdapter(getContext(), R.layout.adapter_hotel_main_screen, roomArrayList);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                GuestHotelProfileFragment guest = new GuestHotelProfileFragment();
                fragmentTransaction.replace(R.id.navHostFragmentGuest, guest);
                fragmentTransaction.commit();
            }
        });

        //fill listview
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room room2 = (Room) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putString("roomSelected", room2.getRoomnumber());
                bundle.putString("uid", data);

                GuestBookingRoomFragment fragment = new GuestBookingRoomFragment();
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.navHostFragmentGuest, fragment).commit();
            }
        });


        lv.setAdapter(adapter);

        return v;
    }
}