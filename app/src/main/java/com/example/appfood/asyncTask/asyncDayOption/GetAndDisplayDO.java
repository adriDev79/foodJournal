package com.example.appfood.asyncTask.asyncDayOption;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.CheckBox;

import com.example.appfood.bo.DayOptions;
import com.example.appfood.dao.SqlService;

import java.util.List;

/**
 * Tâche secondaire qui récupère les options de la journée et qui les affichent.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
@SuppressLint("StaticFieldLeak")
public class GetAndDisplayDO extends AsyncTask<Void, DayOptions, List<DayOptions>> {

    private final Context ctx;
    private final String date;
    private final CheckBox cb_sport;
    private final CheckBox cb_alcool;

    public GetAndDisplayDO(Context ctx , String date, CheckBox cb_sport, CheckBox cb_alcool) {
        this.ctx = ctx;
        this.date = date;
        this.cb_sport = cb_sport;
        this.cb_alcool = cb_alcool;
    }

    @Override
    protected List<DayOptions> doInBackground(Void... voids) {
        SqlService sql_service = new SqlService();
        return sql_service.getDayOptions(ctx, date);
    }

    @Override
    protected void onPostExecute(List<DayOptions> day_options) {
        super.onPostExecute(day_options);

        if (day_options.size() > 0) {
            cb_sport.setChecked(day_options.get(0).isSport());
            cb_alcool.setChecked(day_options.get(0).getIsDrinkAlcohol());
        }
    }
}
