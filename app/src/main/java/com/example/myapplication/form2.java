package com.example.myapplication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityBusSeatBinding;
import com.example.myapplication.databinding.ActivityForm2Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.UUID; // Import UUID for generating unique file names

public class form2 extends AppCompatActivity {

    ActivityForm2Binding binding;

    ActivityResultLauncher<String> launcher1;
    ActivityResultLauncher<String> launcher2;
    ActivityResultLauncher<String> launcher3;
    FirebaseStorage storage;
    FirebaseDatabase database;
    Uri photoUri; // Variable to store the selected image URI
    Uri feeReceiptUri; // Variable to store the selected image URI
    Uri idCardUr1; // Variable to store the selected image URI

    String getenroll;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sssssss-e9a15-default-rtdb.firebaseio.com/");

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForm2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

//
        Intent intent1 = getIntent();
        getenroll = intent1.getStringExtra("getenroll");
//
        database.getReference().child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get()
                        .load(image)
                        .into(binding.photo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        launcher1 = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri1) {
                binding.photo.setImageURI(uri1);
                photoUri = uri1; // Store the selected image URI
            }
        });

        launcher2 = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri2) {
                binding.feeReciept.setImageURI(uri2);
                feeReceiptUri = uri2; // Store the selected image URI
            }
        });

        launcher3 = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri3) {
                binding.idCard.setImageURI(uri3);
                idCardUr1 = uri3; // Store the selected image URI
            }
        });

        binding.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher1.launch("image/*"); // Open the gallery for image selection
            }
        });

        binding.feeReciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher2.launch("image/*"); // Open the gallery for image selection
            }
        });

        binding.idCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher3.launch("image/*"); // Open the gallery for image selection
            }
        });

        binding.upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoUri != null) {
                    uploadImage1(photoUri); // Upload the selected image
                } else {
                    Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feeReceiptUri != null) {
                    uploadImage2(feeReceiptUri); // Upload the selected image
                } else {
                    Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCardUr1 != null) {
                    uploadImage3(idCardUr1); // Upload the selected image
                } else {
                    Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = getIntent();
        String getenroll = intent.getStringExtra("getenroll");

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getbloodgroup = binding.bloodgroup.getText().toString();
                String getbusno = binding.busno.getText().toString();
                databaseReference.child("users").child(getenroll).child(getbloodgroup).child("bloodgroup").setValue(getbloodgroup);
                databaseReference.child("users").child(getenroll).child(getbloodgroup).child("busno").setValue(getbusno);

                Toast.makeText(form2.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                openonclick();
            }
        });
    }

    public void openonclick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

//

//

    private void uploadImage1(Uri imageUri) {
        final StorageReference reference = storage.getReference().child("images/" + getenroll + "photo");
//        final StorageReference reference = storage.getReference().child("images/" + getenroll );

        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful, get the download URL
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Image URL obtained, handle further actions here if needed
                        String imageUrl = uri.toString();
                        Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_LONG).show();
                        // Perform any other actions with the imageUrl if required
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void uploadImage2(Uri imageUri) {
        final StorageReference reference = storage.getReference().child("images/" + getenroll + "feeReceipt");

        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful, get the download URL
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Image URL obtained, handle further actions here if needed
                        String imageUrl = uri.toString();
                        Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_LONG).show();
                        // Perform any other actions with the imageUrl if required
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage3(Uri imageUri) {
        final StorageReference reference = storage.getReference().child("images/" + getenroll + "idCard");

        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful, get the download URL
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Image URL obtained, handle further actions here if needed
                        String imageUrl = uri.toString();
                        Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_LONG).show();
                        // Perform any other actions with the imageUrl if required
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
