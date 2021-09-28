package com.example.appfood.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.appfood.bo.DayOptions;
import com.example.appfood.bo.DaytimeRunningElement;
import com.example.appfood.bo.MealElement;
import com.example.appfood.bo.TokenIg;
import com.example.appfood.bo.User;

@Database(entities = {DayOptions.class, DaytimeRunningElement.class, MealElement.class, User.class, TokenIg.class}, version = 1, exportSchema = true)
    public abstract class AppDataBase extends RoomDatabase {
        public abstract DaytimeRunningElementDao daytimeRunningElementDao();
        public abstract DayOptionsDao dayOptionsDao();
        public abstract  MealElementDao mealElementDao();
        public abstract UserDao userDao();
        public abstract TokenIgDao tokenIgDao();

}