package com.example.wagbaproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackOrderActivity extends AppCompatActivity {

    private TextView orderStatus;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID= user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        orderStatus = findViewById(R.id.orderStatusTrack);

        showStatus();
        navigationBar();
    }

    private void showStatus(){
        Bundle extra = getIntent().getBundleExtra("extraStatus");
        String orderDescription = (String) extra.getSerializable("orderDescription");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = database.getReference("Orders/" + userID);


        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                   String status = snapshot.child(orderDescription).child("orderStatus").getValue().toString();
                   orderStatus.setText(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Failure", "Failed");
            }
        });
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
                Intent intent = new Intent(TrackOrderActivity.this, CurrentOrdersActivity.class);
                startActivity(intent);
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackOrderActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackOrderActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackOrderActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackOrderActivity.this, SignInActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }
}