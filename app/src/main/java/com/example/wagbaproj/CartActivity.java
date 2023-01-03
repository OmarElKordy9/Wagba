package com.example.wagbaproj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagbaproj.Adaptors.CartAdaptor;
import com.example.wagbaproj.Models.DishesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    private RecyclerView.Adapter cartAdapter;
    private RecyclerView recyclerCartList;
    TextView totalPrice, taxesPrice, deliveryPrice, itemsTotalPrice;
    TextView checkoutButton;

    private static ArrayList<HashMap> allOrder = new ArrayList<>();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID= user.getUid();
    String userMail = user.getEmail();

    String restaurantPicture;
    String restaurantName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("itemData"));

        initiateCart();
        navigationBar();
        recyclerCart();
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             restaurantPicture = intent.getStringExtra("restaurantPicture");
             restaurantName = intent.getStringExtra("restaurantName");

             initiateCart();
        }
    };

    private void navigationBar(){
        FloatingActionButton cartButton = findViewById(R.id.cartButton);
        LinearLayout homeButton = findViewById(R.id.homeButton);
        LinearLayout logoutButton = findViewById(R.id.logoutButton);
        LinearLayout ordersButton = findViewById(R.id.ordersButton);
        LinearLayout trackingButton = findViewById(R.id.trackOrderButton);

        trackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, CurrentOrdersActivity.class);
                startActivity(intent);
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, SignInActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }

    private void initiateCart(){
        double taxPercentage = 0.14;
        double delivery = 20.00;

        recyclerCartList = findViewById(R.id.recyclerCartList);
        totalPrice = findViewById(R.id.totalPrice);
        taxesPrice = findViewById(R.id.taxesPrice);
        deliveryPrice = findViewById(R.id.deliveryPrice);
        itemsTotalPrice = findViewById(R.id.itemsTotalPrice);
        checkoutButton = findViewById(R.id.checkoutButton);

        ArrayList<String> itemsTotal = new ArrayList<>();

        for (int i = 0; i < recyclerCartList.getChildCount(); i++) {
            CartAdaptor.ViewHolder holder = (CartAdaptor.ViewHolder) recyclerCartList.getChildViewHolder(recyclerCartList.getChildAt(i));
            TextView dishNewPrice = holder.itemView.findViewById(R.id.orderTotal);
            String dishNewPriceText = dishNewPrice.getText().toString();
            itemsTotal.add(dishNewPriceText);
        }
        Double totalFee = 0.0;
        for (String s : itemsTotal) {
            Double v = Double.parseDouble((String) s);
            totalFee += v;
        }
        if(totalFee == 0.0){
            deliveryPrice.setText("0");
            totalPrice.setText("0");
        }else{
            Double taxFees = taxPercentage*totalFee;
            itemsTotalPrice.setText(String.valueOf(totalFee));
            deliveryPrice.setText(String.valueOf(delivery));
            taxesPrice.setText(String.format("%.2f", taxFees));
            Double totalPriceSum = totalFee + delivery + taxFees;
            totalPrice.setText(String.format("%.2f", totalPriceSum));

            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addOrder(totalPriceSum);
                    eraseCart(userID);
                    Toast.makeText(CartActivity.this, "Order placed Successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void addOrder(Double orderTotalPrice) {
        recyclerCartList = findViewById(R.id.recyclerCartList);
        String status = "Order Placed";
        String statusButtonText = "Accept Order";
        ArrayList<String> itemsList = new ArrayList<>();

        for (int i = 0; i < recyclerCartList.getChildCount(); i++) {
            CartAdaptor.ViewHolder holder = (CartAdaptor.ViewHolder) recyclerCartList.getChildViewHolder(recyclerCartList.getChildAt(i));
            TextView dishName = holder.itemView.findViewById(R.id.dishNameCart);
            TextView dishNumInCart = holder.itemView.findViewById(R.id.numberOfItemsCart);
            String dishNameOrder = dishName.getText().toString();
            String dishNumCartOrder = dishNumInCart.getText().toString();

            String itemDetails = dishNumCartOrder + " " + dishNameOrder;
            itemsList.add(itemDetails);
        }
        String delimiter = " + ";
        String orderDescription = String.join(delimiter, itemsList);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ordersRef = database.getReference("Orders");

        HashMap<String, Object> orderItems = new HashMap<>();
        orderItems.put("orderDescription",orderDescription);
        orderItems.put("restaurantName", restaurantName);
        orderItems.put("orderRestaurantPicture", restaurantPicture);
        orderItems.put("orderTotalPrice", String.format("%.2f", orderTotalPrice));
        orderItems.put("orderStatus", status);
        orderItems.put("statusButtonText", statusButtonText);
        orderItems.put("userID", userID);
        orderItems.put("userMail", userMail);

        allOrder.add(orderItems);
        ordersRef.child(userID).child(orderDescription).setValue(orderItems);

        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.getKey().matches(userID)) {
                        System.out.println("User Exists");
                    } else {
                        System.out.println("User doesn't Exist");
                        allOrder.clear();
                        ordersRef.child(userID);
                    }
                    allOrder.add(orderItems);
                    ordersRef.child(userID).child(orderDescription).setValue(orderItems);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Database Error", "Database Error");
            }
        });
    }

    private void eraseCart(String userID){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference cartRef = database.getReference("Cart");

        cartRef.child(userID).removeValue();

        deliveryPrice.setText("0");
        totalPrice.setText("0");
        itemsTotalPrice.setText("0");
        taxesPrice.setText("0");
    }

    private void recyclerCart(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerCartList = findViewById(R.id.recyclerCartList);
        recyclerCartList.setLayoutManager(linearLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference cartRef = database.getReference("Cart/" + userID);

        ArrayList<DishesModel> cartItems = new ArrayList<>();

        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    cartItems.add(postSnapshot.getValue(DishesModel.class));
                }
                cartAdapter = new CartAdaptor(cartItems);
                recyclerCartList.setAdapter(cartAdapter);
                recyclerCartList.setLayoutManager(linearLayoutManager);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Failure", "Failed");
            }
        });
    }
}