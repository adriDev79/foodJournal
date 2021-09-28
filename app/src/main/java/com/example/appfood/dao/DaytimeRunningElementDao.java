package com.example.appfood.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appfood.bo.DaytimeRunningElement;
import com.example.appfood.bo.MealElement;

import java.util.List;

@Dao
public interface DaytimeRunningElementDao {
    @Query("SELECT * FROM daytime_running_element")
    List<DaytimeRunningElement> getAllDaytimeRunningDay();

    @Query("SELECT * FROM daytime_running_element WHERE day = :date AND id_daytime_running = :id")
    List<DaytimeRunningElement> getDaytimeRunningDay(String date, int id);

    @Query("SELECT * FROM daytime_running_element WHERE id_daytime_running = :id_daytime_running AND day = :date")
    List<DaytimeRunningElement> getDaytimeRunningWhereId(int id_daytime_running, String date);

    @Insert
    void insert(DaytimeRunningElement daytimeRunningElement);

    @Query("DELETE FROM daytime_running_element WHERE id = :id")
    void Delete(int id);
}
