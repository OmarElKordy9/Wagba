package com.example.wagbaproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagbaproj.Adaptors.OrdersAdaptor;
import com.example.wagbaproj.Models.OrdersModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView.Adapter ordersAdapter;
    private RecyclerView recyclerOrdersList;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID= user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        initiate();
        navigationBar();
        recyclerOrders();
    }

    private void initiate(){
        recyclerOrdersList = findViewById(R.id.recyclerOrdersList);
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
                Intent intent = new Intent(OrdersActivity.this, CurrentOrdersActivity.class);
                startActivity(intent);
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdersActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdersActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdersActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdersActivity.this, SignInActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }

    private void recyclerOrders(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerOrdersList = findViewById(R.id.recyclerOrdersList);
        recyclerOrdersList.setLayoutManager(linearLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = database.getReference("Orders/" + userID);

        ArrayList<OrdersModel> ordersItems = new ArrayList<>();

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ordersItems.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    ordersItems.add(postSnapshot.getValue(OrdersModel.class));
                }
                ordersAdapter = new OrdersAdaptor(ordersItems);
                recyclerOrdersList.setAdapter(ordersAdapter);
                recyclerOrdersList.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Failure", "Failed");
            }
        });
    }
}