package com.example.appfood.asyncTask.asyncDayOption;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.appfood.bo.DayOptions;
import com.example.appfood.dao.SqlService;

import java.util.List;

/**
 * Tâche secondaire qui récupère les options de la journée.
 * Si elle n'existe pas on les crées.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class GetDO extends AsyncTask<Context, DayOptions, List<DayOptions>> {
    private String date;

    public GetDO(String date) {
        this.date = date;
    }

    @SuppressLint("WrongThread")
    @Override
    protected List<DayOptions> doInBackground(Context... contexts) {
        SqlService sql_service = new SqlService();
        List<DayOptions> day_options = sql_service.getDayOptions(contexts[0], date);

        if (day_options.size() == 0) {
            DayOptions day_option = new DayOptions();
            day_option.setDay(date);
            day_option.setIsSport(false);
            day_option.setIsDrinkAlcohol(false);

            InsertDO atido = new InsertDO(day_option);
            atido.execute(contexts);
        }
        return null;
    }
}
