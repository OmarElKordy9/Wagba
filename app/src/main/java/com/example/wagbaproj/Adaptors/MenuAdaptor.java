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

public class MenuAdaptor extends RecyclerView.Adapter<MenuAdaptor.ViewHolder> {

    ArrayList<DishesModel> dishesModels;


    public MenuAdaptor(ArrayList<DishesModel> dishesModels) {
        this.dishesModels = dishesModels;
    }

    @NonNull
    @Override
    public MenuAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_menu_item ,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dishNameMenu.setText(dishesModels.get(position).getDishName());
        holder.itemPriceMenu.setText(String.valueOf(dishesModels.get(position).getDishPrice()));


        int drawableResourceID = holder.itemView.getContext().getResources().getIdentifier(dishesModels.get(position).getDishPicture(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceID)
                .into(holder.dishPictureMenu);

        holder.addButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDishActivity.class);
                intent.putExtra("dishObject", dishesModels.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dishesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishNameMenu, itemPriceMenu;
        ImageView dishPictureMenu;
        TextView addButtonMenu;
        View restaurantLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishNameMenu = itemView.findViewById(R.id.dishNameMenu);
            dishPictureMenu = itemView.findViewById(R.id.dishPictureMenu);
            itemPriceMenu = itemView.findViewById(R.id.itemPriceMenu);
            addButtonMenu = itemView.findViewById(R.id.addButtonMenu);
            restaurantLayout = itemView.findViewById(R.id.restaurantLayout);
        }
    }
}