package com.example.auctionapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.hardware.SensorManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class AuctionsActivity extends AppCompatActivity implements RecyclerViewInterface{

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auctionapp-8a872-default-rtdb.europe-west1.firebasedatabase.app/");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private String userPhoneNo;
    private String ItemName;
    private Button button10;
    private Context mContext;
    private RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    ArrayList<AuctionForRecyclerView> auctionList;
    ValueEventListener valueEventListener;
    RecyclerViewInterface recyclerViewInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auctions);



        Button addNewAuction = (Button) findViewById(R.id.buttonAddNewAuction);
        Button carsButton = (Button) findViewById(R.id.carsButton);
        Button toysButton = (Button) findViewById(R.id.toysButton);
        Button shipsButton = (Button) findViewById(R.id.shipsButton);
        Button jetsButton = (Button) findViewById(R.id.jetsButton);
        Button allButton = (Button) findViewById(R.id.allButton);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setHasFixedSize(true);
        auctionList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(this, auctionList, this);
        recyclerViewInterface = this;
        recyclerView.setAdapter(recyclerAdapter);

        final String Filter = " ";

        ClearAll();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                GetDataFromFirebase(Filter);;
            }
        }, 1500);


        carsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String Filter = "Cars";
                ClearAll();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GetDataFromFirebase(Filter);;
                    }
                }, 1500);
            }
        });
        toysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String Filter = "Toys";
                ClearAll();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GetDataFromFirebase(Filter);;
                    }
                }, 1200);
            }
        });
        shipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String Filter = "Ships";
                ClearAll();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GetDataFromFirebase(Filter);;
                    }
                }, 1200);
            }
        });
        jetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String Filter = "Jets";
                ClearAll();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GetDataFromFirebase(Filter);;
                    }
                }, 1200);
            }
        });
        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String Filter = " ";
                ClearAll();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GetDataFromFirebase(Filter);;
                    }
                }, 1200);
            }
        });

        addNewAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateAuctionActivity();
            }
        });
    }

    private void GetDataFromFirebase(String Filter) {

        Query query = databaseReference.child("auctions");
        final String filter = Filter;
        valueEventListener = query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshotPhones : snapshot.getChildren()){
                    for(DataSnapshot dataSnapshotItems: dataSnapshotPhones.getChildren()){

                        if(filter == " "){
                            AuctionForRecyclerView auctionForRecycler = new AuctionForRecyclerView();

                            auctionForRecycler.setImageUrl(dataSnapshotItems.child("image").getValue().toString());
                            auctionForRecycler.setItemname(dataSnapshotItems.child("itemname").getValue().toString());
                            auctionForRecycler.setItemdesc(dataSnapshotItems.child("itemdesc").getValue().toString());
                            auctionForRecycler.setAddress(dataSnapshotItems.child("address").getValue().toString());
                            auctionForRecycler.setCity(dataSnapshotItems.child("city").getValue().toString());
                            auctionForRecycler.setCountry(dataSnapshotItems.child("country").getValue().toString());
                            auctionForRecycler.setStartingprice(dataSnapshotItems.child("startingprice").getValue().toString());
                            auctionForRecycler.setCurrentbid(dataSnapshotItems.child("currentBid").getValue().toString());
                            auctionForRecycler.setLastbidder(dataSnapshotItems.child("lastbidder").getValue().toString());
                            auctionList.add(auctionForRecycler);
                        }
                        if(filter == "Cars")
                        {
                            if(dataSnapshotItems.child("category").getValue().toString().contains(filter))
                            {
                                AuctionForRecyclerView auctionForRecycler = new AuctionForRecyclerView();
                                auctionForRecycler.setImageUrl(dataSnapshotItems.child("image").getValue().toString());
                                auctionForRecycler.setItemname(dataSnapshotItems.child("itemname").getValue().toString());
                                auctionForRecycler.setItemdesc(dataSnapshotItems.child("itemdesc").getValue().toString());
                                auctionForRecycler.setAddress(dataSnapshotItems.child("address").getValue().toString());
                                auctionForRecycler.setCity(dataSnapshotItems.child("city").getValue().toString());
                                auctionForRecycler.setCountry(dataSnapshotItems.child("country").getValue().toString());
                                auctionForRecycler.setStartingprice(dataSnapshotItems.child("startingprice").getValue().toString());
                                auctionForRecycler.setCurrentbid(dataSnapshotItems.child("currentBid").getValue().toString());
                                auctionForRecycler.setLastbidder(dataSnapshotItems.child("lastbidder").getValue().toString());
                                auctionList.add(auctionForRecycler);
                            }
                        }
                        if(filter == "Ships")
                        {
                            if(dataSnapshotItems.child("category").getValue().toString().contains(filter))
                            {
                                AuctionForRecyclerView auctionForRecycler = new AuctionForRecyclerView();
                                auctionForRecycler.setImageUrl(dataSnapshotItems.child("image").getValue().toString());
                                auctionForRecycler.setItemname(dataSnapshotItems.child("itemname").getValue().toString());
                                auctionForRecycler.setItemdesc(dataSnapshotItems.child("itemdesc").getValue().toString());
                                auctionForRecycler.setAddress(dataSnapshotItems.child("address").getValue().toString());
                                auctionForRecycler.setCity(dataSnapshotItems.child("city").getValue().toString());
                                auctionForRecycler.setCountry(dataSnapshotItems.child("country").getValue().toString());
                                auctionForRecycler.setStartingprice(dataSnapshotItems.child("startingprice").getValue().toString());
                                auctionForRecycler.setCurrentbid(dataSnapshotItems.child("currentBid").getValue().toString());
                                auctionForRecycler.setLastbidder(dataSnapshotItems.child("lastbidder").getValue().toString());
                                auctionList.add(auctionForRecycler);
                            }
                        }
                        if(filter == "Jets")
                        {
                            if(dataSnapshotItems.child("category").getValue().toString().contains(filter))
                            {
                                AuctionForRecyclerView auctionForRecycler = new AuctionForRecyclerView();
                                auctionForRecycler.setImageUrl(dataSnapshotItems.child("image").getValue().toString());
                                auctionForRecycler.setItemname(dataSnapshotItems.child("itemname").getValue().toString());
                                auctionForRecycler.setItemdesc(dataSnapshotItems.child("itemdesc").getValue().toString());
                                auctionForRecycler.setAddress(dataSnapshotItems.child("address").getValue().toString());
                                auctionForRecycler.setCity(dataSnapshotItems.child("city").getValue().toString());
                                auctionForRecycler.setCountry(dataSnapshotItems.child("country").getValue().toString());
                                auctionForRecycler.setStartingprice(dataSnapshotItems.child("startingprice").getValue().toString());
                                auctionForRecycler.setCurrentbid(dataSnapshotItems.child("currentBid").getValue().toString());
                                auctionForRecycler.setLastbidder(dataSnapshotItems.child("lastbidder").getValue().toString());
                                auctionList.add(auctionForRecycler);
                            }
                        }
                        if(filter == "Toys")
                        {
                            if(dataSnapshotItems.child("category").getValue().toString().contains(filter))
                            {
                                AuctionForRecyclerView auctionForRecycler = new AuctionForRecyclerView();
                                auctionForRecycler.setImageUrl(dataSnapshotItems.child("image").getValue().toString());
                                auctionForRecycler.setItemname(dataSnapshotItems.child("itemname").getValue().toString());
                                auctionForRecycler.setItemdesc(dataSnapshotItems.child("itemdesc").getValue().toString());
                                auctionForRecycler.setAddress(dataSnapshotItems.child("address").getValue().toString());
                                auctionForRecycler.setCity(dataSnapshotItems.child("city").getValue().toString());
                                auctionForRecycler.setCountry(dataSnapshotItems.child("country").getValue().toString());
                                auctionForRecycler.setStartingprice(dataSnapshotItems.child("startingprice").getValue().toString());
                                auctionForRecycler.setCurrentbid(dataSnapshotItems.child("currentBid").getValue().toString());
                                auctionForRecycler.setLastbidder(dataSnapshotItems.child("lastbidder").getValue().toString());
                                auctionList.add(auctionForRecycler);
                            }
                        }
                    }
                }
                query.removeEventListener(valueEventListener);


                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), auctionList, recyclerViewInterface);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void ClearAll(){
        if(auctionList != null){
            auctionList.clear();

            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        auctionList = new ArrayList<>();
    }

    public void openCreateAuctionActivity() {
        Intent intent = new Intent(this, CreateAuctionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(AuctionsActivity.this, AuctionItemViewActivityForAuctions.class);

        intent.putExtra("IMAGE", auctionList.get(position).getImageUrl());
        intent.putExtra("ITEMDESC", auctionList.get(position).getItemdesc());
        intent.putExtra("ITEMNAME", auctionList.get(position).getItemname());
        intent.putExtra("ADDRESS", auctionList.get(position).getAddress());
        intent.putExtra("CITY", auctionList.get(position).getCity());
        intent.putExtra("COUNTRY", auctionList.get(position).getCountry());
        intent.putExtra("STARTINGPRICE", auctionList.get(position).getStartingprice());

        intent.putExtra("CURRENTBID", auctionList.get(position).getCurrentbid());
        intent.putExtra("LASTBIDDER", auctionList.get(position).getLastbidder());


        startActivity(intent);

    }

}