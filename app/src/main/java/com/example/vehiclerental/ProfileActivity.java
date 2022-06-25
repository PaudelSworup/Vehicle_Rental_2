package com.example.vehiclerental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseUser user;
    String currentId,imageURL,fName,fEmail,fPhone;
    private FirebaseAuth firebaseAuth;
    DocumentReference reference;
    TextView name, nameValue, email, emailValue, phone, phoneValue;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        currentId = user.getUid();
        getFirestoreData();

        name = findViewById(R.id.name);
        nameValue = findViewById(R.id.nameValue);


        email = findViewById(R.id.email);
        emailValue = findViewById(R.id.emailValue);


        phone = findViewById(R.id.phone);
        phoneValue = findViewById(R.id.phoneValue);


        profile = findViewById(R.id.profile);


    }

    private void getFirestoreData() {
        reference = db.collection("users").document(currentId);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    fName = task.getResult().getString("fName");
                    nameValue.setText(fName);
                    fEmail = task.getResult().getString("email");
                    emailValue.setText(fEmail);
                    fPhone = task.getResult().getString("phone");
                    phoneValue.setText(fPhone);
                    imageURL = task.getResult().getString("imageLink");
                    Picasso.get().load(imageURL).into(profile);
                }
            }
        });
    }
}