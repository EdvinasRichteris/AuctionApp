package com.example.auctionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
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

public class CreateAuctionActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auctionapp-8a872-default-rtdb.europe-west1.firebasedatabase.app/");

    final String userPhoneNo = LoginActivity.phoneNoForReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_auction);

        final EditText itemName = findViewById(R.id.ItemName);
        final EditText ItemDescription = findViewById(R.id.ItemDescription);
        final Spinner category = (Spinner) findViewById(R.id.Category);
        final Spinner subCategory = (Spinner) findViewById(R.id.SubCategory);
        final EditText startingPrice = findViewById(R.id.StartingPrice);
        final EditText timeLeft = findViewById(R.id.TimeLeft);
        final EditText country = findViewById(R.id.PickUpCountry);
        final EditText city = findViewById(R.id.PickUpCity);
        final EditText address = findViewById(R.id.PickUpAddress);

        category.setPrompt("Category");
        subCategory.setPrompt("Sub-Category");

        final Button auctionButton = findViewById(R.id.CreateAuctionButton);

        auctionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ItemName = itemName.getText().toString();
                final String itemDescription = ItemDescription.getText().toString();
                final String Category = "new cat";
                final String SubCategory = "new sub cat";
                final String StartingPrice = startingPrice.getText().toString();
                final String TimeLeft = timeLeft.getText().toString();
                final String Country = country.getText().toString();
                final String City = city.getText().toString();
                final String Address = address.getText().toString();


                databaseReference.child("auctions").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("itemname").setValue(ItemName);
                        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("itemdesc").setValue(itemDescription);
                        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("category").setValue(Category);
                        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("subcategory").setValue(SubCategory);
                        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("startingprice").setValue(StartingPrice);
                        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("timeleft").setValue(TimeLeft);
                        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("country").setValue(Country);
                        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("city").setValue(City);
                        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("address").setValue(Address);


                        Toast.makeText(CreateAuctionActivity.this, "User registered succesfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }



        });
    }
}