package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityBuspassBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;

public class buspass extends AppCompatActivity {

    ActivityBuspassBinding binding;
    StorageReference storageReference;
    DatabaseReference reference;

    String getenroll = "aa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuspassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Fetch "getenroll" value from intent or wherever it's obtained
        getenroll = getIntent().getStringExtra("getenroll");


        // Call the method to load the image when the activity is created
        loadImage();

        binding.Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "qq";
                readData(data);
            }

            private void readData(String data) {
                reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.child("getenroll").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(buspass.this, "successfully read", Toast.LENGTH_SHORT).show();
                            DataSnapshot dataSnapshot = task.getResult();
//                            String name = String.valueOf(dataSnapshot.child("yourname").getValue());
                            String name = dataSnapshot.child("yourname").getValue(String.class));
                            binding.getname.setText(name);

                        } else {
                            Toast.makeText(buspass.this, "Failed to read", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    // Method to load the image
    private void loadImage() {
        String imageID = "aa"; // Convert image ID to string

//        Use the value of "getenroll" to fetch the image
//          storageReference = FirebaseStorage.getInstance().getReference("images/" + getenroll + "photo");


        storageReference = FirebaseStorage.getInstance().getReference("images/" + imageID );

        try {
            File localfile = File.createTempFile("tempfile", ".jpeg");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            binding.getImage.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(buspass.this, "Failed to retrieve", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
