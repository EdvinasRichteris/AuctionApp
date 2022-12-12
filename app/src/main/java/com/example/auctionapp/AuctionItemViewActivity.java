package com.example.auctionapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class AuctionItemViewActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auctionapp-8a872-default-rtdb.europe-west1.firebasedatabase.app/");
    ValueEventListener valueEventListener;
    private String ItemName;
    final String userPhoneNo = LoginActivity.phoneNoForReference;

    private String Countryy;
    private String itemDescrr;
    private String Cityy;
    private String ItemNamee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_itemview_activity);
        Button editButton = findViewById(R.id.button4);

        String image = getIntent().getStringExtra("IMAGE");
        String itemname = getIntent().getStringExtra("ITEMNAME");
        String itemdesc = getIntent().getStringExtra("ITEMDESC");
        /*String address = getIntent().getStringExtra("ADDRESS");*/
        String city = getIntent().getStringExtra("CITY");
        String country = getIntent().getStringExtra("COUNTRY");
        /*String currentbid = getIntent().getStringExtra("CURRENTBID");*/
        /*String startingprice = getIntent().getStringExtra("STARTINGPRICE");*/
        /*String lastbidder = getIntent().getStringExtra("LASTBIDDER");*/
        ItemName = getIntent().getStringExtra("realName");


        ImageView ItemImage = findViewById(R.id.ItemViewImage);
        EditText descriptionTextView = findViewById(R.id.editDesc);
        EditText itemnameTextView = findViewById(R.id.editName);
        EditText cityTextView = findViewById(R.id.editCity);
        EditText countryTextView = findViewById(R.id.editCountry);
        /*TextView countryTextView = findViewById(R.id.textView29);
        TextView startingpriceTextView = findViewById(R.id.textView30);*/
/*        TextView currentbidTextView = findViewById(R.id.textView31);
        TextView lastbidderTextView = findViewById(R.id.textView32);*/


        Glide.with(this).load(image).into(ItemImage);
        descriptionTextView.setText(itemdesc);
        itemnameTextView.setText(itemname);
        /*cityTextView.setText(city);*/
        cityTextView.setText(city);
        countryTextView.setText(country);
        /*startingpriceTextView.setText(startingprice);*/
/*        currentbidTextView.setText(currentbid);
        lastbidderTextView.setText(lastbidder);*/

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Countryy = countryTextView.getText().toString();
                Cityy = cityTextView.getText().toString();
                itemDescrr = descriptionTextView.getText().toString();
                ItemNamee = itemnameTextView.getText().toString();

                EditDataFromFirebase(Countryy, Cityy, itemDescrr, ItemNamee, itemname);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), MyAuctions.class);
                        startActivity(intent);
                    }
                }, 2000);

            }
        });
    }

    private void EditDataFromFirebase(String countryy, String cityy, String itemdescrr, String itemnamee, String lastItemName) {


        databaseReference.child("auctions").child(userPhoneNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Item = "";
                String currentNumber = "";
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    boolean flag = false;
                    Item = itemSnapshot.getKey();
                    if(Objects.equals(itemSnapshot.child("itemname").getValue(), lastItemName))
                    {
                        DatabaseReference refForMaps = databaseReference.child("auctions").child(userPhoneNo).child(Item);
                        HashMap maps = new HashMap();
                        maps.put("itemname", itemnamee);
                        maps.put("country", countryy);
                        maps.put("city", cityy);
                        maps.put("itemdesc", itemdescrr);
                        refForMaps.updateChildren(maps);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        /*databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("itemname").setValue(ItemNamee);
        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("itemdesc").setValue(itemDescrr);
        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("country").setValue(Countryy);
        databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("city").setValue(Cityy);*/

    }

}
