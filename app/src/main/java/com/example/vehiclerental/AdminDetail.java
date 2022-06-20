package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminDetail extends AppCompatActivity {
    ImageView adminImage;
    TextView carName, carSource, carDestination, rating, carRental;
    Button accept, reject;
    String name,source,dest,rate,rent, imgUrl, id;
//    String url = "http://192.168.1.67/Api/Delete.php";

    String url = "http://192.168.1.70/Api/Delete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail);
        adminImage = findViewById(R.id.adminImage);
        carName = findViewById(R.id.adminTxtTitle);
        carSource = findViewById(R.id.adminTxtSource);
        carDestination = findViewById(R.id.adminTxtDest);
        rating = findViewById(R.id.oneRating);
        carRental = findViewById(R.id.adminRental);
        accept = findViewById(R.id.accept);
        reject = findViewById(R.id.reject);

        ArrayList<String> vehicles = getIntent().getExtras().getStringArrayList("vehicles_Rental");
        name =  vehicles.get(0);
        source = vehicles.get(1);
        dest = vehicles.get(2);
        imgUrl = vehicles.get(4);
        rate = vehicles.get(5);
        rent  = vehicles.get(6);
        id = vehicles.get(7);
        Picasso.get().load(imgUrl).into(adminImage);
        carName.setText(name);
        carSource.setText(source);
        carDestination.setText(dest);
//        firebaseId.setText(fid);
        rating.setText(rate + ".0");
        carRental.setText(rent + " Days");

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             DeleteData();
            }
        });

    }

    private void DeleteData(){
        Toast.makeText(AdminDetail.this,"del",Toast.LENGTH_LONG).show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    Toast.makeText(AdminDetail.this,"Rejected",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminDetail.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String ,String> params = new HashMap<>();
                params.put("id",id);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AdminDetail.this);
        requestQueue.add(request);
    }
}