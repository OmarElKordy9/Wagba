package com.example.wagbaproj.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantsModel implements Serializable {
    private String restaurantName = null;
    private String restaurantPicture = null;
    private ArrayList<DishesModel> dishes = null;

    public RestaurantsModel(){}

    public RestaurantsModel(String restaurantName, String restaurantPicture, ArrayList<DishesModel> dishes) {
        this.restaurantName = restaurantName;
        this.restaurantPicture = restaurantPicture;
        this.dishes = dishes;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantPicture() {
        return restaurantPicture;
    }

    public void setRestaurantPicture(String restaurantPicture) {
        this.restaurantPicture = restaurantPicture;
    }

    public ArrayList<DishesModel> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<DishesModel> dishes) {
        this.dishes = dishes;
    }
}
