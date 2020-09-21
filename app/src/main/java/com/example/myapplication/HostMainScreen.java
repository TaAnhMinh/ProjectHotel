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

public class HostMainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavController navController;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_main_screen);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        myRef = FirebaseDatabase.getInstance().getReference();

        //allow user to open the side bar when click on the menu icon.
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        //set home screen
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, new HostHotelMain()).commit();
            navigationView.setCheckedItem(R.id.homeScreen);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        TextView textTitle = findViewById(R.id.textTitle);
        switch (item.getItemId()){
            case R.id.homeScreen:
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, new HostHotelMain()).commit();
                //to indicate which section the user is in. (if the user is in profile then the title will show profile.)
                break;
            case R.id.serviceControl:
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, new HostServiceControlFragment()).commit();
                break;
            case R.id.menuProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, new HostProfileFragment()).commit();
                break;
            case R.id.notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, new HostNotificationFragment()).commit();
                break;
            case R.id.booking:
                //nav to booking
                break;
            case R.id.inbox:
                //nav to inbox
                break;
            case R.id.setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.navHostFragment, new HostSettingFragment()).commit();
                break;
            case R.id.help:
                //nav to help
                break;
            case R.id.signoutNow:
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
