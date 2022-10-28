package com.example.auctionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Button profileButton = (Button) findViewById(R.id.profileButton);
        final Button auctionsButton = (Button) findViewById(R.id.auctionsButton);
        final Button myauctionsButton = (Button) findViewById(R.id.myauctionsButton);


        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileActivity();
            }
        });

        auctionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAuctionsActivity();
            }
        });
    }
    public void openAuctionsActivity() {
        Intent intent = new Intent(this, AuctionsActivity.class);
        startActivity(intent);
    }
    public void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}