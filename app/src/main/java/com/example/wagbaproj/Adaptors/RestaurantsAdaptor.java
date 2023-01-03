package com.example.wagbaproj.Adaptors;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagbaproj.CartActivity;
import com.example.wagbaproj.MenuActivity;
import com.example.wagbaproj.Models.RestaurantsModel;
import com.example.wagbaproj.R;

import java.util.ArrayList;

public class RestaurantsAdaptor extends RecyclerView.Adapter<RestaurantsAdaptor.ViewHolder> {

    ArrayList<RestaurantsModel> restaurantsModels;

    public RestaurantsAdaptor(ArrayList<RestaurantsModel> restaurantsModels) {
        this.restaurantsModels = restaurantsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_restaurant,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.restaurantName.setText(restaurantsModels.get(position).getRestaurantName());

        int drawableResourceID = holder.itemView.getContext().getResources().getIdentifier(restaurantsModels.get(position).getRestaurantPicture(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceID)
                .into(holder.restaurantPicture);

        holder.restaurantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), MenuActivity.class);
                Bundle extra = new Bundle();

                Intent intentRestaurant = new Intent(holder.itemView.getContext(), CartActivity.class);
                intentRestaurant.putExtra("restaurantObject", restaurantsModels.get(position));

                extra.putSerializable("menu", restaurantsModels.get(holder.getAdapterPosition()).getDishes());
                intent.putExtra("extra", extra);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView restaurantName;
        ImageView restaurantPicture;
        ConstraintLayout restaurantLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            restaurantPicture = itemView.findViewById(R.id.restaurantPicture);
            restaurantLayout = itemView.findViewById(R.id.restaurantLayout);
        }
    }
}
