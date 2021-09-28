package com.example.appfood.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appfood.bo.DayOptions;

import java.util.List;

@Dao
public interface DayOptionsDao {

    @Query("SELECT * FROM day_options")
    List<DayOptions> getAll();

    @Query("SELECT * FROM day_options WHERE day = :date ")
    List<DayOptions> getAllWhereDate(String date);

    @Insert
    void insert(DayOptions dayOptions);

    @Query("UPDATE day_options SET is_sport = :is_sport WHERE day = :date")
    void updateIsSPort(boolean is_sport, String date);

    @Query("UPDATE day_options SET is_drink_alcohol = :is_alcool WHERE day = :date")
    void updateIsAlcool(boolean is_alcool, String date);

    @Delete
    void delete(DayOptions dayOptions);
}
