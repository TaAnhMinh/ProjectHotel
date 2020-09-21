package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class HostEditProfile extends Fragment {
    TextView first, last, age, address, email;
    Button save;
    EditText nfirst, nlast, nage, naddress, nemail;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        declare(v);

        myRef.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.getValue(Person.class);

                first.setText(person.getFirstName());
                last.setText(person.getLastName());
                age.setText(person.getAge());
                address.setText(person.getAddress());
                email.setText(person.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }

    private void declare(View v) {
        first = v.findViewById(R.id.cFirst);
        last = v.findViewById(R.id.cLast);
        age = v.findViewById(R.id.cAge);
        address = v.findViewById(R.id.cAddress);
        email = v.findViewById(R.id.cEmail);

        save = v.findViewById(R.id.saveProfile);
        nfirst = v.findViewById(R.id.nFirst);
        nlast = v.findViewById(R.id.nLast);
        nage = v.findViewById(R.id.nAge);
        naddress = v.findViewById(R.id.nAddress);
        nemail = v.findViewById(R.id.nEmail);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
    }
}