package com.example.appfood.helper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.R;
import com.example.appfood.asyncTask.asyncElementMeal.DeleteElementMeal;
import com.example.appfood.asyncTask.asyncElementMeal.InsertElementMeal;
import com.example.appfood.asyncTask.asyncUtils.Utils;
import com.example.appfood.bo.MealElement;

import java.util.LinkedList;
import java.util.List;

public class RepasHelper {

    private TextView tv_date_journee;
    private String calendar_date;

    // Constructeurs
    public RepasHelper(TextView tv_date_journee) {
        this.tv_date_journee = tv_date_journee;
    }
    public RepasHelper(String calendar_date) {
        this.calendar_date = calendar_date;
    }


    /**
     * Ajout d'un élémnent du repas
     *
     * @param ctx context de l'activité
     * @param view vue de l'activité
     * @param fragment_repas vue du fragment des repas
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void addElementRepas(Context ctx, View view, View fragment_repas) {
        LinkedList<ImageView> image_views = new LinkedList<>();
        LinkedList<EditText> edits_text = new LinkedList<>();

        image_views.add(fragment_repas.findViewById(R.id.iv_validation_petit_dejeuner));
        image_views.add(fragment_repas.findViewById(R.id.iv_validation_collation_matin));
        image_views.add(fragment_repas.findViewById(R.id.iv_validation_dejeuner));
        image_views.add(fragment_repas.findViewById(R.id.iv_validation_collation_apres_midi));
        image_views.add(fragment_repas.findViewById(R.id.iv_validation_diner));

        edits_text.add(fragment_repas.findViewById(R.id.et_petit_dejeuner));
        edits_text.add(fragment_repas.findViewById(R.id.et_collation_matin));
        edits_text.add(fragment_repas.findViewById(R.id.et_dejeuner));
        edits_text.add(fragment_repas.findViewById(R.id.et_collation_apres_midi));
        edits_text.add(fragment_repas.findViewById(R.id.et_diner));

        int id_meal = 0;
        EditText edit_text = null;
        MealElement element = new MealElement();

        int position = 0;
        for (ImageView iv : image_views) {
            if (iv != null) {
                if (iv.equals(view)) {
                    id_meal = position + 1;
                    edit_text = edits_text.get(position);
                }
            }
            position ++;
        }

        if (edit_text != null) {
            if (edit_text.getText().toString().equals("")) {
                Toast.makeText(ctx, "Le champs ne peut pas être vide !", Toast.LENGTH_LONG).show();
            } else {
                if (edit_text.getText().toString().length() > 30) {
                    Toast.makeText(ctx, "30 caractères maximum !", Toast.LENGTH_LONG).show();
                } else {
                    String[] date = tv_date_journee.getText().toString().split(" ");
                    element.setDay(date[1]);
                    element.setElementName(edit_text.getText().toString());
                    element.setIdMeal(id_meal);

                    try {
                        InsertElementMeal atime = new InsertElementMeal(element);
                        atime.execute(ctx);
                        Toast.makeText(ctx, "L'ingrédient a bien été ajouté ! ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("FOOD", e.getMessage());
                    }
                }
            }
            edit_text.setText("");
        }
    }

    /**
     * Supression d'un élément du repas
     *  @param ctx context de l'activité
     * @param view vue de l'activité*/
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void deleteElementRepas(Context ctx, View view) {
        TableRow row = (TableRow) view.getParent();
        TextView tv_id = row.findViewById(R.id.tv_table_row_id_element_meal);

        try {
            DeleteElementMeal atdem = new DeleteElementMeal(Integer.parseInt(tv_id.getText().toString()));
            atdem.execute(ctx);
            Toast.makeText(ctx, "L'ingrédient a bien été supprimé ", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("FOOD", e.getMessage());
        }
    }

    /**
     * Mis à jour du fragment des repas
     *
     * @param activity activité en cours d'execution
     * @param fragment_repas fragment des repas
     * @param ctx context de l'application
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public  void updateFragment(Activity activity, View fragment_repas, Context ctx) {
        List<Fragment> fragment = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fragment = activity.getFragmentManager().getFragments();
        }
        Fragment frag = fragment.get(0);
        if (frag != null) {
            LayoutInflater inflater = activity.getLayoutInflater();

            LinkedList<TableLayout> table_layouts = new LinkedList<>();
            table_layouts.add(fragment_repas.findViewById(R.id.tl_petit_dejeuner));
            table_layouts.add(fragment_repas.findViewById(R.id.tl_collation_matin));
            table_layouts.add(fragment_repas.findViewById(R.id.tl_dejeuner));
            table_layouts.add(fragment_repas.findViewById(R.id.tl_collation_apres_midi));
            table_layouts.add(fragment_repas.findViewById(R.id.tl_diner));

            String date;
            if (tv_date_journee != null) {
                String[] d = tv_date_journee.getText().toString().split(" ");
                date = d[1];
            } else {
                date = calendar_date;
            }

            for (int i = 0; i < table_layouts.size(); i++) {
                Utils.executeAsyncMeal(ctx, table_layouts.get(i), date, inflater, i + 1);
            }

            final FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                ft.detach(frag);
                ft.attach(frag);
                ft.commit();
            }
        }
    }
}
