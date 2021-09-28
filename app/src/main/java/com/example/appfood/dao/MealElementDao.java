package com.example.appfood.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appfood.bo.MealElement;

import java.util.List;

@Dao
public interface MealElementDao {

    @Query("SELECT * FROM meal_element")
    List<MealElement> getAll();

    @Query("SELECT * FROM meal_element WHERE day = :date")
    List<MealElement> getMealDay(String date);

    @Query("SELECT * FROM meal_element WHERE day = :date AND id_meal = :id_meal")
    List<MealElement> getMealDay(String date, int id_meal);

    @Insert
    void insert(MealElement mealElement);

    @Query("DELETE FROM meal_element WHERE id = :id")
    void delete(int id);
}
