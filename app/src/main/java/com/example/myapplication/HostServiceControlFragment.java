package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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


public class HostServiceControlFragment extends Fragment {

    EditText serviceName, description;
    private ArrayList<Service> serviceArray, serviceArrayShow;
    Button addButton;
    ListView lv;
    String uid;
    ServiceListAdapter adapter;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;

    private void declare(View v){
        serviceName = v.findViewById(R.id.serviceNameFrag);
        description = v.findViewById(R.id.serviceDescriptionFrag);
        addButton = v.findViewById(R.id.addServiceBtnFrag);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
        uid = mUser.getUid();
        lv = (ListView) v.findViewById(R.id.viewServiceFrag);
        serviceArray = new ArrayList<Service>();
        adapter = new ServiceListAdapter(getContext(), R.layout.adapter_view_layout, serviceArray);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_service_control, container, false);
        declare(v);

        //adding service
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

        addButton.setOnClickListener(v1 -> {
            saveService();
            Service service = new Service(serviceName.getText().toString(), description.getText().toString());
            serviceArray.add(service);
            serviceName.setText("");
            description.setText("");
            adapter.notifyDataSetChanged();
        });
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Service serviceNow = (Service) parent.getItemAtPosition(position);
            Bundle bundle = new Bundle();
            bundle.putString("servicePassing", serviceNow.getServiceName());

            HostEditServiceFragment fragment = new HostEditServiceFragment();
            fragment.setArguments(bundle);

            getFragmentManager().beginTransaction().replace(R.id.navHostFragment, fragment).commit();
        });
        lv.setAdapter(adapter);
        return v;
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


        Toast.makeText(getContext(), "Service added", Toast.LENGTH_LONG).show();
    }
}