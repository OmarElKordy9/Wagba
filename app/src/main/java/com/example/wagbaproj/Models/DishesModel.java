package com.example.wagbaproj.Models;

import java.io.Serializable;

public class DishesModel implements Serializable {
    private String dishName;
    private String dishPicture;
    private String relatedRestaurant;
    private String dishDescription;
    private Double dishPrice;
    private int numInCart;
    private String userID;
    private Double eachItemTotal;
    private String restaurantPicture;

    public DishesModel(){}

//    public DishesModel(String dishName, String dishPicture, String relatedRestaurant, Double dishPrice, int numInCart, String userID) {
//        this.dishName = dishName;
//        this.dishPicture = dishPicture;
//        this.relatedRestaurant = relatedRestaurant;
//        this.dishPrice = dishPrice;
//        this.numInCart = numInCart;
//        this.userID = userID;
//    }

    public DishesModel(String dishName, String dishPicture, String relatedRestaurant, String dishDescription, Double dishPrice) {
        this.dishName = dishName;
        this.dishPicture = dishPicture;
        this.relatedRestaurant = relatedRestaurant;
        this.dishDescription = dishDescription;
        this.dishPrice = dishPrice;
    }

//    public DishesModel(String dishName, String dishPicture, String relatedRestaurant, String dishDescription, Double dishPrice, int numInCart) {
//        this.dishName = dishName;
//        this.dishPicture = dishPicture;
//        this.relatedRestaurant = relatedRestaurant;
//        this.dishDescription = dishDescription;
//        this.dishPrice = dishPrice;
//        this.numInCart = numInCart;
//    }

    public DishesModel(String dishName, String dishPicture, String relatedRestaurant, String restaurantPicture, String dishDescription, Double dishPrice) {
        this.dishName = dishName;
        this.dishPicture = dishPicture;
        this.relatedRestaurant = relatedRestaurant;
        this.dishDescription = dishDescription;
        this.dishPrice = dishPrice;
        this.restaurantPicture = restaurantPicture;
    }

    public String getRestaurantPicture() {
        return restaurantPicture;
    }

    public void setRestaurantPicture(String restaurantPicture) {
        this.restaurantPicture = restaurantPicture;
    }

    public Double getEachItemTotal() {
        eachItemTotal = dishPrice * numInCart;
        return eachItemTotal;
    }

    public void setEachItemTotal(Double eachItemTotal) {
        this.eachItemTotal = eachItemTotal;
    }

    public String getUserID() {
        return userID;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishPicture() {
        return dishPicture;
    }

    public void setDishPicture(String dishPicture) {
        this.dishPicture = dishPicture;
    }

    public String getRelatedRestaurant() {
        return relatedRestaurant;
    }

    public void setRelatedRestaurant(String relatedRestaurant) {
        this.relatedRestaurant = relatedRestaurant;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public Double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getNumInCart() {
        return numInCart;
    }

    public void setNumInCart(int numInCart) {
        this.numInCart = numInCart;
    }
}
