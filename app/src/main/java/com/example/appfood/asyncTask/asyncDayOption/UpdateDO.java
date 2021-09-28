package com.example.appfood.asyncTask.asyncDayOption;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.appfood.bo.DayOptions;
import com.example.appfood.dao.SqlService;

/**
 * Tâche qui met à jour les options de la journée.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class UpdateDO extends AsyncTask<Context, DayOptions, Void> {
    private boolean sport;
    private boolean alcool;
    private boolean option;
    private String date;

    public UpdateDO(boolean sport, boolean alcool, boolean option, String date) {
        this.sport = sport;
        this.alcool = alcool;
        this.option = option;
        this.date = date;
    }

    @Override
    protected Void doInBackground(Context... contexts) {
        SqlService sql_service = new SqlService();
        if (sport) {
            sql_service.updateSport(contexts[0], option, date);
        }
        if (alcool) {
            sql_service.updateAlcool(contexts[0], option, date);
        }
        return null;
    }
}
