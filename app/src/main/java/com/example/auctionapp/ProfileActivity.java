package com.example.auctionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auctionapp-8a872-default-rtdb.europe-west1.firebasedatabase.app/");

    final String userPhoneNo = LoginActivity.phoneNoForReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(userPhoneNo))
                {
                    EditText name = findViewById(R.id.userName);
                    EditText lastName = findViewById(R.id.userLastName);
                    EditText email = findViewById(R.id.userEmailAddress);
                    EditText phoneNo = findViewById(R.id.userPhoneNumber);
                    EditText country = findViewById(R.id.userCountry);
                    EditText city = findViewById(R.id.userCity);
                    EditText address = findViewById(R.id.userAddress);

                    name.setText(snapshot.child(userPhoneNo).child("firstname").getValue(String.class));
                    lastName.setText(snapshot.child(userPhoneNo).child("lastname").getValue(String.class));
                    email.setText(snapshot.child(userPhoneNo).child("email").getValue(String.class));
                    phoneNo.setText(userPhoneNo);
                    country.setText(snapshot.child(userPhoneNo).child("country").getValue(String.class));
                    city.setText(snapshot.child(userPhoneNo).child("city").getValue(String.class));
                    address.setText(snapshot.child(userPhoneNo).child("address").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}