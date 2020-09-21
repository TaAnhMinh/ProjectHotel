package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HostCreateRoom extends AppCompatActivity implements View.OnClickListener {

    EditText roomnumber, roomSize;
    String roomNum, roomSiz, smokingBoo, maxPPl, bedStyle, bedQuantity, bathroomQuantity, wifiBoo, tableQuantity, dryerQuantity, towelQuantity;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;
    Spinner smoking, maxPpl, bed, bedQuan, bathroom, wifi, table, dryer, towel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        //set onlicklistener
        findViewById(R.id.createRoom).setOnClickListener(this);
        findViewById(R.id.backBtn).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        roomnumber = (EditText) findViewById(R.id.roomNumber);
        roomSize = (EditText) findViewById(R.id.roomSize);

        smoking = (Spinner) findViewById(R.id.smokingTorF);
        maxPpl = (Spinner) findViewById(R.id.pplQuantity);
        bed = (Spinner) findViewById(R.id.bedSelection);
        bedQuan = (Spinner) findViewById(R.id.bedQuantity);
        bathroom = (Spinner) findViewById(R.id.bathRoomQuantity);
        wifi = (Spinner) findViewById(R.id.wifiTorF);
        table = (Spinner) findViewById(R.id.tableQuantity);
        dryer = (Spinner) findViewById(R.id.dryerQuantity);
        towel = (Spinner) findViewById(R.id.towelQuantity);

        ArrayAdapter<String> bedType = new ArrayAdapter<String>(HostCreateRoom.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.beds));

        ArrayAdapter<String> quantity = new ArrayAdapter<String>(HostCreateRoom.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.quantityCount));

        ArrayAdapter<String> TorF = new ArrayAdapter<String>(HostCreateRoom.this,
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

        Toast.makeText(this, "Room added", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createRoom:
                saveState();
            case R.id.backBtn:
                startActivity(new Intent(this, HostCreateHotel.class));
                break;
        }
    }
}
