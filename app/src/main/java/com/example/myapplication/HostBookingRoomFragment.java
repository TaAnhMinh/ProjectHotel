package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class HostBookingRoomFragment extends Fragment {

    TextView avail, cNo, checkin, checkout, roomediting;
    Spinner nAvail, nNo, nCheckinM, nCheckinD, nCheckoutM, nCheckoutD;
    String available, no, hour1, min1, hour2, min2;
    Button save, cancel;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_booking_room_host, container, false);

        Bundle bundle = this.getArguments();
        String data = bundle.getString("key");

        declare(v);

        myRef.child("Hotel").child(mUser.getUid()).child("Rooms").child(data).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Room room = snapshot.getValue(Room.class);
                Log.d("key2", room.getCurrent());
                avail.setText(room.getAvail());
                cNo.setText(room.getCurrent());
                checkin.setText(room.getCheckin());
                checkout.setText(room.getCheckout());
                roomediting.setText(room.getRoomnumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                available = nAvail.getSelectedItem().toString();
                no = nNo.getSelectedItem().toString();
                hour1 = nCheckinM.getSelectedItem().toString();
                min1 = nCheckinD.getSelectedItem().toString();
                hour2 = nCheckoutM.getSelectedItem().toString();
                min2 = nCheckoutD.getSelectedItem().toString();

                myRef.child("Hotel").child(mUser.getUid()).child("Rooms").child(data).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Room room = snapshot.getValue(Room.class);
                        room.setAvail(available);
                        room.setCurrent(no);
                        room.setCheckin(hour1, min1);
                        room.setCheckout(hour2, min2);
                        myRef.child("Hotel").child(mUser.getUid()).child("Rooms").child(data).setValue(room);

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        HostHotelMain HostHotelMain = new HostHotelMain();
                        fragmentTransaction.replace(R.id.navHostFragment, HostHotelMain);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                nAvail.setSelection(0);
                nNo.setSelection(0);
                nCheckinM.setSelection(0);
                nCheckinD.setSelection(0);
                nCheckoutM.setSelection(0);
                nCheckoutD.setSelection(0);

                Toast.makeText(getContext(), "Success!",  Toast.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                HostHotelMain HostHotelMain = new HostHotelMain();
                fragmentTransaction.replace(R.id.navHostFragment, HostHotelMain);
                fragmentTransaction.commit();
            }
        });

        return  v;
    }

    private void declare(View v) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        avail = v.findViewById(R.id.cAvail);
        cNo = v.findViewById(R.id.cCurrentNo);
        checkin = v.findViewById(R.id.cCheckin);
        checkout = v.findViewById(R.id.cCheckout);
        roomediting = v.findViewById(R.id.roomEditing);
        cancel = v.findViewById(R.id.cancelRoomSetting);

        nAvail = v.findViewById(R.id.aTF);
        nNo = v.findViewById(R.id.cNoGuest);
        nCheckinM = v.findViewById(R.id.checkinH);
        nCheckinD = v.findViewById(R.id.checkinM);
        nCheckoutM = v.findViewById(R.id.checkoutH);
        nCheckoutD = v.findViewById(R.id.checkoutM);

        save = v.findViewById(R.id.saveRoom);

        ArrayAdapter<String> quantity = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.guestnum));
        ArrayAdapter<String> TorF = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.trueORflase));
        ArrayAdapter<String> month = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.months));
        ArrayAdapter<String> date = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dates31));

        nAvail.setAdapter(TorF);
        nNo.setAdapter(quantity);
        nCheckinM.setAdapter(month);
        nCheckinD.setAdapter(date);
        nCheckoutM.setAdapter(month);
        nCheckoutD.setAdapter(date);
    }
}