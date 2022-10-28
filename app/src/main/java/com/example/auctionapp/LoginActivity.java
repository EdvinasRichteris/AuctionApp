package com.example.auctionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auctionapp-8a872-default-rtdb.europe-west1.firebasedatabase.app/");
    public static String phoneNoForReference = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText phoneNo = findViewById(R.id.PhoneNumber);
        final EditText password = findViewById(R.id.password);
        final Button loginButton = (Button) findViewById(R.id.buttonLogin);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String phoneNoInput = phoneNo.getText().toString();
                final String passwordInput = password.getText().toString();

                if(phoneNoInput.isEmpty() || passwordInput.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please enter both phone number and password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneNoInput))
                            {
                                final String getPassword = snapshot.child(phoneNoInput).child("password").getValue(String.class);
                                
                                if(getPassword.equals(passwordInput))
                                {
                                    Toast.makeText(LoginActivity.this, "Succesfully Logged In", Toast.LENGTH_SHORT).show();
                                    phoneNoForReference = phoneNo.getText().toString();
                                    openMenuActivity();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "User does not exist for this phone number", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    public void openMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}