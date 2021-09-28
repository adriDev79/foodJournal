package com.example.appfood.asyncTask.asyncDaytimeRunning;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.appfood.bo.DaytimeRunningElement;
import com.example.appfood.dao.SqlService;

/**
 * Tâche secondaire qui supprime un élément du déroulement d ela journée.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class DeleteElementDR extends AsyncTask<Context, DaytimeRunningElement, Void> {
    private final int id_element;

    public DeleteElementDR(int id_element) {
        this.id_element = id_element;
    }

    @Override
    protected Void doInBackground(Context... contexts) {
        SqlService sql_service = new SqlService();
        sql_service.deleteDaytimeRunningElement(contexts[0], id_element);
        return null;
    }
}
