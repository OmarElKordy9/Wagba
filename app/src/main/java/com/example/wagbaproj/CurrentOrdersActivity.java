package com.example.wagbaproj;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class CurrentOrdersActivity extends AppCompatActivity {

    private RecyclerView.Adapter ordersAdapter;
    private RecyclerView recyclerCurrentOrdersList;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID= user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);

        initiate();
        navigationBar();
        recyclerCurrentOrders();
        showDialog();
    }

    private void initiate(){
        recyclerCurrentOrdersList = findViewById(R.id.recyclerCurrentOrdersList);
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
                Intent intent = new Intent(CurrentOrdersActivity.this, CurrentOrdersActivity.class);
                startActivity(intent);
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentOrdersActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentOrdersActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentOrdersActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentOrdersActivity.this, SignInActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }

    private void recyclerCurrentOrders(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerCurrentOrdersList = findViewById(R.id.recyclerCurrentOrdersList);
        recyclerCurrentOrdersList.setLayoutManager(linearLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = database.getReference("Orders/" + userID);

        ArrayList<OrdersModel> ordersItems = new ArrayList<>();

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ordersItems.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    String currentOrderStatus = postSnapshot.child("orderStatus").getValue().toString();
                    if(!(currentOrderStatus.matches("Delivered"))){
                        ordersItems.add(postSnapshot.getValue(OrdersModel.class));
                    }
                }
                ordersAdapter = new OrdersAdaptor(ordersItems);
                recyclerCurrentOrdersList.setAdapter(ordersAdapter);
                recyclerCurrentOrdersList.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Failure", "Failed");
            }
        });
    }
    private void showDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Current Orders Status");
            builder.setMessage("This page displays the status of your undelivered orders");

            builder.setPositiveButton("Add items to your cart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(CurrentOrdersActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel",null);
            AlertDialog dialog = builder.create();
            dialog.show();
    }
}