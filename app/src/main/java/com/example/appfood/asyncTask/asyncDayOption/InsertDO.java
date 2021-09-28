package com.example.appfood.asyncTask.asyncDayOption;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.appfood.bo.DayOptions;
import com.example.appfood.dao.SqlService;

/**
 * Tâche secondaire qui insert les options de la journée.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class InsertDO extends AsyncTask<Context, DayOptions, Void> {
    private DayOptions day_options;

    public InsertDO(DayOptions day_options) {
        this.day_options = day_options;
    }

    @Override
    protected Void doInBackground(Context... contexts) {
        SqlService sql_service = new SqlService();
        sql_service.insertDayOptions(contexts[0], day_options);
        return null;
    }
}
