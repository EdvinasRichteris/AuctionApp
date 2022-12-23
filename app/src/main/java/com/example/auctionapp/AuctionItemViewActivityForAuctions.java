package com.example.auctionapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionItemViewActivityForAuctions extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auctionapp-8a872-default-rtdb.europe-west1.firebasedatabase.app/");
    ValueEventListener valueEventListener;
    private String ItemName;
    final String userPhoneNo = LoginActivity.phoneNoForReference;

    private String newBidderName;
    private String yourBid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_itemview_activity_auctions);
        Button addBidButton = findViewById(R.id.button3);
        Button seeLocation = findViewById(R.id.locationButton);

        String image = getIntent().getStringExtra("IMAGE");
        String itemname = getIntent().getStringExtra("ITEMNAME");
        String itemdesc = getIntent().getStringExtra("ITEMDESC");
        String address = getIntent().getStringExtra("ADDRESS");
        String city = getIntent().getStringExtra("CITY");
        String country = getIntent().getStringExtra("COUNTRY");
        String currentbid = getIntent().getStringExtra("CURRENTBID");
        String startingprice = getIntent().getStringExtra("STARTINGPRICE");
        String lastbidder = getIntent().getStringExtra("LASTBIDDER");
        ItemName = getIntent().getStringExtra("realName");


        ImageView ItemImage = findViewById(R.id.ItemViewImage);

        EditText addBid = findViewById(R.id.editTextNumberDecimal);
        TextView nameTextView = findViewById(R.id.textView32);
        TextView descriptionTextView = findViewById(R.id.textView25);
        TextView addressTextView = findViewById(R.id.textView26);
        TextView cityTextView = findViewById(R.id.textView27);
        TextView countryTextView = findViewById(R.id.textView28);
        TextView startingpriceTextView = findViewById(R.id.textView29);
        TextView currentbidTextView = findViewById(R.id.textView30);
        TextView lastbidderTextView = findViewById(R.id.textView31);



        Glide.with(this).load(image).into(ItemImage);
        descriptionTextView.setText(itemdesc);
        nameTextView.setText(itemname);
        addressTextView.setText(address);
        cityTextView.setText(city);
        countryTextView.setText(country);
        startingpriceTextView.setText(startingprice);
        currentbidTextView.setText(currentbid);
        lastbidderTextView.setText(lastbidder);

        addBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                yourBid = addBid.getText().toString();


                PlaceBidFirebase(yourBid, itemname);

            }
        });


        seeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Intent intent = new Intent(AuctionItemViewActivityForAuctions.this, AuctionLocation.class);
                intent.putExtra("ADDRESS", address);
                intent.putExtra("CITY", city);
                intent.putExtra("COUNTRY", country);
                startActivity(intent);

            }
        });
    }

    private void PlaceBidFirebase(String yourBid, String lastItemName) {


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(userPhoneNo))
                {
                    newBidderName = snapshot.child(userPhoneNo).child("firstname").getValue(String.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("auctions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot phoneSnapshot : dataSnapshot.getChildren()) {
                    String Item = "";
                    String phoneNumber = phoneSnapshot.getKey();
                    if (!phoneNumber.contains(userPhoneNo)) {
                        for (DataSnapshot itemSnapshot : phoneSnapshot.getChildren()) {
                            boolean flag = false;
                            Item = itemSnapshot.getKey();
                            if (Objects.equals(itemSnapshot.child("itemname").getValue(), lastItemName)) {
                                if (Float.valueOf(itemSnapshot.child("currentBid").getValue().toString()) < Float.valueOf(yourBid) &&
                                        Float.valueOf(itemSnapshot.child("startingprice").getValue().toString()) < Float.valueOf(yourBid)) {
                                    DatabaseReference refForMaps = databaseReference.child("auctions").child(phoneNumber).child(Item);
                                    HashMap maps = new HashMap();
                                    maps.put("lastbidder", newBidderName);
                                    maps.put("currentBid", yourBid);
                                    refForMaps.updateChildren(maps);
                                } else {
                                    Toast.makeText(AuctionItemViewActivityForAuctions.this, "Bid must be higher than current bid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(AuctionItemViewActivityForAuctions.this, "Can not place bids on your own auctions", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
