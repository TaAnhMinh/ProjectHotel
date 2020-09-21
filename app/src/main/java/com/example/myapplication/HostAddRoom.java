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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HostAddRoom extends Fragment {
    EditText roomnumber, roomSize;
    String roomNum, roomSiz, smokingBoo, maxPPl, bedStyle, bedQuantity, bathroomQuantity, wifiBoo, tableQuantity, dryerQuantity, towelQuantity;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;
    double roomS = 0;
    int roomN = 0;
    Context context;
    Spinner smoking, maxPpl, bed, bedQuan, bathroom, wifi, table, dryer, towel;
    Button createRoom, backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_room, container, false);
        declare(v);
        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveState();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HostHotelMain HostHotelMain = new HostHotelMain();
                fragmentTransaction.replace(R.id.navHostFragment, HostHotelMain);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

    private void declare(View v) {
        backBtn = v.findViewById(R.id.backBtn2);
        createRoom = v.findViewById(R.id.createRoom2);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        roomnumber = (EditText) v.findViewById(R.id.roomNumber2);
        roomSize = (EditText) v.findViewById(R.id.roomSize2);

        smoking = (Spinner) v.findViewById(R.id.smokingTorF2);
        maxPpl = (Spinner) v.findViewById(R.id.pplQuantity2);
        bed = (Spinner) v.findViewById(R.id.bedSelection2);
        bedQuan = (Spinner) v.findViewById(R.id.bedQuantity2);
        bathroom = (Spinner) v.findViewById(R.id.bathRoomQuantity2);
        wifi = (Spinner) v.findViewById(R.id.wifiTorF2);
        table = (Spinner) v.findViewById(R.id.tableQuantity2);
        dryer = (Spinner) v.findViewById(R.id.dryerQuantity2);
        towel = (Spinner) v.findViewById(R.id.towelQuantity2);

        context = getContext();

        ArrayAdapter<String> bedType = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.beds));

        ArrayAdapter<String> quantity = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.quantityCount));

        ArrayAdapter<String> TorF = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.trueORflase));

        bedType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TorF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        smoking.setAdapter(TorF);
        maxPpl.setAdapter(quantity);
        bed.setAdapter(bedType);
        bedQuan.setAdapter(quantity);
        bathroom.setAdapter(quantity);
        wifi.setAdapter(TorF);
        table.setAdapter(quantity);
        dryer.setAdapter(quantity);
        towel.setAdapter(quantity);
    }

    private void saveState() {
        roomNum = roomnumber.getText().toString();
        roomSiz = roomSize.getText().toString();

        smokingBoo = smoking.getSelectedItem().toString();
        maxPPl = maxPpl.getSelectedItem().toString();
        bedStyle = bed.getSelectedItem().toString();
        bedQuantity = bedQuan.getSelectedItem().toString();
        bathroomQuantity = bathroom.getSelectedItem().toString();
        wifiBoo = wifi.getSelectedItem().toString();
        tableQuantity = table.getSelectedItem().toString();
        dryerQuantity = dryer.getSelectedItem().toString();
        towelQuantity = towel.getSelectedItem().toString();

        if (roomNum.equals("")) {
            roomnumber.setError("room number cannot be empty");
            roomnumber.requestFocus();
        } else if (roomSiz.equals("")) {
            roomSize.setError("room size cannnot be empty");
            roomSize.requestFocus();
        } else {
            String uid = mUser.getUid();

            //save the data to database under Rooms in hotel.
            final Room room = new Room(roomNum, roomSiz, smokingBoo, maxPPl, bedStyle, bedQuantity, bathroomQuantity, wifiBoo, tableQuantity, dryerQuantity, towelQuantity);

            myRef.child("Hotel").child(uid).
                    child("Rooms").child(roomNum).setValue(room);

            //reset the values to default.
            roomnumber.setText("");
            roomSize.setText("");
            smoking.setSelection(0);
            maxPpl.setSelection(0);
            bed.setSelection(0);
            bedQuan.setSelection(0);
            bathroom.setSelection(0);
            wifi.setSelection(0);
            table.setSelection(0);
            dryer.setSelection(0);
            towel.setSelection(0);

            Toast.makeText(context, "Room added", Toast.LENGTH_LONG).show();
        }
    }
}