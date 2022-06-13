package com.example.vehiclerental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Detail extends AppCompatActivity {
    private static final int PERMISSION_ID = 44 ;
    ArrayAdapter<String> arrayAdapter;
    String[] items = {"Kailash Mansarovar","Pokhara","Janaki Mandir", "Chitwan(Nepal’s Wildlife)", "Lumbini",
            "Namche Bazar", "Gosaikunda", "Gorkha", "Nuwakot", "Manakamana", "Ghorepani", "Ghandruk", "Davis fall",
            "Mugu", "Manang", "MUstang", "Taplejung", "Illam", "Sindhuli", "Okhaldhunga","Dang","Makawanpur(Daman)",
            "Bardiya National park(Jungle Safari)", "Phoksundo lake", "Shey Gompa", "Shey Phoksundo National Park",
            "Dho Tarap valley", "Suligad waterfall", "Jakhera Lake(Dang)", "Rasuwa", "Panchakot Dham(Baglung)", "Kalika Temple(Baglung",
            "Kalinchwok Bhagwati temple(Dolakha)", " Dolakha Bhimsen Temple", " Jiri(Dolokha)", "Gaurishankar conservation area(Dolokha)",
            "Swargadwari(Pyuthan)", "Jauljibi(Darchula)", "Doti", "Kailali","Arghakot(Agrakhachi)", "Rauthat" ,"Nijgadh"};

//    ArrayAdapter<String> arrayAdapter1;
//    String[] Source = {"Bhaktapur", "Duwakot", "Changunaryan", "Byasi", "Lalitpur",
//            "Kathmandu",, "Chitwan", "Nawalparasi", "Devghat", "Thimi", "Pepsicola", "Bharatpur",
//            "Rautahat", "Mulpani", "Radhe-Radhe", "Lokanthali", "Ghataghar",
//            "Balkot", "Baneswor", "Maitighar", "Patan", "Thapathali", "Tripureswor", "Anamnagar", "Naxal", "kagbeni", "rukum", "mugu"};


    FusedLocationProviderClient mFusedLocationClient;
    Location location;
    LocationRequest locationRequest;
    ImageView img;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextViewSrc;
    EditText vehicleRentalDays;
    TextView txtName, txtDescription, txtType, txtPhoneNum, titleDetail, txtCategory;
    RatingBar txtRating;
    String title, description, imageUrl, rating, type, phone, vehicleCategory, destination_box, rentalTime, userID, srcLocation;
    Button addToList;
    private FirebaseAuth firebaseAuth;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        showLocation();
        img = findViewById(R.id.imageView);
        txtName = findViewById(R.id.txtName);
        addToList = findViewById(R.id.addToList);
        autoCompleteTextView = findViewById(R.id.destination);
//        autoCompleteTextViewSrc = findViewById(R.id.source);
        vehicleRentalDays = findViewById(R.id.days);
        titleDetail = findViewById(R.id.detailTitle);
        txtRating = findViewById(R.id.rating_star);
        txtPhoneNum = findViewById(R.id.txtPhoneNum);
        txtCategory = findViewById(R.id.category);
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destination_box = autoCompleteTextView.getText().toString();
                rentalTime = vehicleRentalDays.getText().toString().trim();

                if (destination_box.isEmpty()) {
                    autoCompleteTextView.setError("Please enter your destination");
                    return;
                }

                if (rentalTime.isEmpty()) {
                    vehicleRentalDays.setError("Please enter for how many days you want to rent a vehicle");
                    return;
                }

                int rent = Integer.parseInt(rentalTime);

                if (rent > 20) {
                    vehicleRentalDays.setError("Please choose up to 20 days or less than 20 days only");
                    return;
                }

//                String url = "http://192.168.1.67/Api/vehicleRequest.php";

                String url = "http://192.168.1.69/Api/vehicleRequest.php";

//                    String url = "http://192.168.1.68/Api/vehicleRequest.php";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        autoCompleteTextView.setText("");
                        vehicleRentalDays.setText("");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Detail.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("name", title);
                        params.put("imageUrl", imageUrl);
                        params.put("rating", rating);
                        params.put("source", srcLocation);
                        params.put("destination", destination_box);
                        params.put("days", rentalTime);
                        params.put("uid", userID);
                        return params;

                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Detail.this);
                requestQueue.add(request);

                Toast.makeText(getApplicationContext(), "Wait for admin Approval", Toast.LENGTH_SHORT).show();
                finishAffinity();
                startActivity(new Intent(getApplicationContext(),UserPage.class));
            }
        });


        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, items);
        autoCompleteTextView.setAdapter(arrayAdapter);

//        arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, Source);
//        autoCompleteTextViewSrc.setAdapter(arrayAdapter1);

        autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), 0);
            }
        });

//        autoCompleteTextViewSrc.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), 0);
//            }
//        });
    }


    protected void onStart(){
        super.onStart();
        showLocation();
    }


    private void showLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                LocationServices.getFusedLocationProviderClient(getApplicationContext()).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                    if(location!=null){
                        Geocoder geocoder = new Geocoder(Detail.this, Locale.getDefault());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            srcLocation = addressList.get(0).getAddressLine(0);

                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        Toast.makeText(Detail.this,"location is available",Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(Detail.this,"location is not available",Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                return;
//                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }



    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLocation();
            }
        }
    }









}