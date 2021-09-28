package com.example.appfood.asyncTask.asyncDaytimeRunning;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.appfood.bo.DaytimeRunningElement;
import com.example.appfood.dao.SqlService;

/**
 * Tâche secondaire qui insert un element du déroulement de la journée.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class InsertElementDR extends AsyncTask<Context, DaytimeRunningElement, Void> {
    DaytimeRunningElement element_DR;

    public InsertElementDR(DaytimeRunningElement element_DR) {
        this.element_DR = element_DR;
    }

    @Override
    protected Void doInBackground(Context... contexts) {
        SqlService sql_service = new SqlService();
        sql_service.insertDaytimeRunningElement(contexts[0], element_DR);
        return null;
    }
}
