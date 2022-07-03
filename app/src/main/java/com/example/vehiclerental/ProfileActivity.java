package com.example.vehiclerental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseUser user;
    String currentId,imageURL,fName,fEmail,fPhone,documentID;
    private FirebaseAuth firebaseAuth;
    DocumentReference reference;
    EditText nameValue,  phoneValue;
    String name, email, phone;
    String password;
    TextView nameValue1 ,emailValue;
    AuthCredential credential;
    ImageView profile;
    Button updateB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        nameValue = findViewById(R.id.nameValue);

        nameValue1 = findViewById(R.id.nameValue1);


        emailValue = findViewById(R.id.emailValue);


        phoneValue = findViewById(R.id.phoneValue);


        profile = findViewById(R.id.profile);
        updateB = findViewById(R.id.update);


        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameValue.getText().toString();
                phone = phoneValue.getText().toString();
//                String email_pattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
//                boolean check_email = email.matches(email_pattern);

                if(name.length() < 3){
                    nameValue.setError("please enter at least 3 word ");
                    return;
                }


               if(phone.length() != 10){
                   phoneValue.setError("please enter 10 digit phone number");
                   return;
               }
                updateData();
            }
        });

        getFirestoreData();


    }

    private void getFirestoreData() {
        currentId = user.getUid();
        reference = db.collection("users").document(currentId);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    fName = task.getResult().getString("fName");
                    nameValue.setText(fName);
                    nameValue1.setText(fName);
                    fEmail = task.getResult().getString("email");
                    emailValue.setText(fEmail);
                    fPhone = task.getResult().getString("phone");
                    phoneValue.setText(fPhone);
                    imageURL = task.getResult().getString("imageLink");
                    password = task.getResult().getString("password");

                    Picasso.get().load(imageURL).into(profile);
                }
            }
        });
    }

    private void updateData(){
        Map<String,Object> userData = new HashMap<>();
        userData.put("fName",name);
        userData.put("phone",phone);

        db.collection("users").whereEqualTo("password",password)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            documentID = documentSnapshot.getId();
                            Log.d("IDDD", documentID);
                            db.collection("users")
                                    .document(documentID)
                                    .update(userData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(),"Updated success",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"error occurred!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else {
                            Toast.makeText(getApplicationContext(),"Failed to update",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}