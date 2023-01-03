package com.example.wagbaproj;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wagbaproj.Models.DishesModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowDishActivity extends AppCompatActivity {

    private TextView addToCartButton;
    private TextView dishNameShow, dishPriceShow, dishDescriptionShow, numberOfItems;
    private ImageView dishPictureShow;
    private TextView minusButton, plusButton;

    private DishesModel dishObject;
    int itemsNumber = 0;

    private static ArrayList<HashMap> allCart = new ArrayList<>();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID= user.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dish);

        dishObject = (DishesModel) getIntent().getSerializableExtra("dishObject");

        initiate();
        getBundle();
    }

    private void getBundle() {

        int drawableResourceID = this.getResources().getIdentifier(dishObject.getDishPicture(), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourceID)
                .into(dishPictureShow);

        dishNameShow.setText(dishObject.getDishName());
        dishPriceShow.setText("£" +dishObject.getDishPrice());
        dishDescriptionShow.setText(dishObject.getDishDescription());
        numberOfItems.setText(String.valueOf(itemsNumber));

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsNumber++;
                numberOfItems.setText(String.valueOf(itemsNumber));
                dishObject.setNumInCart(itemsNumber);
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemsNumber>0){
                    itemsNumber--;
                }
                numberOfItems.setText(String.valueOf(itemsNumber));
                dishObject.setNumInCart(itemsNumber);
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dishObject.getNumInCart() > 0){
                    dishObject.setUserID(userID);
                    isUserCartExist();
                    Toast.makeText(ShowDishActivity.this, "Item added to cart Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ShowDishActivity.this, "Number of items 0, can't add to cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void isUserCartExist(){
        String dishNameInput = dishObject.getDishName();
        int dishQuantityInput = dishObject.getNumInCart();
        String relatedRestaurantInput = dishObject.getRelatedRestaurant();
        Double dishPriceInput = dishObject.getDishPrice();
        String dishPictureInput = dishObject.getDishPicture();
        String cartUser = dishObject.getUserID();
        String restaurantPicture = dishObject.getRestaurantPicture();


        HashMap<String, Object> cartItems = new HashMap<>();
        cartItems.put("dishName", dishNameInput);
        cartItems.put("dishPicture", dishPictureInput);
        cartItems.put("dishPrice", dishPriceInput);
        cartItems.put("eachItemTotal", dishQuantityInput*dishPriceInput);
        cartItems.put("numInCart", dishQuantityInput);
        cartItems.put("relatedRestaurant", relatedRestaurantInput);
        cartItems.put("restaurantPicture", restaurantPicture);
        cartItems.put("userID", cartUser);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference cartRef = database.getReference("Cart/");

        allCart.add(cartItems);
        cartRef.child(cartUser).child(dishNameInput).setValue(cartItems);

        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.getKey().matches(cartUser)) {
                        System.out.println("User Exists");
                    } else {
                        System.out.println("User doesn't Exist");
                        allCart.clear();
                        cartRef.child(cartUser);
                    }
                    allCart.add(cartItems);
                    cartRef.child(cartUser).child(dishNameInput).setValue(cartItems);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Database Error", "Database Error");
            }
        });
    }

    private void initiate(){
        addToCartButton = findViewById(R.id.addToCartButton);
        dishNameShow = findViewById(R.id.dishNameShow);
        dishPriceShow = findViewById(R.id.dishPriceShow);
        dishDescriptionShow = findViewById(R.id.dishDescriptionShow);
        numberOfItems = findViewById(R.id.numberOfItemsCart);
        plusButton = findViewById(R.id.plusButtonCart);
        minusButton = findViewById(R.id.minusButtonCart);
        dishPictureShow = findViewById(R.id.dishPictureShow);
    }
}