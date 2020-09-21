package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HostCreate extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;

    EditText editTextFirstName, editTextLastName, editTextAge, editTextUserName, editTextPassword, editTextConfirmPassword, editTextAddress;
    String FirstName,LastName,username,password,confirm,address,userRole,Age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createhost);

        editTextFirstName = (EditText) findViewById(R.id.firstName);
        editTextLastName = (EditText) findViewById(R.id.lastName);
        editTextAge = (EditText) findViewById(R.id.Age);
        editTextUserName = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        editTextAddress = findViewById(R.id.Address);

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.logIn).setOnClickListener(this);
    }

    private void registerGuest(){
         FirstName = editTextFirstName.getText().toString().trim();
         LastName = editTextLastName.getText().toString().trim();
         username = editTextUserName.getText().toString().trim();
         password = editTextPassword.getText().toString().trim();
         confirm = editTextConfirmPassword.getText().toString().trim();
         address = editTextAddress.getText().toString().trim();
         userRole = "Host"; //set user role to guest
         Age = editTextAge.getText().toString().trim();

        if (username.isEmpty()) {
            editTextUserName.setError("Email is required");
            editTextUserName.requestFocus();
            return ;
        }

        if (address.isEmpty()) {
            editTextAddress.setError("Address is empty");
            editTextAddress.requestFocus();
            return;
        }

        if (Age.isEmpty()){
            editTextAge.setError("Age is empty");
            editTextAge.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return ;
        }

        if (FirstName.isEmpty()) {
            editTextFirstName.setError("first name is required");
            editTextFirstName.requestFocus();
            return ;
        }
        if (LastName.isEmpty()) {
            editTextLastName.setError("last name is required");
            editTextLastName.requestFocus();
            return ;
        }
        if (confirm.isEmpty()) {
            editTextConfirmPassword.setError("Password confirmation is required");
            editTextConfirmPassword.requestFocus();
            return ;
        }

        if(!(confirm.equals(password))){
            editTextConfirmPassword.setError("Confirmation do not match password");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            editTextUserName.setError("Please enter a valid email");
            editTextUserName.requestFocus();
            return ;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return ;
        }

        final Person person = new Person (userRole, FirstName, LastName, username, Age, address);

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this,
                task ->{
                    if(task.isSuccessful()){
                        mAuth = FirebaseAuth.getInstance();
                        myRef = FirebaseDatabase.getInstance().getReference();
                        FirebaseUser user = mAuth.getCurrentUser();

                        myRef.child("users").child(user.getUid()).setValue(person).addOnCompleteListener(this,
                                task1 -> {
                                    if(task1.isSuccessful()){
                                        startActivity(new Intent(HostCreate.this, HostWelcomeNew.class));
                                    } else{
                                        Toast.makeText(HostCreate.this, "cannot make a host account", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }else{
                        checkUsername();
                    }

                });
    }

    public void checkUsername(){
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (!task.isSuccessful())
                                {
                                    try
                                    {
                                        throw task.getException();
                                    }
                                    // if user enters wrong email.
                                    catch (FirebaseAuthWeakPasswordException weakPassword)
                                    {
                                        editTextPassword.setError("weak password");
                                        editTextPassword.requestFocus();
                                    }
                                    // if user enters wrong password.
                                    catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                    {
                                        editTextUserName.setError("malformed email");
                                        editTextUserName.requestFocus();
                                    }
                                    catch (FirebaseAuthUserCollisionException existEmail)
                                    {
                                        editTextUserName.setError("email already used");
                                        editTextUserName.requestFocus();
                                    }
                                    catch (Exception e)
                                    {
                                        Toast.makeText(HostCreate.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                );
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                registerGuest();
                break;
            case R.id.logIn:
                startActivity(new Intent(this,HostLogIn.class));
                break;
        }
    }
}
