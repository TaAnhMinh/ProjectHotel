package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class HostEditServiceFragment extends Fragment {
    EditText ServiceNameEdit, ServiceDescriptionEdit;
    Button save, back, delete;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;

    private void declare(View v){
        ServiceNameEdit = v.findViewById(R.id.ServiceNameEdit);
        ServiceDescriptionEdit = v.findViewById(R.id.ServiceDescriptionEdit);
        save = v.findViewById(R.id.saveEditService);
        back = v.findViewById(R.id.BackEditService);
        delete = v.findViewById(R.id.delteBtn);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_edit_service, container, false);
        Bundle bundle = this.getArguments();
        String data = bundle.getString("servicePassing");

        declare(v);

        myRef.child("Hotel").child(mUser.getUid()).child("Service").child(data).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Service service = snapshot.getValue(Service.class);
                ServiceNameEdit.setText(service.getServiceName());
                ServiceDescriptionEdit.setText(service.getDescription());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(v1 -> {
            String NewName = ServiceNameEdit.getText().toString();
            String newDescription = ServiceDescriptionEdit.getText().toString();

            myRef.child("Hotel").child(mUser.getUid()).child("Service").child(data).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Service newService = snapshot.getValue(Service.class);
                    newService.setServiceName(NewName);
                    newService.setDescription(newDescription);

                    myRef.child("Hotel").child(mUser.getUid()).child("Service").child(data).setValue(newService);

                    Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    HostServiceControlFragment hostServiceControlFragment = new HostServiceControlFragment();
                    fragmentTransaction.replace(R.id.navHostFragment, hostServiceControlFragment);
                    fragmentTransaction.commit();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("Hotel").child(mUser.getUid()).child("Service").child(data).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(getContext(), "Service deleted", Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        HostServiceControlFragment hostServiceControlFragment = new HostServiceControlFragment();
                        fragmentTransaction.replace(R.id.navHostFragment, hostServiceControlFragment);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        back.setOnClickListener(v12 -> {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            HostServiceControlFragment hostServiceControlFragment = new HostServiceControlFragment();
            fragmentTransaction.replace(R.id.navHostFragment, hostServiceControlFragment);
            fragmentTransaction.commit();
        });

        return v;
    }
}