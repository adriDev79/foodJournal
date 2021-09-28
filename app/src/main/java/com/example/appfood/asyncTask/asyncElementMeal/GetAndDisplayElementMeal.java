package com.example.appfood.asyncTask.asyncElementMeal;

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
import com.example.appfood.bo.MealElement;
import com.example.appfood.dao.SqlService;

import java.util.List;

/**
 * Tâche secondaire qui récupère et affiche les élements des repas.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
@SuppressLint("StaticFieldLeak")
public class GetAndDisplayElementMeal extends AsyncTask<Void, MealElement, List<MealElement>> {

    private final Context ctx;
    private final TableLayout table_layout;
    private final String date;
    private final LayoutInflater layout_inflater;
    private final int id_meal;

    public GetAndDisplayElementMeal(Context ctx, TableLayout table_layout, String date, LayoutInflater layout_inflater, int id_meal) {
        this.ctx = ctx;
        this.table_layout = table_layout;
        this.date = date;
        this.layout_inflater = layout_inflater;
        this.id_meal = id_meal;
    }

    @Override
    protected List<MealElement> doInBackground(Void... voids) {
        SqlService sql_service = new SqlService();
        return sql_service.getMealElementsWhereIdMeal(ctx, date, id_meal);
    }

    @Override
    protected void onPostExecute(List<MealElement> elements_meal) {
        super.onPostExecute(elements_meal);
        table_layout.removeAllViews();

        if (elements_meal.size() > 0) {
            for (MealElement element : elements_meal) {
                TableRow table_row = (TableRow) layout_inflater.inflate(R.layout.table_row_meal, null);

                TextView text_view = table_row.findViewById(R.id.tv_table_row_meal);
                text_view.setText(element.getElementName());

                TextView tv_id = table_row.findViewById(R.id.tv_table_row_id_element_meal);
                TextView tv_id_meal = table_row.findViewById(R.id.tv_table_row_id_meal);

                tv_id.setText(String.valueOf(element.getId()));
                tv_id_meal.setText(String.valueOf(element.getIdMeal()));

                table_layout.addView(table_row);
            }
        }
    }
}
