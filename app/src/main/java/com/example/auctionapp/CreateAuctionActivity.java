package com.example.auctionapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateAuctionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auctionapp-8a872-default-rtdb.europe-west1.firebasedatabase.app/");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private Spinner category;
    private Spinner subCategory;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Uri imageUri;
    private Model modelForUpload;



    String categoryChoice = "";
    String subCategoryChoice = "";
    String imageUrlForRecycle;

    final String userPhoneNo = LoginActivity.phoneNoForReference;

    public CreateAuctionActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_auction);

        final EditText itemName = findViewById(R.id.ItemName);
        final EditText ItemDescription = findViewById(R.id.ItemDescription);
        category = (Spinner) findViewById(R.id.Category);
        subCategory = (Spinner) findViewById(R.id.SubCategory);
        final EditText startingPrice = findViewById(R.id.StartingPrice);
        final EditText timeLeft = findViewById(R.id.TimeLeft);
        final EditText country = findViewById(R.id.PickUpCountry);
        final EditText city = findViewById(R.id.PickUpCity);
        final EditText address = findViewById(R.id.PickUpAddress);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, 2);
            }
        });

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);

        category.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> subCategoryAdapter = ArrayAdapter.createFromResource(this, R.array.subCategories, android.R.layout.simple_spinner_dropdown_item);
        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCategory.setAdapter(subCategoryAdapter);

        subCategory.setOnItemSelectedListener(this);



        final Button auctionButton = findViewById(R.id.CreateAuctionButton);

        auctionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageUri != null) {

                    uploadToFirebase(imageUri);
                    final String ItemName = itemName.getText().toString();
                    final String itemDescription = ItemDescription.getText().toString();
                    final String Category = categoryChoice;
                    final String SubCategory = subCategoryChoice;
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
                            databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("currentBid").setValue("-");
                            databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("timeleft").setValue(TimeLeft);
                            databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("country").setValue(Country);
                            databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("city").setValue(City);
                            databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("address").setValue(Address);
                            databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("image").setValue(imageUrlForRecycle);
                            databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("lastbidder").setValue("-");
                            databaseReference.child("auctions").child(userPhoneNo).child(ItemName).child("createdtime").setValue(Calendar.getInstance().getTime());


                            Toast.makeText(CreateAuctionActivity.this, "Auction created succesfully!", Toast.LENGTH_SHORT).show();
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 5000);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(CreateAuctionActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }

            private void uploadToFirebase(Uri uri) {
                StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Model model = new Model(uri.toString());
                                modelForUpload = model;
                                Log.i("auctionact", uri.toString());
                                imageUrlForRecycle = uri.toString();
                                Toast.makeText(CreateAuctionActivity.this, "Image was uploaded succesfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(CreateAuctionActivity.this, "Image failed to upload", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private String getFileExtension(Uri imageUri) {
                ContentResolver cr = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                return mime.getExtensionFromMimeType(cr.getType(imageUri));
            }


        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null)
        {
            imageUri = data.getData();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getItemAtPosition(i).toString() == "Transport" || adapterView.getItemAtPosition(i).toString() == "Toys" ||
                adapterView.getItemAtPosition(i).toString() == "Beauty" || adapterView.getItemAtPosition(i).toString() == "Gaming" ||
                adapterView.getItemAtPosition(i).toString() == "Clothes")
        {
            categoryChoice = adapterView.getItemAtPosition(i).toString();
        }
        else
        {
            subCategoryChoice = adapterView.getItemAtPosition(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}