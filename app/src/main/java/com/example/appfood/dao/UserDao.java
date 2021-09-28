package com.example.appfood.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appfood.bo.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM utilisateur")
    List<User> getAll();

    @Insert
    void insert(User user);

    @Update
    void update(User user);
}
