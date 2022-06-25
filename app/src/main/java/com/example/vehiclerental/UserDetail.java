package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserDetail extends AppCompatActivity {
    TextView carName, rentDays, carDestination, carAmount;
    ImageView img;
    Button accept,reject;
    String name, destination, date, amount, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        carAmount = findViewById(R.id.carAmt);
        carName = findViewById(R.id.carName);
        carDestination = findViewById(R.id.carDest);
        img = findViewById(R.id.userDetailImage);
        rentDays = findViewById(R.id.carDate);

        ArrayList<String> vehicles = getIntent().getExtras().getStringArrayList("vehicles");
        name = vehicles.get(0);
        image = vehicles.get(1);
        date = vehicles.get(2);
        amount = vehicles.get(3);
        destination = vehicles.get(4);

        Picasso.get().load(image).into(img);
        carAmount.setText(amount);
        carName.setText(name);
        rentDays.setText(date);
        carDestination.setText(destination);




    }
}