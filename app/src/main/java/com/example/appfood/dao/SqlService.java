package com.example.appfood.dao;

import android.content.Context;

import com.example.appfood.bo.DayOptions;
import com.example.appfood.bo.DaytimeRunningElement;
import com.example.appfood.bo.MealElement;
import com.example.appfood.bo.TokenIg;
import com.example.appfood.bo.User;

import java.util.List;

public class SqlService {

    public void insertDaytimeRunningElement(Context ctx, DaytimeRunningElement daytimeRunningElement) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.daytimeRunningElementDao().insert(daytimeRunningElement);
        bdd.close();
    }

    public List<DaytimeRunningElement> getDaytimeRunningElement(Context ctx, String date, int id) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<DaytimeRunningElement> daytimeRunningElements = bdd.daytimeRunningElementDao().getDaytimeRunningDay(date, id);
        bdd.close();
        return daytimeRunningElements;
    }

    public List<DaytimeRunningElement> getAllDaytimeRunningElement(Context ctx) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<DaytimeRunningElement> daytimeRunningElements = bdd.daytimeRunningElementDao().getAllDaytimeRunningDay();
        bdd.close();
        return  daytimeRunningElements;
    }

    public void deleteDaytimeRunningElement(Context ctx, int id) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.daytimeRunningElementDao().Delete(id);
        bdd.close();
    }

    public List<DayOptions> getDayOptions(Context ctx, String date) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<DayOptions> dayOptions = bdd.dayOptionsDao().getAllWhereDate(date);
        bdd.close();
        return dayOptions;
    }

    public List<DayOptions> getAllDayOptions(Context ctx) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<DayOptions> dayOptions = bdd.dayOptionsDao().getAll();
        bdd.close();
        return dayOptions;
    }

    public void insertDayOptions(Context ctx, DayOptions dayOptions) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.dayOptionsDao().insert(dayOptions);
        bdd.close();
    }

    public void updateSport(Context ctx, boolean is_sport, String date) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.dayOptionsDao().updateIsSPort(is_sport, date);
        bdd.close();
    }

    public void updateAlcool(Context ctx, boolean is_alcool, String date) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.dayOptionsDao().updateIsAlcool(is_alcool, date);
        bdd.close();
    }

    public List<MealElement> getMealElementsWhereIdMeal(Context ctx, String date, int id_meal) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<MealElement> mealElements = bdd.mealElementDao().getMealDay(date, id_meal);
        bdd.close();
        return mealElements;
    }

    public List<MealElement> getMealElements(Context ctx) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<MealElement> mealElements = bdd.mealElementDao().getAll();
        bdd.close();
        return mealElements;
    }


    public void insertMealElement(Context ctx, MealElement mealElement) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.mealElementDao().insert(mealElement);
        bdd.close();
    }

    public void deleteElementMeal(Context ctx, int id) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.mealElementDao().delete(id);
        bdd.close();
    }

    public List<User> getAllUser(Context ctx) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<User> users = bdd.userDao().getAll();
        bdd.close();
        return users;
    }

    public void insertUser(Context ctx, User user) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.userDao().insert(user);
        bdd.close();
    }

    public void updateUser(Context ctx, User user) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.userDao().update(user);
        bdd.close();
    }

    public void insertTokenIg(Context ctx, TokenIg tokenIg) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.tokenIgDao().insert(tokenIg);
        bdd.close();
    }

    public List<TokenIg> getAllTokenIg(Context ctx) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        List<TokenIg> tokenIgs = bdd.tokenIgDao().getAll();
        bdd.close();
        return tokenIgs;
    }

    public void updateToken(Context ctx, TokenIg token) {
        AppDataBase bdd = Connexion.getConnexion(ctx);
        bdd.tokenIgDao().update(token);
        bdd.close();
    }
}
