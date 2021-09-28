package com.example.appfood.asyncTask.asyncUtils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.TableLayout;

import com.example.appfood.asyncTask.asyncDayOption.GetAndDisplayDO;
import com.example.appfood.asyncTask.asyncDaytimeRunning.GetAndDisplayElementDR;
import com.example.appfood.asyncTask.asyncElementMeal.GetAndDisplayElementMeal;
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class Utils {
    public static void executeAsyncDaytimeRunning(Context ctx, TableLayout table_impression, String date, LayoutInflater inflater, int id_daytime_running) {
        GetAndDisplayElementDR atdr = new GetAndDisplayElementDR(ctx, table_impression, date, inflater, id_daytime_running);
        atdr.execute();
    }

    public static void executeAsyncDayOptions(Context ctx, String date, CheckBox is_sport, CheckBox is_alcool) {
        GetAndDisplayDO atdo = new GetAndDisplayDO(ctx, date, is_sport, is_alcool);
        atdo.execute();
    }

    public static void executeAsyncMeal(Context ctx, TableLayout table_impression, String date, LayoutInflater inflater, int id_meal) {
        GetAndDisplayElementMeal asm = new GetAndDisplayElementMeal(ctx, table_impression, date, inflater, id_meal);
        asm.execute();
    }
}
