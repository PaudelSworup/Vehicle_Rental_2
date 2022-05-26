package com.example.vehiclerental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yinglan.shadowimageview.ShadowImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Detail extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    String [] items = {"Pokhara", "Mustang" ,"Rara" ,"Nagarkot", "Namche Bazar" ,
            "Bardiya National Park", "Sarangkot" ," Bandipur",
            "Koshi tapu", "Chandragiri", "Janaki Temple","Bhote Koshi", "Illam", "Khumbu Valley", "Nuwakot"};

    ArrayAdapter<String> arrayAdapter1;
    String [] Source = {"Bhaktapur", "Duwakot", "Changunaryan" ,"Byasi","Lalitpur",
            "Kathmandu","Chitwan","Nawalparasi","Devghat","Thimi","Pepsicola","Bharatpur",
            "Rautahat","Mulpani","Radhe-Radhe","Lokanthali","Ghataghar",
            "Balkot","Baneswor","Maitighar","Patan","Thapathali","Tripureswor","Anamnagar","Naxal","kagbeni","rukum","mugu"};
    ImageView img;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextViewSrc;
    EditText vehicleRentalDays;
    TextView txtName , txtDescription, txtType, txtPhoneNum, titleDetail, txtCategory;
    RatingBar txtRating;
    String title, description, imageUrl, rating, type, phone, vehicleCategory,destination_box, rentalTime, userID;
    Button addToList;
    private FirebaseAuth firebaseAuth;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img = findViewById(R.id.imageView);
        txtName = findViewById(R.id.txtName);
        addToList = findViewById(R.id.addToList);
        autoCompleteTextView = findViewById(R.id.destination);
        autoCompleteTextViewSrc = findViewById(R.id.source);
        vehicleRentalDays = findViewById(R.id.days);
        titleDetail = findViewById(R.id.detailTitle);
        txtRating = findViewById(R.id.rating_star);
        txtPhoneNum = findViewById(R.id.txtPhoneNum);
        txtCategory =findViewById(R.id.category);
        txtType = findViewById(R.id.txtType);
        txtDescription = findViewById(R.id.txtDesc);

        ArrayList<String> vehicles = getIntent().getExtras().getStringArrayList("vehicles_Rental");
        title = vehicles.get(0);
        description = vehicles.get(2);
        imageUrl = vehicles.get(1);
        rating = vehicles.get(3);
        type = vehicles.get(4);
        phone = vehicles.get(5);
        vehicleCategory = vehicles.get(6);
        Picasso.get().load(imageUrl).into(img);
        txtName.setText(title);
        txtRating.setRating(Float.parseFloat(rating));
        titleDetail.setText("Detail");
        txtDescription.setText(description);
        txtType.setText("Vehicle Type : " + type);
        txtPhoneNum.setText("Contact Number : " + phone);
        txtCategory.setText("Category : " + vehicleCategory);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();


        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               destination_box  = autoCompleteTextView.getText().toString();
               rentalTime = vehicleRentalDays.getText().toString();
                if(destination_box.isEmpty()){
                    autoCompleteTextView.setError("Please enter your destination");
                    return;
                }

                if(rentalTime.isEmpty()){
                    vehicleRentalDays.setError("Please enter for how many days you want to rent a vehicle");
                    return;
                }

//                String url = "http://192.168.1.67/Api/vehicleRequest.php";

                    String url = "http://192.168.1.68/Api/vehicleRequest.php";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        autoCompleteTextView.setText("");
                        vehicleRentalDays.setText("");
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Detail.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {

                        Map<String ,String> params = new HashMap<>();
                        params.put("name",title);
                        params.put("imageUrl", imageUrl);
                        params.put("rating", rating);
                        params.put("destination",destination_box);
                        params.put("days",rentalTime);
                        params.put("uid", userID);
                       return params;

                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Detail.this);
                requestQueue.add(request);

                Toast.makeText(getApplicationContext(),"Wait for admin Approval", Toast.LENGTH_SHORT).show();
            }
        });

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,items);
        autoCompleteTextView.setAdapter(arrayAdapter);
        arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,Source);
        autoCompleteTextViewSrc.setAdapter(arrayAdapter1);

        autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0);
            }
        });

        autoCompleteTextViewSrc.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0);
            }
        });








    }
}