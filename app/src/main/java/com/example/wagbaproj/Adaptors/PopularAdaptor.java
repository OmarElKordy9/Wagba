package com.example.wagbaproj.Adaptors;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagbaproj.Models.DishesModel;
import com.example.wagbaproj.R;
import com.example.wagbaproj.ShowDishActivity;

import java.util.ArrayList;

public class PopularAdaptor extends RecyclerView.Adapter<PopularAdaptor.ViewHolder> {

    ArrayList<DishesModel> popularDishes;

    public PopularAdaptor(ArrayList<DishesModel> dishesModels) {
        this.popularDishes = dishesModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dishName.setText(popularDishes.get(position).getDishName());
        holder.relatedRestaurant.setText(popularDishes.get(position).getRelatedRestaurant());
        holder.dishPrice.setText(String.valueOf(popularDishes.get(position).getDishPrice()));


        int drawableResourceID = holder.itemView.getContext().getResources().getIdentifier(popularDishes.get(position).getDishPicture(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceID)
                .into(holder.dishPicture);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDishActivity.class);
                intent.putExtra("dishObject", popularDishes.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularDishes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishName, relatedRestaurant, dishPrice;
        ImageView dishPicture;
        TextView addButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishName);
            relatedRestaurant = itemView.findViewById(R.id.relatedRestaurant);
            dishPrice = itemView.findViewById(R.id.dishPrice);
            dishPicture = itemView.findViewById(R.id.dishPicture);
            addButton = itemView.findViewById(R.id.addButton);
        }
    }
}
