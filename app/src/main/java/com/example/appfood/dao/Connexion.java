package com.example.appfood.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Connexion {

    public static AppDataBase getConnexion(Context ctx) {
        return Room
                .databaseBuilder(ctx, AppDataBase.class, "food.bdd")
                .allowMainThreadQueries()
                .build();
    }
}


