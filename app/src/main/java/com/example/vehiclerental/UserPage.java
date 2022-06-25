package com.example.vehiclerental;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SliderView sliderView;
    int[] images = {
            R.drawable.bg,
            R.drawable.car1,
            R.drawable.car2,
            R.drawable.cars3,
            R.drawable.car4
    };

    RecyclerView recyclerView;
    List<Vehicle> vehicles;
//    private String JSON_URL = "http://192.168.1.67/Api/get.php";
//    private String imageUrl = "http://192.168.1.67/Api/images/";


    private String JSON_URL = "http://192.168.1.70/Api/get.php";
    private String imageUrl = "http://192.168.1.70/Api/images/";

//    private String JSON_URL = "http://192.168.1.68/Api/get.php";
//    private String imageUrl = "http://192.168.1.68/Api/images/";

    Adapter adapter;
    LinearLayoutManager llm;

    private FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseFirestore db;
    FirebaseUser user;
    String currentId,imageURL;

    DocumentReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        recyclerView = findViewById(R.id.vehicleList);
        vehicles = new ArrayList<>();
        adapter = new Adapter(getApplicationContext(), vehicles);
        llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        currentId = user.getUid();
        getFirestoreData();
        navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        extractData();

// image slider
        sliderView = findViewById(R.id.imageSlider);

        sliderView = findViewById(R.id.imageSlider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//         item.setChecked(true);
        drawerLayout.closeDrawers();
        int id = item.getItemId();
        if (id == R.id.home_menu) {
            Toast.makeText(UserPage.this, "home is clicked", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.dashboard_menu) {
            Toast.makeText(UserPage.this, "dash is clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), UserDashboard.class));
        }

        if (id == R.id.list_menu) {
            Toast.makeText(UserPage.this, "list is clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),UserRequest.class));

        }

        if(id == R.id.profile_menu){
            Toast.makeText(UserPage.this, "profile is clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }

        if (id == R.id.logout_menu) {
            Toast.makeText(UserPage.this, "logout is clicked", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            finishAffinity();
            startActivity(new Intent(getApplicationContext(), Login_Activity.class));
            finish();
        }
        return true;
    }

    public void extractData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                Log.d("Success", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    Log.d("Success object", jsonObject.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Vehicle vec = new Vehicle();
                            vec.setName(jsonObject1.getString("name").toString());
                            vec.setImage(imageUrl + jsonObject1.getString("image"));
                            vec.setRating(jsonObject1.getString("rating").toString());
                            vec.setPhone(jsonObject1.getString("phone").toString());
                            vec.setType(jsonObject1.getString("type").toString());
                            vec.setDescription(jsonObject1.getString("car_detail").toString());
                            vec.setCategory(jsonObject1.getString("category").toString());
                            vehicles.add(vec);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.e("error is ", "my json " + e);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
    }

    private void getFirestoreData() {
        reference = db.collection("users").document(currentId);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    imageURL = task.getResult().getString("imageLink");
                    Toast.makeText(getApplicationContext(),"image"+imageURL,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


