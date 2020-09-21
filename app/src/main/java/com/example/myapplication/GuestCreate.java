package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GuestCreate extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    EditText editTextFirstName, editTextLastName, editTextAge, editTextUserName, editTextPassword, editTextConfirmPassword ;
    String FirstName, LastName, username, password, confirm , userRole, Age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createguest);

        editTextFirstName = (EditText) findViewById(R.id.firstName2);
        editTextLastName = (EditText) findViewById(R.id.lastName2);
        editTextAge = (EditText) findViewById(R.id.Age2);
        editTextUserName = (EditText) findViewById(R.id.username2);
        editTextPassword = (EditText) findViewById(R.id.password2);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirmPassword2);

        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.logIn2).setOnClickListener(this);
    }

    private void registerGuest() {
        FirstName = editTextFirstName.getText().toString().trim();
        LastName = editTextLastName.getText().toString().trim();
        username = editTextUserName.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        confirm = editTextConfirmPassword.getText().toString().trim();
        userRole = "Guest"; //set user role to guest
        Age = editTextAge.getText().toString().trim();

        if (username.isEmpty()) {
            editTextUserName.setError("Email is required");
            editTextUserName.requestFocus();
            return;
        }

        if (Age.isEmpty()) {
            editTextAge.setError("Age is empty");
            editTextAge.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (FirstName.isEmpty()) {
            editTextFirstName.setError("first name is required");
            editTextFirstName.requestFocus();
            return;
        }
        if (LastName.isEmpty()) {
            editTextLastName.setError("last name is required");
            editTextLastName.requestFocus();
            return;
        }
        if (confirm.isEmpty()) {
            editTextConfirmPassword.setError("Password confirmation is required");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (!(confirm.equals(password))) {
            editTextConfirmPassword.setError("Confirmation do not match password");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            editTextUserName.setError("Please enter a valid email");
            editTextUserName.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        final Person person = new Person(userRole, FirstName, LastName, username, Age);

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this,
                task -> {
                    if (task.isSuccessful()) {
                        mAuth = FirebaseAuth.getInstance();
                        myRef = FirebaseDatabase.getInstance().getReference();
                        FirebaseUser user = mAuth.getCurrentUser();

                        myRef.child("users").child(user.getUid()).setValue(person).addOnCompleteListener(this,
                                task1 -> {
                                    if (task1.isSuccessful()) {
                                        startActivity(new Intent(this, GuestScreen.class));
                                    } else {
                                        Toast.makeText(this, "cannot make a host account", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        checkUsername();
                    }

                });
    }

    public void checkUsername() {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(
                        task -> {
                            if (!task.isSuccessful()) {
                                try {
                                    throw task.getException();
                                }
                                // if user enters wrong email.
                                catch (FirebaseAuthWeakPasswordException weakPassword) {
                                    editTextPassword.setError("weak password");
                                    editTextPassword.requestFocus();
                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                    editTextUserName.setError("malformed email");
                                    editTextUserName.requestFocus();
                                } catch (FirebaseAuthUserCollisionException existEmail) {
                                    editTextUserName.setError("email already used");
                                    editTextUserName.requestFocus();
                                } catch (Exception e) {
                                    Toast.makeText(GuestCreate.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                registerGuest();
                break;
            case R.id.logIn:
                startActivity(new Intent(this, GuestLogIn.class));
                break;
        }
    }
}
