package com.example.appfood.asyncTask.asyncElementMeal;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.appfood.bo.MealElement;
import com.example.appfood.dao.SqlService;

/**
 * Tâche secondaire qui supprime un élement d'un repas.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class DeleteElementMeal extends AsyncTask<Context, MealElement, Void> {

    private int id_element;

    public DeleteElementMeal(int id_element) {
        this.id_element = id_element;
    }

    @Override
    protected Void doInBackground(Context... contexts) {
        SqlService sql_service = new SqlService();
        sql_service.deleteElementMeal(contexts[0], id_element);
        return null;
    }
}
