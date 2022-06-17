package com.example.vehiclerental;

import androidx.annotation.NonNull;
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
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    List<Admin> vehicles;
    private String JSON_URL = "http://192.168.1.67/Api/Adminget.php";
    private String imageUrl = "http://192.168.1.67/Api/images/";


//    private String JSON_URL = "http://192.168.1.70/Api/Adminget.php";
//    private String imageUrl = "http://192.168.1.70/Api/images/";


//    private String JSON_URL = "http://192.168.1.69/Api/Adminget.php";
//    private String imageUrl = "http://192.168.1.69/Api/images/";

    AdminAdapter adapter;
    LinearLayoutManager llm ;

    private FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        recyclerView = findViewById(R.id.UserList);
        vehicles = new ArrayList<>();
        adapter = new AdminAdapter(getApplicationContext(),vehicles);
        llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        firebaseAuth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.navigationview);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        getData();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//         item.setChecked(true);
        drawerLayout.closeDrawers();
        int id = item.getItemId();
        if(id == R.id.home_menu){
            Toast.makeText(AdminPanel.this, "home is clicked",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Index.class));
        }

        if(id == R.id.dashboard_menu){
            Toast.makeText(AdminPanel.this, "dash is clicked",Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.list_menu){
            Toast.makeText(AdminPanel.this, "list is clicked",Toast.LENGTH_SHORT).show();

        }

        if(id == R.id.logout_menu){
            Toast.makeText(AdminPanel.this, "logout is clicked",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            finishAffinity();
            startActivity(new Intent(getApplicationContext(),Login_Activity.class));
            finish();
        }
        return true;
    }

    public void getData() {
        StringRequest request = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("not null")) {
                    Log.d("Success", response.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                        Log.d("Success object", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Admin admin = new Admin();
                                admin.setAdminId(jsonObject1.getString("id").toString());
                                admin.setAdminName(jsonObject1.getString("name").toString());
                                admin.setImageAdmin(jsonObject1.getString("image").toString());
                                admin.setAdminRating(jsonObject1.getString("rating").toString());
                                admin.setSource(jsonObject1.getString("source").toString());
                                admin.setDestination(jsonObject1.getString("destination").toString());
                                admin.setRentalTime(jsonObject1.getString("rentalday").toString());
                                admin.setFirebase_id(jsonObject1.getString("firebase_id").toString());
                                vehicles.add(admin);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        Log.e("error is ", "my json " + e);
                        e.printStackTrace();
                    }

                }else {
                        Toast.makeText(AdminPanel.this, "null data",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }
}