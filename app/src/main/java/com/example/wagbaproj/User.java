package com.example.wagbaproj;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mID;
    @ColumnInfo(name = "username")
    String username;
    @ColumnInfo(name = "email")
    String email;
    @ColumnInfo(name = "phone")
    String phone;

    public User(@NonNull String username, String email, String phone){
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public void setID(int mID){
        this.mID = mID;
    }
    public String getUsername(){return this.username;}
    public String getEmail(){return this.email;}
    public String getPhone(){return this.phone;}
    public int getID(){return this.mID;}
}

