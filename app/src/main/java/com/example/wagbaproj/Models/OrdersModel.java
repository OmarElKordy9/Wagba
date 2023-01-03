package com.example.wagbaproj.Models;

import java.io.Serializable;

public class OrdersModel implements Serializable {
    private String restaurantName;
    private String orderDescription;
    private String orderStatus;
    private String orderRestaurantPicture;
    private String orderTotalPrice;
    private String userID;
    private String userMail;
    private String statusButtonText;

    public OrdersModel(String restaurantName, String orderDescription, String orderStatus, String orderRestaurantPicture, String orderTotalPrice, String userID, String userMail, String statusButtonText) {
        this.restaurantName = restaurantName;
        this.orderDescription = orderDescription;
        this.orderStatus = orderStatus;
        this.orderRestaurantPicture = orderRestaurantPicture;
        this.orderTotalPrice = orderTotalPrice;
        this.userID = userID;
        this.userMail = userMail;
        this.statusButtonText = statusButtonText;
    }
    public OrdersModel(){}

    public String getStatusButtonText() {
        return statusButtonText;
    }

    public void setStatusButtonText(String statusButtonText) {
        this.statusButtonText = statusButtonText;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderRestaurantPicture() {
        return orderRestaurantPicture;
    }

    public void setOrderRestaurantPicture(String orderRestaurantPicture) {
        this.orderRestaurantPicture = orderRestaurantPicture;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }
}
