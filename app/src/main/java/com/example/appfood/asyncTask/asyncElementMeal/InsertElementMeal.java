package com.example.appfood.asyncTask.asyncElementMeal;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.appfood.bo.MealElement;
import com.example.appfood.dao.SqlService;

/**
 * Tâche secondaire qui insert un élément du repas.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class InsertElementMeal extends AsyncTask<Context, MealElement, Void> {
    private MealElement element_meal;

    public InsertElementMeal(MealElement element_meal) {
        this.element_meal = element_meal;
    }

    @Override
    protected Void doInBackground(Context... contexts) {
        SqlService sql_service = new SqlService();
        sql_service.insertMealElement(contexts[0], element_meal);
        return null;
    }
}
