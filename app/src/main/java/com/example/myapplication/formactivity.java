package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityForm2Binding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class formactivity extends AppCompatActivity {
    private EditText enroll;
    private EditText yourname;
    private EditText fathername;
    private EditText bran;
    private EditText sem;
    private FirebaseDatabase firebaseDatabase;

    ActivityForm2Binding binding;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://sssssss-e9a15-default-rtdb.firebaseio.com/");

    private Button button2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formactivity);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        enroll =findViewById(R.id.enroll);
        yourname =findViewById(R.id.yourname);
        fathername =findViewById(R.id.fathername);
        bran =findViewById(R.id.bran);
        sem =findViewById(R.id.sem);
        button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getenroll = enroll.getText().toString();
                String getyourname = yourname.getText().toString();
                String getfathername =fathername.getText().toString();
                String getbran = bran.getText().toString();
                String getsem = sem.getText().toString();

                databaseReference.child("users").child(getenroll).child("enroll").setValue(getenroll);
                databaseReference.child("users").child(getenroll).child("yourname").setValue(getyourname);
                databaseReference.child("users").child(getenroll).child("fathername").setValue(getfathername);
                databaseReference.child("users").child(getenroll).child("bran").setValue(getbran);
                databaseReference.child("users").child(getenroll).child("sem").setValue(getsem);

                Toast.makeText(formactivity.this, "User registered succesfully", Toast.LENGTH_SHORT).show();

                openonclick(getenroll);

            }
        });
    }
    public void openonclick(String value){
        Intent intent = new Intent(this, form2.class);
        intent.putExtra("getenroll",value);
        startActivity(intent);
    }
}