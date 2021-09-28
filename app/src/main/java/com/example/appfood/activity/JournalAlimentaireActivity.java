package com.example.appfood.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.appfood.R;
import com.example.appfood.helper.JourneeHelper;
import com.example.appfood.helper.RepasHelper;
import com.example.appfood.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class JournalAlimentaireActivity extends AppCompatActivity {
    Context ctx = this;
    BottomNavigationView bottom_nav_view;
    TextView tv_date_journee;
    TextView tv_jour_journee;
    LinearLayout ll_date_picker;
    ImageButton iv_retour;
    CheckBox cb_sport;
    CheckBox cb_alcool;
    LinkedList<TableLayout> table_layouts;
    FragmentContainerView fragment_container;

    Activity activity = this;

    JourneeHelper jh;
    RepasHelper rh;
    Date now = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_alimentaire2);
        fragment_container = findViewById(R.id.fragmentContainerView);
        tv_date_journee = findViewById(R.id.tv_date_journal);
        ll_date_picker = findViewById(R.id.ll_date_journal);
        iv_retour = findViewById(R.id.ib_home_journal);
        cb_sport = findViewById(R.id.ctv_activite_physique);
        cb_alcool = findViewById(R.id.ctv_alcool);

        jh = new JourneeHelper(tv_date_journee);
        rh = new RepasHelper(tv_date_journee);

        // Initialisation de la date du jour
        SharedPreferences settings = ctx.getSharedPreferences("date", 0);
        String load_date = settings.getString("date", "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        if (load_date.length() > 0) {
            try {
                @SuppressLint("SimpleDateFormat")
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(load_date);
                tv_date_journee.setText(Utils.dateFormatCalendar("EEEE", date) + " " + load_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            tv_date_journee.setText(Utils.dateFormatCalendar("EEEE", now) + " " + sdf.format(now));
        }

        // Initialisation des bouttons de navigations
        bottom_nav_view = findViewById(R.id.activity_main_bottom_navigation);
        NavController nav_controller = Navigation.findNavController(JournalAlimentaireActivity.this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottom_nav_view, nav_controller);

        // Initialisation du date picker et du bouton retour
        ll_date_picker.setOnClickListener(Utils.onClickOpenCalendarDaytimeRunning(ctx, tv_date_journee,tv_jour_journee, table_layouts, activity));
        iv_retour.setOnClickListener(Utils.onClickOpenActivity(ctx, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = ctx.getSharedPreferences("date_journal", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("date_journal", "");
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void add(View view) {
        fragment_container = findViewById(R.id.fragmentContainerView);
        jh.addElementJournee(ctx, view, fragment_container);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            jh.updateFragment(activity, fragment_container, ctx);
        }
    }

    public void delete(View view) {
        fragment_container = findViewById(R.id.fragmentContainerView);
        jh.deleteElementJournee(ctx, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            jh.updateFragment(activity, fragment_container, ctx);
        }
    }

    public void update(View view) {
        fragment_container = findViewById(R.id.fragmentContainerView);
        jh.updateOptionJournee(ctx, view, fragment_container);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            jh.updateFragment(activity, fragment_container, ctx);
        }
    }

    public void addIngredient(View view) {
        fragment_container = findViewById(R.id.fragmentContainerView);
        rh.addElementRepas(ctx, view, fragment_container);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            rh.updateFragment(activity, fragment_container, ctx);
        }
    }

    public void deleteIngredient(View view) {
        fragment_container = findViewById(R.id.fragmentContainerView);
        rh.deleteElementRepas(ctx, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            rh.updateFragment(activity, fragment_container, ctx);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void changeDate(View view) {
        FragmentContainerView fragment = findViewById(R.id.fragmentContainerView);

        String date_journal = tv_date_journee.getText().toString();
        String[] date = date_journal.split(" ");
        date = date[1].split("/");
        Date d = new Date(date[2] + "/" + date[1] + "/" + date[0]);
        LocalDate ld = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        SharedPreferences settings = ctx.getSharedPreferences("date_journal", 0);
        SharedPreferences.Editor editor = settings.edit();

        if (findViewById(R.id.ib_chevron_left).equals(view)) {
            ld = ld.minusDays(1);
        } else if (findViewById(R.id.ib_chevron_right).equals(view)){
            ld = ld.plusDays(1);
        }

        String day = Utils.dateFormat("EEEE", ld);
        String complete_date = Utils.dateFormat("dd/MM/yyyy", ld);
        date_journal = day + " " + complete_date;
        tv_date_journee.setText(date_journal);

        if (fragment.findViewById(R.id.ctv_activite_physique) != null) {
            JourneeHelper jh = new JourneeHelper(complete_date);
            jh.updateFragment(activity, fragment, ctx);

        } else {
            RepasHelper rh = new RepasHelper(complete_date);
            rh.updateFragment(activity, fragment, ctx);
        }
        editor.putString("date_journal", complete_date);
        editor.apply();
    }
}