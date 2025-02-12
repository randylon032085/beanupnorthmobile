package com.example.beanupnorth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Registration extends AppCompatActivity {

    EditText edtUser, edtEmail, edtPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        edtUser = findViewById(R.id.editTextText);
        edtEmail = findViewById(R.id.editTextTextEmailAddress);
        edtPassword = findViewById(R.id.editTextTextPassword);

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance("https://bean-upnorth.firebaseio.com/").getReference("Users");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void registerUser(){
        String name = edtUser.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(name)
        || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(this, "All field are required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<8){
            Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               FirebaseUser user = mAuth.getCurrentUser();
               if(user!=null){
                    saveUserDatabase(user.getUid(), name, email, password);
               }else{
                   Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
               }
           }
        });

    }
    private void saveUserDatabase(String userId, String name, String email, String password){
    User user = new User(name, email, password);
    mDataBase.child(userId).setValue(user).addOnCompleteListener(task -> {
        if(task.isSuccessful()){
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Registration.this, Login.class));
            finish();
        }else{
            Toast.makeText(this, "Database Error", Toast.LENGTH_SHORT).show();
        }
    });
    }

    static class User {
        public String name, email, password;

        public User(){

        }
        public User(String name, String email, String password){
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }

    public void returnLogin(View v){
        registerUser();
//        startActivity(new Intent(Registration.this, Login.class));
    }
}