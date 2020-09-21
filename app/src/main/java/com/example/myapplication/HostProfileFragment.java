package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class HostProfileFragment extends Fragment {

    TextView profileName, ageTV, addressTV, emailadddressTV, hotelNameTV, hotelAddress, phone,
            openingTime, closingTime, checkin;

    Button editH, editP;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        editH = v.findViewById(R.id.hostEditHotel);
        //editP = v.findViewById(R.id.editUser);

        profileName = v.findViewById(R.id.profileName);
        ageTV = v.findViewById(R.id.ageTextView);
        addressTV = v.findViewById(R.id.addressHostShow);
        emailadddressTV = v.findViewById(R.id.emailShow);
        hotelNameTV = v.findViewById(R.id.textViewHotelName);
        hotelAddress = v.findViewById(R.id.addressHotelShow);
        phone = v.findViewById(R.id.phoneHotelShow);
        openingTime = v.findViewById(R.id.openingTimeShow);
        closingTime = v.findViewById(R.id.closingTimeShow);
        checkin = v.findViewById(R.id.checkinTimeShow);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.getValue(Person.class);

                profileName.setText(person.getFirstName());
                ageTV.setText(person.getAge());
                addressTV.setText(person.getAddress());
                emailadddressTV.setText(person.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Hotel")) {
                    if (snapshot.child("Hotel").hasChild(mUser.getUid())) {
                        if (snapshot.child("Hotel").child(mUser.getUid()).hasChild("Detail")) {
                            myRef.child("Hotel").child(mUser.getUid()).child("Detail").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    HotelProfile hotelProfile = snapshot.getValue(HotelProfile.class);
                                    hotelNameTV.setText(hotelProfile.getName());
                                    hotelAddress.setText(hotelProfile.getAddress());
                                    phone.setText(hotelProfile.getPhone());
                                    openingTime.setText(hotelProfile.getOpen());
                                    closingTime.setText(hotelProfile.getClosing());
                                    checkin.setText(hotelProfile.getCheckin());
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


        //change to edit hotel fragment
        editH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotelname = hotelNameTV.getText().toString();
                /*
                if the user has not create a hotel profile then go to create hotel profile. else go to edit screen
                since the hotel name is manditory, we do not need to check for other detail.
                 */
                if (hotelname.equals("")) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    HostCreateHotelProfile HostCreateHotelProfile = new HostCreateHotelProfile();
                    fragmentTransaction.replace(R.id.navHostFragment, HostCreateHotelProfile);
                    fragmentTransaction.commit();

                } else {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    HostEditHotel hostEditHotel = new HostEditHotel();
                    fragmentTransaction.replace(R.id.navHostFragment, hostEditHotel);
                    fragmentTransaction.commit();
                }
            }
        });

//        //change to edit profile fragment
//        editP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                EditProfile editProfile = new EditProfile();
//                fragmentTransaction.replace(R.id.navHostFragment, editProfile);
//                fragmentTransaction.commit();
//            }
//        });

        return v;
    }
}