package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HostCreateService extends AppCompatActivity {
    EditText serviceName, description;
    private ArrayList<Service> serviceArray;
    Button addButton, backBtn;
    ListView lv;
    String uid;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;

    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        serviceName = findViewById(R.id.serviceName);
        description = findViewById(R.id.serviceDescription);
        addButton = findViewById(R.id.addServiceBtn);
        backBtn = findViewById(R.id.backBtn);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
        uid = mUser.getUid();
        lv = (ListView) findViewById(R.id.viewService);

        serviceArray = new ArrayList<Service>();
        ServiceListAdapter adapter = new ServiceListAdapter(this, R.layout.adapter_view_layout, serviceArray);

        service = new Service();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Hotel")) {
                    if (snapshot.child("Hotel").hasChild(mUser.getUid())) {
                        if (snapshot.child("Hotel").child(mUser.getUid()).hasChild("Service")) {
                            myRef.child("Hotel").child(mUser.getUid()).child("Service").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    adapter.clear();
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        Service service2 = ds.getValue(Service.class);
                                        serviceArray.add(service2);
                                        adapter.notifyDataSetChanged();
                                    }
                                    lv.setAdapter(adapter);
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

        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save data to the firebase so that it can be used in the future.
                saveService();
                Service service = new Service(serviceName.getText().toString(), description.getText().toString());
                serviceArray.add(service);
                serviceName.setText("");
                description.setText("");
                adapter.notifyDataSetChanged();
            }
        };
        addButton.setOnClickListener(addListener);

        View.OnClickListener backListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HostCreateService.this, HostCreateHotel.class));
            }
        };
        backBtn.setOnClickListener(backListener);

        lv.setAdapter(adapter);
    }

    private void saveService() {
        String serviceN = serviceName.getText().toString().trim();

        if (serviceN.isEmpty()) {
            serviceName.setError("Service Name is required to add");
            serviceName.requestFocus();
            return;
        }
        final Service service = new Service(serviceN, description.getText().toString());

        myRef.child("Hotel").child(uid)
                .child("Service").child(serviceN).setValue(service);


        Toast.makeText(this, "Service added", Toast.LENGTH_LONG).show();
    }
}
