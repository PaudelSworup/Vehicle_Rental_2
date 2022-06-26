package com.example.vehiclerental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRequest extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Users> vehicles;

    //    private String JSON_URL = "http://192.168.1.67/Api/getUser.php";
//    private String imageUrl = "http://192.168.1.67/Api/images/";


    private String JSON_URL = "http://192.168.1.70/Api/getUser.php";
    private String imageUrl = "http://192.168.1.70/Api/images/";


//    private String JSON_URL = "http://192.168.1.69/Api/getUser.php";
//    private String imageUrl = "http://192.168.1.69/Api/images/";

    UserAdapter adapter;
    LinearLayoutManager llm ;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String userID;
    String jsonObj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request);
        recyclerView = findViewById(R.id.userRequest);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();
        vehicles = new ArrayList<>();
        adapter = new UserAdapter(getApplicationContext(),vehicles);
        llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        getData(JSON_URL);
    }







    public void getData(String url) {
        if(url == null){
            Toast.makeText(getApplicationContext(),"null null", Toast.LENGTH_SHORT).show();
        }
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                Log.d("Success", response.toString());
                if(response.length() == 0){
                    Toast.makeText(getApplicationContext(),"User request has not been approved yet",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    try {
                        String res = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
                        JSONObject jsonObject = new JSONObject(res);
                        Log.d("Success object", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Users users = new Users();
                                users.setUserImage(jsonObject1.getString("image").toString());
                                users.setUserDate(jsonObject1.getString("dates").toString());
                                users.setUserFid(jsonObject1.getString("fid").toString());
                                users.setUserName(jsonObject1.getString("name").toString());
                                users.setUserDestination(jsonObject1.getString("destination").toString());
                                users.setUserRental(jsonObject1.getString("rental_days").toString());
                                users.setUserAmount(jsonObject1.getString("amount").toString());
                                jsonObj = jsonObject1.getString("fid").toString();
                                if(userID.equals(jsonObj)){
                                    vehicles.add(users);
                                }else  Toast.makeText(getApplicationContext(),"User request has not been approved yet",Toast.LENGTH_SHORT).show();

                            }
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        Log.e("error is ", "my json " + e);
                        e.printStackTrace();
                    }
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