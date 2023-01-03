package com.example.wagbaproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    public UserViewModel mUserViewModel;


    UserRoomDatabase db;
    private UserDao mUserDao;
    private TextView fullName, email, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        navigationBar();

        fullName = findViewById(R.id.fullNameData);
        email = findViewById(R.id.emailData);
        phoneNumber = findViewById(R.id.phoneNumberData);

        db = Room.databaseBuilder(getApplicationContext(), UserRoomDatabase.class, "user_database").allowMainThreadQueries().build();
        mUserDao = db.userDao();

        User user = mUserDao.getUsers();
        String fullNameData = user.username;
        String emailData = user.email;
        String phoneData = user.phone;

        fullName.setText(fullNameData);
        email.setText(emailData);
        phoneNumber.setText(phoneData);
    }
    private void navigationBar(){
        FloatingActionButton cartButton = findViewById(R.id.cartButton);
        LinearLayout homeButton = findViewById(R.id.homeButton);
        LinearLayout logoutButton = findViewById(R.id.logoutButton);
        LinearLayout ordersButton = findViewById(R.id.ordersButton);
        LinearLayout trackingButton = findViewById(R.id.trackOrderButton);

        trackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, TrackOrderActivity.class);
                startActivity(intent);
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, SignInActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }
}