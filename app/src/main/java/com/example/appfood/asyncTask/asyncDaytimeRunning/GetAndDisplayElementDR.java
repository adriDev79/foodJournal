package com.example.appfood.asyncTask.asyncDaytimeRunning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.appfood.R;
import com.example.appfood.bo.DaytimeRunningElement;
import com.example.appfood.dao.SqlService;

import java.util.List;

/**
 * Tâche secondaire qui récupère et affiche les élément du déroulement de la journée.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
@SuppressLint("StaticFieldLeak")
public class GetAndDisplayElementDR extends AsyncTask<Void, DaytimeRunningElement, List<DaytimeRunningElement>> {

    private final Context ctx;
    private final TableLayout table_layout;
    private final String date;
    private final LayoutInflater layout_inflater;
    private final int id_daytime_running;

    public GetAndDisplayElementDR(Context ctx, TableLayout table_layout, String date, LayoutInflater layout_inflater, int id_daytime_running) {
        this.ctx = ctx;
        this.table_layout = table_layout;
        this.date = date;
        this.layout_inflater = layout_inflater;
        this.id_daytime_running = id_daytime_running;
    }

    @Override
    protected List<DaytimeRunningElement> doInBackground(Void... voids) {
        SqlService sql_service = new SqlService();
        return sql_service.getDaytimeRunningElement(ctx, date, id_daytime_running);
    }

    @Override
    protected void onPostExecute(List<DaytimeRunningElement> elements_DR) {
        super.onPostExecute(elements_DR);
        table_layout.removeAllViews();

        if (elements_DR.size() > 0) {
            for (DaytimeRunningElement element : elements_DR) {
                TableRow table_row = (TableRow) layout_inflater.inflate(R.layout.table_row_daytime_running, null);

                TextView text_view = table_row.findViewById(R.id.tv_table_row);
                text_view.setText(element.getElementName());

                TextView tv_id = table_row.findViewById(R.id.tv_table_row_id);
                TextView tv_id_daytime = table_row.findViewById(R.id.tv_table_row_id_daytime_running);

                tv_id.setText(String.valueOf(element.getId()));
                tv_id_daytime.setText(String.valueOf(element.getIdDaytimeRunning()));

                table_layout.addView(table_row);
            }
        }
    }
}
