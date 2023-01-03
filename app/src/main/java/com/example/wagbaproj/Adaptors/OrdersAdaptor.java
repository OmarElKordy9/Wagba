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
import com.example.wagbaproj.Models.OrdersModel;
import com.example.wagbaproj.R;
import com.example.wagbaproj.TrackOrderActivity;

import java.util.ArrayList;

public class OrdersAdaptor extends RecyclerView.Adapter<OrdersAdaptor.ViewHolder> {

    ArrayList<OrdersModel> ordersModels;


    public OrdersAdaptor(ArrayList<OrdersModel> ordersModels) {
        this.ordersModels = ordersModels;
    }

    @NonNull
    @Override
    public OrdersAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order ,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.restaurantNameOrder.setText(ordersModels.get(position).getRestaurantName());
        holder.orderDescription.setText(String.valueOf(ordersModels.get(position).getOrderDescription()));
        holder.orderTotal.setText(String.valueOf(ordersModels.get(position).getOrderTotalPrice()));
        holder.orderStatus.setText(String.valueOf(ordersModels.get(position).getOrderStatus()));

        int drawableResourceID = holder.itemView.getContext().getResources().getIdentifier(ordersModels.get(position).getOrderRestaurantPicture(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceID)
                .into(holder.restaurantPictureOrder );

        holder.orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), TrackOrderActivity.class);
                Bundle extra = new Bundle();

                extra.putSerializable("orderDescription", ordersModels.get(holder.getAdapterPosition()).getOrderDescription());
                intent.putExtra("extraStatus", extra);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return ordersModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameOrder, orderDescription;
        ImageView restaurantPictureOrder;
        TextView orderTotal, orderStatus;
        ConstraintLayout orderLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           restaurantNameOrder = itemView.findViewById(R.id.restaurantNameOrder);
           orderDescription = itemView.findViewById(R.id.orderDescription);
           restaurantPictureOrder = itemView.findViewById(R.id.restaurantPictureOrder);
           orderTotal = itemView.findViewById(R.id.orderTotal);
           orderStatus = itemView.findViewById(R.id.orderStatus);
           orderLayout = itemView.findViewById(R.id.orderLayout);
        }
    }
}