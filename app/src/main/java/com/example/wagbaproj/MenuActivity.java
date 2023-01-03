package com.example.wagbaproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagbaproj.Adaptors.MenuAdaptor;
import com.example.wagbaproj.Models.DishesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterMenu;
    private RecyclerView recyclerMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initiate();
        recyclerMenu();
        navigationBar();
    }

    private void initiate(){
        recyclerMenuList = findViewById(R.id.recyclerMenu);
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
                Intent intent = new Intent(MenuActivity.this, CurrentOrdersActivity.class);
                startActivity(intent);
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, SignInActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }

    private void recyclerMenu(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerMenuList = findViewById(R.id.recyclerMenu);
        recyclerMenuList.setLayoutManager(linearLayoutManager);

        Bundle extra = getIntent().getBundleExtra("extra");
        ArrayList<DishesModel> dishes = (ArrayList<DishesModel>) extra.getSerializable("menu");

        adapterMenu = new MenuAdaptor(dishes);
        recyclerMenuList.setAdapter(adapterMenu);
    }
}