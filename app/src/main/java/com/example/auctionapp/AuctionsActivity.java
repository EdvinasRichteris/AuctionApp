package com.example.auctionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class AuctionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auctions);

        TextView addNewAuction = (TextView)findViewById(R.id.addNewAuction);

        addNewAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateAuctionActivity();
            }
        });
    }
    public void openCreateAuctionActivity() {
        Intent intent = new Intent(this, CreateAuctionActivity.class);
        startActivity(intent);
    }
}