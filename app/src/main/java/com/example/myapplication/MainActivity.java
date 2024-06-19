package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ActivityMainBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sssssss-e9a15-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText userID = findViewById(R.id.uid);
        final EditText password = findViewById(R.id.passw);
        final Button loginButton = findViewById(R.id.button);
        final Button signUpButton = findViewById(R.id.button21);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = userID.getText().toString();
                final String pass = password.getText().toString();

                if (id.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter uid and pass", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(id)) {
                                final String getPass = snapshot.child(id).child("enroll").getValue(String.class);

                                if (Objects.equals(getPass, pass)) {
                                    Toast.makeText(MainActivity.this, "Succesfully loged in", Toast.LENGTH_SHORT).show();
                                    openOnClick();
                                } else {
                                    Toast.makeText(MainActivity.this, "Wrong Pass", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Wrong pass", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Failed to read data.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOnClick2(); // Call openOnClick2 when signUpButton is clicked
            }
        });
    }

    public void openOnClick() {
        Intent intent = new Intent(this, bus_seat.class);
        startActivity(intent);
    }

    public void openOnClick2() {
        Intent intent2 = new Intent(MainActivity.this, formactivity.class);
        startActivity(intent2);
    }
}