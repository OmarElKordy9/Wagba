package com.example.wagbaproj.Adaptors;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagbaproj.Models.DishesModel;
import com.example.wagbaproj.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CartAdaptor extends RecyclerView.Adapter<CartAdaptor.ViewHolder> {


    ArrayList<DishesModel> dishesModels;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID = user.getUid();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference cartRef = database.getReference("Cart/" + userID);


    public CartAdaptor(ArrayList<DishesModel> dishesModels) {
        this.dishesModels = dishesModels;
    }

    @NonNull
    @Override
    public CartAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart ,parent,false);
        return new ViewHolder(inflate);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CartAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final int[] itemsNumber = {dishesModels.get(position).getNumInCart()};
        holder.dishNameCart.setText(dishesModels.get(position).getDishName());
        holder.eachItemPrice.setText(String.valueOf(dishesModels.get(position).getDishPrice()));
        holder.eachItemTotal.setText(String.valueOf(dishesModels.get(position).getEachItemTotal()));
        holder.numberOfItemsCart.setText(String.valueOf(dishesModels.get(position).getNumInCart()));


        int drawableResourceID = holder.itemView.getContext().getResources().getIdentifier(dishesModels.get(position).getDishPicture(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceID)
                .into(holder.dishPictureCart );


        String restaurantPicture = dishesModels.get(position).getRestaurantPicture();
        String restaurantName = dishesModels.get(position).getRelatedRestaurant();

        Intent intent = new Intent("itemData");
        intent.putExtra("restaurantPicture",restaurantPicture);
        intent.putExtra("restaurantName",restaurantName);

        LocalBroadcastManager.getInstance(holder.itemView.getContext()).sendBroadcast(intent);


        holder.plusButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsNumber[0]++;
                dishesModels.get(position).setNumInCart(itemsNumber[0]);
                holder.numberOfItemsCart.setText(String.valueOf(itemsNumber[0]));
                holder.eachItemTotal.setText(String.valueOf(dishesModels.get(position).getEachItemTotal()));

                int numInCart = dishesModels.get(position).getNumInCart();
                String eachItemTotal = holder.eachItemTotal.getText().toString();
                String dishName = holder.dishNameCart.getText().toString();
                String restaurantPicture = dishesModels.get(position).getRestaurantPicture();

                Intent intent = new Intent("itemData");
                intent.putExtra("restaurantPicture",restaurantPicture);

                LocalBroadcastManager.getInstance(holder.itemView.getContext()).sendBroadcast(intent);

                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String key = snapshot.child(dishName).child("numInCart").getValue().toString();
                        System.out.println(key);
                        System.out.println("Added ------ " + numInCart);
                        cartRef.child(dishName).child("numInCart").setValue(numInCart);
                        cartRef.child(dishName).child("eachItemTotal").setValue(Double.parseDouble(eachItemTotal));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Failure", "Failed");
                    }
                });

            }
        });
        holder.minusButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemsNumber[0] >1){
                    itemsNumber[0]--;
                    dishesModels.get(position).setNumInCart(itemsNumber[0]);
                }
                holder.numberOfItemsCart.setText(String.valueOf(itemsNumber[0]));
                holder.eachItemTotal.setText(String.valueOf(dishesModels.get(position).getEachItemTotal()));

                int numInCart = dishesModels.get(position).getNumInCart();
                String eachItemTotal = holder.eachItemTotal.getText().toString();
                String dishName = holder.dishNameCart.getText().toString();

                String restaurantPicture = dishesModels.get(position).getRestaurantPicture();

                Intent intent = new Intent("itemData");
                intent.putExtra("restaurantPicture",restaurantPicture);

                LocalBroadcastManager.getInstance(holder.itemView.getContext()).sendBroadcast(intent);

                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cartRef.child(dishName).child("numInCart").setValue(numInCart);
                        cartRef.child(dishName).child("eachItemTotal").setValue(Double.parseDouble(eachItemTotal));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Failure", "Failed");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishNameCart, eachItemPrice;
        ImageView dishPictureCart;
        TextView plusButtonCart, minusButtonCart;
        TextView eachItemTotal, numberOfItemsCart;
        ConstraintLayout cartLayout;
        TextView orderTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishNameCart = itemView.findViewById(R.id.dishNameCart);
            dishPictureCart = itemView.findViewById(R.id.dishPictureCart);
            plusButtonCart = itemView.findViewById(R.id.plusButtonCart);
            minusButtonCart = itemView.findViewById(R.id.minusButtonCart);
            eachItemPrice = itemView.findViewById(R.id.eachItemPrice);
            eachItemTotal = itemView.findViewById(R.id.orderTotal);
            numberOfItemsCart = itemView.findViewById(R.id.numberOfItemsCart);
            cartLayout = itemView.findViewById(R.id.cartItemLayout);

            orderTotal = itemView.findViewById(R.id.orderTotal);
        }
    }
}