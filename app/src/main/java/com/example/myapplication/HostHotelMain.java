package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class HostHotelMain extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;
    ArrayList<Room> roomArrayList;
    ListView mListView;
    RoomListAdapter adapter;
    Context context;
    Room room;
    TextView hotelname;
    Button addRoom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hotel_main, container, false);

        declare(v);

        //display hotel name
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("Hotel").hasChild(mUser.getUid())) {
                    myRef.child("Hotel").child(mUser.getUid()).child("Detail").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HotelProfile hotelProfile = snapshot.getValue(HotelProfile.class);
                            hotelname.setText(hotelProfile.getName());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    hotelname.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Listview fragment update
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Hotel")) {
                    if (snapshot.child("Hotel").hasChild(mUser.getUid())) {
                        if (snapshot.child("Hotel").child(mUser.getUid()).hasChild("Rooms")) {
                            myRef.child("Hotel").child(mUser.getUid()).child("Rooms").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        room = ds.getValue(Room.class);
                                        roomArrayList.add(room);
                                    }
                                    mListView.setAdapter(adapter);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //add room
        addRoom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                HostAddRoom HostAddRoom = new HostAddRoom();
                fragmentTransaction.replace(R.id.navHostFragment, HostAddRoom);
                fragmentTransaction.commit();
            }
        });

        //allow user to edit the state of the room(such as set available to true or set the sign in date. To make booking for guest
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room room = (Room) parent.getItemAtPosition(position);
                Log.d("roomNum", room.getRoomnumber());
                Bundle bundle = new Bundle();
                bundle.putString("key", room.getRoomnumber());

                HostBookingRoomFragment fragment = new HostBookingRoomFragment();
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.navHostFragment, fragment).commit();
            }
        });


        return v;
    }

    private void declare(View v) {
        context = v.getContext();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        room = new Room();
        hotelname = (TextView) v.findViewById(R.id.hotelNameTextView);
        mListView = (ListView) v.findViewById(R.id.viewRoom);
        roomArrayList = new ArrayList<Room>();
        addRoom = v.findViewById(R.id.addRoom);

        adapter = new RoomListAdapter(context, R.layout.adapter_hotel_main_screen, roomArrayList);
    }
}