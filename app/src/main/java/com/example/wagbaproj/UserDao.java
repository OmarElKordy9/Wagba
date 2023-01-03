package com.example.wagbaproj;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void insert (User user);


    @Query("SELECT * from user_table")
    User getUsers();

    @Query("DELETE FROM user_table")
    void deleteAll();
}