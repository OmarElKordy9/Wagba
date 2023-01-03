package com.example.wagbaproj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private TextView registerButton;
    private TextView alreadyUser;
    private EditText fullName, phoneNumber, email, password;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    private UserDao mUserDao;
    public UserRoomDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        db = Room.databaseBuilder(getApplicationContext(), UserRoomDatabase.class, "user_database").allowMainThreadQueries().build();

        mUserDao = db.userDao();

        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.emailSignIn);
        password = findViewById(R.id.passwordSignIn);

        loadingBar = new ProgressDialog(this);

        registerButton = (TextView) findViewById(R.id.registerButton);
        alreadyUser = (TextView) findViewById(R.id.alreadyUserButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void createAccount(){
        String fullNameInput = fullName.getText().toString();
        String phoneNumberInput = phoneNumber.getText().toString();
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();

        if (TextUtils.isEmpty(fullNameInput)){
            fullName.setError("Enter your full name");
            fullName.requestFocus();
        }
        else if (TextUtils.isEmpty(phoneNumberInput)){
            phoneNumber.setError("Enter your phone number");
            phoneNumber.requestFocus();
        }
        else if (TextUtils.isEmpty(emailInput)){
            email.setError("Enter your email address");
            email.requestFocus();
        }
        else if(!isEmailValid(emailInput)){
            email.setError("Email address format in invalid");
            email.requestFocus();
        }
        else if (TextUtils.isEmpty(passwordInput)){
            password.setError("Enter your password");
            password.requestFocus();
        }
        else if(passwordInput.length() < 6){
            password.setError("Password must be more than 6 digits");
            password.requestFocus();
        }
        else{
            loadingBar.setTitle("Register");
            loadingBar.setMessage("Account is being created, just few seconds.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validate(fullNameInput, phoneNumberInput, emailInput, passwordInput);
        }

    }


    private void validate(String fullNameInput, String phoneNumberInput, String emailInput, String passwordInput){

        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUserDao.deleteAll();
                            User mUser = new User(fullNameInput, emailInput, phoneNumberInput);
                            mUserDao.insert(mUser);

                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://wagbaproj-default-rtdb.firebaseio.com/");
                            DatabaseReference myRef = database.getReference();
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        HashMap<String, Object> userDataMap = new HashMap<>();
                                        userDataMap.put("email", emailInput);
                                        userDataMap.put("fullName", fullNameInput);
                                        userDataMap.put("phoneNumber", phoneNumberInput);
                                        userDataMap.put("password", passwordInput);

                                        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(userDataMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(SignUpActivity.this, "Your account has been created successfully", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                                            startActivity(intent);
                                                        }
                                                        else{
                                                            loadingBar.dismiss();
                                                            Toast.makeText(SignUpActivity.this, "Error! Please try again", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    loadingBar.dismiss();
                                    Log.d("Failure", "Database failure");
                                }
                            });
                        } else{
                            loadingBar.dismiss();
                            Toast.makeText(SignUpActivity.this, "Email already Exists, or Password less than 6 digits", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}