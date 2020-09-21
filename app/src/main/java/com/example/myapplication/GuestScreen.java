package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GuestScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavController navController;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guestscreen);

        drawerLayout = findViewById(R.id.drawerLayoutGuest);
        navigationView = findViewById(R.id.navigationViewGuest);
        navController = Navigation.findNavController(this, R.id.navHostFragmentGuest);
        myRef = FirebaseDatabase.getInstance().getReference();

        //allow user to open the side bar when click on the menu icon.
        findViewById(R.id.imageMenuGuest).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        //set home screen
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragmentGuest, new GuestMain() ).commit();
            navigationView.setCheckedItem(R.id.homeScreen);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        TextView textTitle = findViewById(R.id.textTitle);
        switch (item.getItemId()){
            case R.id.homeScreenGuest:
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragmentGuest, new GuestMain()).commit();
                //to indicate which section the user is in. (if the user is in profile then the title will show profile.)
                break;
            case R.id.menuProfileGuest:
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragmentGuest, new GuestProfileFragment()).commit();
                break;
            case R.id.notificationGuest:
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragmentGuest, new GuestNotificationFragment()).commit();
                break;
            case R.id.bookingGuest:
                //nav to booking
                break;
            case R.id.inboxGuest:
                //nav to inbox
                break;
            case R.id.settingGuest:
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragmentGuest, new GuestSettingFragment()).commit();
                break;
            case R.id.helpGuest:
                //nav to help
                break;
            case R.id.signoutNowGuest:
                //sign out prop
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent (this, MainActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                break;
        }
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                textTitle.setText(destination.getLabel());
            }
        });
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
