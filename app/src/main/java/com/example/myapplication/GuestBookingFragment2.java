package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GuestBookingFragment2 extends Fragment {

    String no, monthStart, dateStart, monthEnd, dateEnd, smoke;
    Button book, cancel;
    Spinner monthS, dateS, monthE, dateE, smoking;
    EditText number;
    Context context;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_guest_booking, container, false);

        Bundle bundle = this.getArguments();
        String uid = bundle.getString("uid");
        String roomid = bundle.getString("roomSelected");

        context = v.getContext();
        book = v.findViewById(R.id.bookGuest);
        cancel = v.findViewById(R.id.cancelBooking);
        monthS = v.findViewById(R.id.monthBook);
        dateS = v.findViewById(R.id.dateBook);
        monthE = v.findViewById(R.id.monthEnd);
        dateE = v.findViewById(R.id.dateEnd);
        smoking = v.findViewById(R.id.smokingTorFG);
        number = v.findViewById(R.id.noPeople);
        myRef = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<String> bool = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.trueORflase));

        ArrayAdapter<String> month = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.months));

        ArrayAdapter<String> date31 = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dates31));

        ArrayAdapter<String> date30 = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dates30));

        ArrayAdapter<String> date29 = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dates29));

        bool.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date31.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date30.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date29.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        smoking.setAdapter(bool);
        monthS.setAdapter(month);
        monthE.setAdapter(month);

        monthS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                    case 4:
                    case 6:
                    case 7:
                    case 9:
                    case 11:
                    case 2:
                        dateS.setAdapter(date31);
                        break;
                    case 1:
                        dateS.setAdapter(date29);
                        break;
                    case 3:
                    case 5:
                    case 8:
                    case 10:
                        dateS.setAdapter(date30);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        monthE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                    case 4:
                    case 6:
                    case 7:
                    case 9:
                    case 11:
                    case 2:
                        dateE.setAdapter(date31);
                        break;
                    case 1:
                        dateE.setAdapter(date29);
                        break;
                    case 3:
                    case 5:
                    case 8:
                    case 10:
                        dateE.setAdapter(date30);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Hotel").child(uid).hasChild("Processing")) {
                    if (snapshot.child("Hotel").child(uid).child("Processing").hasChild(roomid)) {
                        myRef.child("Hotel").child(uid).child("Processing").child(roomid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                RequestRoomClass requestRoomClass = snapshot.getValue(RequestRoomClass.class);
                                number.setText(requestRoomClass.getNo());
                                switch (requestRoomClass.getMonthS()) {
                                    case "01":
                                        monthS.setSelection(0);
                                        break;
                                    case "02":
                                        monthS.setSelection(1);
                                        break;
                                    case "03":
                                        monthS.setSelection(2);
                                        break;
                                    case "04":
                                        monthS.setSelection(3);
                                        break;
                                    case "05":
                                        monthS.setSelection(4);
                                        break;
                                    case "06":
                                        monthS.setSelection(5);
                                        break;
                                    case "07":
                                        monthS.setSelection(6);
                                        break;
                                    case "08":
                                        monthS.setSelection(7);
                                        break;
                                    case "09":
                                        monthS.setSelection(8);
                                        break;
                                    case "10":
                                        monthS.setSelection(9);
                                        break;
                                    case "11":
                                        monthS.setSelection(10);
                                        break;
                                    case "12":
                                        monthS.setSelection(11);
                                        break;
                                }
                                switch (requestRoomClass.getMonthE()) {
                                    case "01":
                                        monthE.setSelection(0);
                                        break;
                                    case "02":
                                        monthE.setSelection(1);
                                        break;
                                    case "03":
                                        monthE.setSelection(2);
                                        break;
                                    case "04":
                                        monthE.setSelection(3);
                                        break;
                                    case "05":
                                        monthE.setSelection(4);
                                        break;
                                    case "06":
                                        monthE.setSelection(5);
                                        break;
                                    case "07":
                                        monthE.setSelection(6);
                                        break;
                                    case "08":
                                        monthE.setSelection(7);
                                        break;
                                    case "09":
                                        monthE.setSelection(8);
                                        break;
                                    case "10":
                                        monthE.setSelection(9);
                                        break;
                                    case "11":
                                        monthE.setSelection(10);
                                        break;
                                    case "12":
                                        monthE.setSelection(11);
                                        break;
                                }
                                switch (requestRoomClass.getDateS()) {
                                    case "01":
                                        dateS.setSelection(0);
                                        break;
                                    case "02":
                                        dateS.setSelection(1);
                                        break;
                                    case "03":
                                        dateS.setSelection(2);
                                        break;
                                    case "04":
                                        dateS.setSelection(3);
                                        break;
                                    case "05":
                                        dateS.setSelection(4);
                                        break;
                                    case "06":
                                        dateS.setSelection(5);
                                        break;
                                    case "07":
                                        dateS.setSelection(6);
                                        break;
                                    case "08":
                                        dateS.setSelection(7);
                                        break;
                                    case "09":
                                        dateS.setSelection(8);
                                        break;
                                    case "10":
                                        dateS.setSelection(9);
                                        break;
                                    case "11":
                                        dateS.setSelection(10);
                                        break;
                                    case "12":
                                        dateS.setSelection(11);
                                        break;
                                    case "13":
                                        dateS.setSelection(12);
                                        break;
                                    case "14":
                                        dateS.setSelection(13);
                                        break;
                                    case "15":
                                        dateS.setSelection(14);
                                        break;
                                    case "16":
                                        dateS.setSelection(15);
                                        break;
                                    case "17":
                                        dateS.setSelection(16);
                                        break;
                                    case "18":
                                        dateS.setSelection(17);
                                        break;
                                    case "19":
                                        dateS.setSelection(18);
                                        break;
                                    case "20":
                                        dateS.setSelection(19);
                                        break;
                                    case "21":
                                        dateS.setSelection(20);
                                        break;
                                    case "22":
                                        dateS.setSelection(21);
                                        break;
                                    case "23":
                                        dateS.setSelection(22);
                                        break;
                                    case "24":
                                        dateS.setSelection(23);
                                        break;
                                    case "25":
                                        dateS.setSelection(24);
                                        break;
                                    case "26":
                                        dateS.setSelection(25);
                                        break;
                                    case "27":
                                        dateS.setSelection(26);
                                        break;
                                    case "28":
                                        dateS.setSelection(27);
                                        break;
                                    case "29":
                                        dateS.setSelection(28);
                                        break;
                                    case "30":
                                        dateS.setSelection(29);
                                        break;
                                    case "31":
                                        dateS.setSelection(30);
                                        break;
                                }
                                switch (requestRoomClass.getDateE()) {
                                    case "01":
                                        dateE.setSelection(0);
                                        break;
                                    case "02":
                                        dateE.setSelection(1);
                                        break;
                                    case "03":
                                        dateE.setSelection(2);
                                        break;
                                    case "04":
                                        dateE.setSelection(3);
                                        break;
                                    case "05":
                                        dateE.setSelection(4);
                                        break;
                                    case "06":
                                        dateE.setSelection(5);
                                        break;
                                    case "07":
                                        dateE.setSelection(6);
                                        break;
                                    case "08":
                                        dateE.setSelection(7);
                                        break;
                                    case "09":
                                        dateE.setSelection(8);
                                        break;
                                    case "10":
                                        dateE.setSelection(9);
                                        break;
                                    case "11":
                                        dateE.setSelection(10);
                                        break;
                                    case "12":
                                        dateE.setSelection(11);
                                        break;
                                    case "13":
                                        dateE.setSelection(12);
                                        break;
                                    case "14":
                                        dateE.setSelection(13);
                                        break;
                                    case "15":
                                        dateE.setSelection(14);
                                        break;
                                    case "16":
                                        dateE.setSelection(15);
                                        break;
                                    case "17":
                                        dateE.setSelection(16);
                                        break;
                                    case "18":
                                        dateE.setSelection(17);
                                        break;
                                    case "19":
                                        dateE.setSelection(18);
                                        break;
                                    case "20":
                                        dateE.setSelection(19);
                                        break;
                                    case "21":
                                        dateE.setSelection(20);
                                        break;
                                    case "22":
                                        dateE.setSelection(21);
                                        break;
                                    case "23":
                                        dateE.setSelection(22);
                                        break;
                                    case "24":
                                        dateE.setSelection(23);
                                        break;
                                    case "25":
                                        dateE.setSelection(24);
                                        break;
                                    case "26":
                                        dateE.setSelection(25);
                                        break;
                                    case "27":
                                        dateE.setSelection(26);
                                        break;
                                    case "28":
                                        dateE.setSelection(27);
                                        break;
                                    case "29":
                                        dateE.setSelection(28);
                                        break;
                                    case "30":
                                        dateE.setSelection(29);
                                        break;
                                    case "31":
                                        dateE.setSelection(30);
                                        break;
                                }
                                switch (requestRoomClass.getSmoking()){
                                    case "true":
                                        smoking.setSelection(0);
                                        break;
                                    case "false":
                                        smoking.setSelection(1);
                                        break;
                                }
                        }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        book.setOnClickListener(v12 -> {
            no = number.getText().toString();
            monthStart = monthS.getSelectedItem().toString();
            dateStart = dateS.getSelectedItem().toString();
            monthEnd = monthE.getSelectedItem().toString();
            dateEnd = dateE.getSelectedItem().toString();
            smoke = smoking.getSelectedItem().toString();
            RequestRoomClass room = new RequestRoomClass(uid, roomid, no, monthStart, dateStart, monthEnd, dateEnd, smoke);
            myRef.child("Hotel").child(uid).child("Processing").child(roomid).setValue(room);
        });

        cancel.setOnClickListener(v1 -> {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            GuestBookingRoomFragment guestBookingRoomFragment = new GuestBookingRoomFragment();
            fragmentTransaction.replace(R.id.navHostFragmentGuest, guestBookingRoomFragment);
            fragmentTransaction.commit();
        });


        return v;
    }
}