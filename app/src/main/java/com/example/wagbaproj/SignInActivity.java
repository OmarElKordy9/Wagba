package com.example.wagbaproj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private TextView signInButton;
    private EditText email, password;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailSignIn);
        password = findViewById(R.id.passwordSignIn);
        signInButton = (TextView) findViewById(R.id.signInButton);

        loadingBar = new ProgressDialog(this);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void signin(){
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();

        if (TextUtils.isEmpty(emailInput)){
            email.setError("Enter your email address");
            email.requestFocus();
        }
        else if (TextUtils.isEmpty(passwordInput)){
            password.setError("Enter your password");
            password.requestFocus();
        }
        else if(!isEmailValid(emailInput)){
            email.setError("Email address format in invalid");
            email.requestFocus();
        }
        else{
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Account credentials are being validated, just few seconds.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validateSignIn(emailInput, passwordInput);
        }
    }

    private void validateSignIn(String emailInput, String passwordInput){
        mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(SignInActivity.this,"Signed In successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignInActivity.this, MainPageActivity.class);
                            startActivity(intent);
                        } else {
                            loadingBar.dismiss();
                            System.out.println("Error"+task.getException().getMessage());

                            Toast.makeText(SignInActivity.this, "Email or password incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
}