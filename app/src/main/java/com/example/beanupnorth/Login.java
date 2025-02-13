package com.example.beanupnorth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private GoogleSignInClient googleSignInClient;
    Button googleLoginButton;
    private FirebaseAuth mAuth;
    EditText editUser, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        googleLoginButton = findViewById(R.id.button4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Google Login
        googleLoginButton.setOnClickListener(view -> signInWithGoogle());

        mAuth = FirebaseAuth.getInstance();
        editUser = findViewById(R.id.etUser);
        editPassword = findViewById(R.id.etPassword);
        // Google Sign-In Config
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }
public void loginHomeScreen(View v){
        String email = editUser.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,task -> {
            if(task.isSuccessful()){
                FirebaseUser user = mAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(this, "Login Successful ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, HomeScreen.class));
                }else{
                    Toast.makeText(this, "Login Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


}

public void createAccount (View v){
    startActivity(new Intent(Login.this, Registration.class));
}

//    public void signInGoogle (View v){
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        googleSignInClient = GoogleSignIn.getClient(this, gso);
//        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
//    }
private void signInWithGoogle() {
    Intent signInIntent = googleSignInClient.getSignInIntent();
    startActivityForResult(signInIntent, 100);
}

}
