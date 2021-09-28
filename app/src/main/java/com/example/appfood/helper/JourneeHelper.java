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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.R;
import com.example.appfood.asyncTask.asyncDaytimeRunning.DeleteElementDR;
import com.example.appfood.asyncTask.asyncDaytimeRunning.InsertElementDR;
import com.example.appfood.asyncTask.asyncDayOption.UpdateDO;
import com.example.appfood.asyncTask.asyncUtils.Utils;
import com.example.appfood.bo.DaytimeRunningElement;

import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class JourneeHelper {

    private TextView tv_date_journee;
    private String calendar_date;

    // Constructeurs
    public JourneeHelper(String calendar_date) {
        this.calendar_date = calendar_date;
    }
    public JourneeHelper(TextView tv_date_journee) {
        this.tv_date_journee = tv_date_journee;
    }

    /**
     * Ajouter un élément du déroulement de la journée
     *
     * @param ctx context de l'activité
     * @param view vue de l'activité
     * @param fragJournee fragment de la vue (journée)
     */
    public void addElementJournee(Context ctx, View view, View fragJournee) {
        LinkedList<ImageView> image_views = new LinkedList<>();
        LinkedList<EditText> edits_text = new LinkedList<>();

        image_views.add(fragJournee.findViewById(R.id.iv_validation_impression_journee));
        image_views.add(fragJournee.findViewById(R.id.iv_validation_type_journee));
        image_views.add(fragJournee.findViewById(R.id.iv_validation_activite_physique));
        image_views.add(fragJournee.findViewById(R.id.iv_validation_hydratation));
        image_views.add(fragJournee.findViewById(R.id.iv_validation_alcool));

        edits_text.add(fragJournee.findViewById(R.id.et_impression_journee));
        edits_text.add(fragJournee.findViewById(R.id.et_type_journee));
        edits_text.add(fragJournee.findViewById(R.id.et_activite_physique));
        edits_text.add(fragJournee.findViewById(R.id.et_hydratation));
        edits_text.add(fragJournee.findViewById(R.id.et_alcool));
        int id_daytime_running = 0;
        EditText edit_text = null;
        DaytimeRunningElement dtre = new DaytimeRunningElement();

        int position = 0;
        for (ImageView iv : image_views) {
            if (iv != null) {
                if (iv.equals(view)) {
                    id_daytime_running = position + 1;
                    edit_text = edits_text.get(position);
                }
            }
            position ++;
        }

        if (edit_text.getText().toString().equals("")) {
            Toast.makeText(ctx, "Le champs ne peut pas être vide !", Toast.LENGTH_LONG).show();
        } else {
            if (edit_text.getText().toString().length() > 30) {
                Toast.makeText(ctx, "30 caractères maximum !", Toast.LENGTH_LONG).show();
            } else {
                String[] date = tv_date_journee.getText().toString().split(" ");
                dtre.setDay(date[1]);
                dtre.setElementName(edit_text.getText().toString());
                dtre.setIdDaytimeRunning(id_daytime_running);

                try {
                    InsertElementDR atidr = new InsertElementDR(dtre);
                    atidr.execute(ctx);
                    Toast.makeText(ctx, "L'élément a bien été ajouté !", Toast.LENGTH_LONG).show();
                    edit_text.setText("");
                } catch (Exception e) {
                    Log.e("FOOD", e.getMessage());
                }
                edit_text.setText("");
            }
        }
    }

    /**
     * Supression d'un élémnet du déroulement de la journée
     *
     * @param ctx context de l'activité
     * @param view vue de l'activité
     */
    public void deleteElementJournee(Context ctx, View view) {
        TableRow row = (TableRow) view.getParent();
        TextView tv_id = row.findViewById(R.id.tv_table_row_id);

        try {
            DeleteElementDR atddr = new DeleteElementDR(Integer.parseInt(tv_id.getText().toString()));
            atddr.execute(ctx);
            Toast.makeText(ctx, "L'élément a bien été supprimé !", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("FOOD", e.getMessage());
        }
    }

    /**
     * Mis à jour des options de la journée
     *
     * @param ctx context de l'activité
     * @param view vue de la l'activité
     * @param fragment_journee vue du fragment de la journée
     */
    public void updateOptionJournee(Context ctx, View view, View fragment_journee) {
        String[] date = tv_date_journee.getText().toString().split(" ");
        CheckBox cb_sport = fragment_journee.findViewById(R.id.ctv_activite_physique);
        CheckBox cb_alcool = fragment_journee.findViewById(R.id.ctv_alcool);

        try {
            if (cb_sport.equals(view)) {
                try {
                    UpdateDO atudo = new UpdateDO(true, false, cb_sport.isChecked(), date[1]);
                    atudo.execute(ctx);
                    Toast.makeText(ctx, "L'option à bien été modifié !", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("FOOD", e.getMessage());
                }
            } else if (cb_alcool.equals(view)) {
                try {
                    UpdateDO atudo = new UpdateDO(false, true, cb_alcool.isChecked(), date[1]);
                    atudo.execute(ctx);
                    Toast.makeText(ctx, "L'option à bien été modifié !", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("FOOD", e.getMessage());
                }
            }
        } catch (Exception e) {
            Log.e("FOOD", e.getMessage());
        }
    }

    /**
     * Mis à jour du fragment de la journée
     *
     * @param activity activité en cours d'execution
     * @param fragment_journee vue de la journée
     * @param ctx context de l'activité
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void updateFragment(Activity activity, View fragment_journee, Context ctx) {
        List<Fragment> fragment = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fragment = activity.getFragmentManager().getFragments();
        }
        Fragment frag = fragment.get(0);
        if (frag != null) {
            CheckBox cb_sport = fragment_journee.findViewById(R.id.ctv_activite_physique);
            CheckBox cb_alcool = fragment_journee.findViewById(R.id.ctv_alcool);

            LayoutInflater inflater = activity.getLayoutInflater();

            LinkedList<TableLayout> table_layouts = new LinkedList<>();
            table_layouts.add(fragment_journee.findViewById(R.id.tl_impression_journee));
            table_layouts.add(fragment_journee.findViewById(R.id.tl_type_journee));
            table_layouts.add(fragment_journee.findViewById(R.id.tl_activite_physique));
            table_layouts.add(fragment_journee.findViewById(R.id.tl_hydratation));
            table_layouts.add(fragment_journee.findViewById(R.id.tl_alcool));

            String date = "";
            if (tv_date_journee != null) {
                String[] d = tv_date_journee.getText().toString().split(" ");
                date = d[1];
            } else {
                date = calendar_date;
            }

            for (int i = 0; i < table_layouts.size(); i++) {
                Utils.executeAsyncDaytimeRunning(ctx, table_layouts.get(i), date, inflater, i + 1);
            }
            Utils.executeAsyncDayOptions(ctx, date, cb_sport, cb_alcool);
            final FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                ft.detach(frag);
                ft.attach(frag);
                ft.commit();
            }

        }
    }
}
