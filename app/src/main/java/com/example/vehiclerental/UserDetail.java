package com.example.vehiclerental;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UserDetail extends AppCompatActivity {
    TextView carName, rentDays, carDestination, carAmount;
    ImageView img;
    EditText payment;
    Button accept,reject;
    String name, destination, date, amount, image, carRentAmount,fid,userId;


//    String url = "http://192.168.1.70/Api/transaction.php";
//    String rejectVehicleURL = "http://192.168.1.70/Api/rejectVehicle.php";

    String url = "http://192.168.1.69/Api/transaction.php";
    String rejectVehicleURL = "http://192.168.1.69/Api/rejectVehicle.php";




    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        carAmount = findViewById(R.id.carAmt);
        carName = findViewById(R.id.carName);
        carDestination = findViewById(R.id.carDest);
        payment = findViewById(R.id.etCarAmt);
        img = findViewById(R.id.userDetailImage);
        rentDays = findViewById(R.id.carDate);
        accept = findViewById(R.id.UserAccept);
        reject = findViewById(R.id.UserReject);

        ArrayList<String> vehicles = getIntent().getExtras().getStringArrayList("vehicles");
        name = vehicles.get(0);
        image = vehicles.get(1);
        date = vehicles.get(2);
        amount = vehicles.get(3);
        destination = vehicles.get(4);
        fid = vehicles.get(5);
        userId = vehicles.get(6);


        Picasso.get().load(image).into(img);
        carAmount.setText(amount);
        carName.setText(name);
        rentDays.setText(date);
        payment.setText(amount);
//        payment.setEnabled(false);
        carDestination.setText(destination);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payAmount();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    rejectVehicle();
            }
        });
    }

    private void payAmount() {
        carRentAmount = payment.getText().toString();


        if (carRentAmount.isEmpty()) {
            payment.setError("Enter the amount ");
            return;
        }

        if (carRentAmount.equals(amount)) {
            Toast.makeText(getApplicationContext(), "equal", Toast.LENGTH_SHORT).show();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(UserDetail.this, response.trim(), Toast.LENGTH_LONG).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UserDetail.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("date", dtf.format(now));
                    params.put("amount", amount);
                    params.put("name", name);
                    params.put("fid", fid);
                    params.put("id",userId);
                    return params;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(UserDetail.this);
            requestQueue.add(request);
        }else {
            payment.setError("enter correct amount");
            return;
        }
    }


    private void rejectVehicle(){
        Toast.makeText(UserDetail.this,"rejecting...",Toast.LENGTH_LONG).show();
        StringRequest request = new StringRequest(Request.Method.POST, rejectVehicleURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UserDetail.this,"Rejected",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserDetail.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String ,String> params = new HashMap<>();
                params.put("id",userId);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UserDetail.this);
        requestQueue.add(request);
    }

    }
