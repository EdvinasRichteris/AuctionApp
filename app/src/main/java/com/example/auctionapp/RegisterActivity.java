package com.example.auctionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auctionapp-8a872-default-rtdb.europe-west1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText firstname = findViewById(R.id.FirstName);
        final EditText lastname = findViewById(R.id.LastName);
        final EditText Password = findViewById(R.id.Password);
        final EditText confirmPassword = findViewById(R.id.ConfirmPassword);
        final EditText phoneNo = findViewById(R.id.PhoneNumber);
        final EditText Email = findViewById(R.id.EmailAddress);
        final EditText Country = findViewById(R.id.Country);
        final EditText City = findViewById(R.id.City);
        final EditText Address = findViewById(R.id.Address);

        final Button registerButton = findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstName = firstname.getText().toString();
                final String lastName = lastname.getText().toString();
                final String password = Password.getText().toString();
                final String confirmpassword = confirmPassword.getText().toString();
                final String phone = phoneNo.getText().toString();
                final String email = Email.getText().toString();
                final String country = Country.getText().toString();
                final String city = City.getText().toString();
                final String address = Address.getText().toString();


                if(firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() ||
                        phone.isEmpty() || email.isEmpty() || country.isEmpty() || city.isEmpty() || address.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Please fill all registration fields", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(confirmpassword))
                {
                    Toast.makeText(RegisterActivity.this, "Please make sure both password fields match", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phone))
                            {
                                Toast.makeText(RegisterActivity.this, "Phone is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                databaseReference.child("users").child(phone).child("firstname").setValue(firstName);
                                databaseReference.child("users").child(phone).child("lastname").setValue(lastName);
                                databaseReference.child("users").child(phone).child("password").setValue(password);
                                databaseReference.child("users").child(phone).child("email").setValue(email);
                                databaseReference.child("users").child(phone).child("country").setValue(country);
                                databaseReference.child("users").child(phone).child("city").setValue(city);
                                databaseReference.child("users").child(phone).child("address").setValue(address);

                                Toast.makeText(RegisterActivity.this, "User registered succesfully!", Toast.LENGTH_SHORT).show();
                                finish();
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
}