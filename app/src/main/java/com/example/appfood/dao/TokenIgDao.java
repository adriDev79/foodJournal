package com.example.appfood.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appfood.bo.TokenIg;

import java.util.List;

@Dao
public interface TokenIgDao {

    @Query("SELECT * FROM token_ig")
    List<TokenIg> getAll();

    @Insert
    void insert(TokenIg tokenIg);

    @Delete
    void delete(TokenIg tokenIg);

    @Update
    void update(TokenIg tokenIg);
}
