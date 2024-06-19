package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.databinding.ActivityBusSeatBinding;

public class bus_seat extends AppCompatActivity {
    ActivityBusSeatBinding binding;
    private ImageButton img1;
    private ImageButton img2;
    private ImageButton img3;
    private ImageButton img4;
    private ImageButton img5;
    private ImageButton img6;
    private ImageButton img7;
    private ImageButton img8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_seat);
        img1 = findViewById(R.id.imageButton);
        img2 = findViewById(R.id.imageButton3);
        img3 = findViewById(R.id.imageButton4);
        img4 = findViewById(R.id.imageButton5);
        img5 = findViewById(R.id.imageButton6);
        img6 = findViewById(R.id.imageButton7);
        img7 = findViewById(R.id.imageButton8);
        img8 = findViewById(R.id.imageButton9);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openonclick();
            }

        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openonclick();
            }

        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openonclick();
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openonclick();
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openonclick();
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openonclick();
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openonclick();
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openonclick();
            }
        });
    }

    public void openonclick(){
        Intent intent = new Intent(this, buspass.class);
        startActivity(intent);

    }

    public void openonclick2(String value) {

        Intent intent2 = new Intent(this, buspass.class);
        intent2.putExtra("getenroll", value);
        startActivity(intent2);
    }
}