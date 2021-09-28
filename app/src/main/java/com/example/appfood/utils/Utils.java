package com.example.appfood.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TextView;


import androidx.fragment.app.FragmentContainerView;

import com.example.appfood.R;
import com.example.appfood.helper.JourneeHelper;
import com.example.appfood.helper.RepasHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;

public class Utils {
    public static final String NAME_ADMIN = "$@dmin@drien@pp$";

    public static final String SPORT = "sport";
    public static final String ALCOOL = "alcool";
    public static final int DELAY_LAUNCHER_PAGE = 4300;



    public static LinkedHashMap<Integer, String> meal_category_map = addCategoryMeal();
    public static LinkedHashMap<Integer, String> daytime_running_category_map = addCategoryDaytimeRunning();

    private static LinkedHashMap<Integer, String> addCategoryDaytimeRunning() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "Impressions");
        map.put(2, "Type de journée");
        map.put(3, "Activité physique");
        map.put(4, "Hydratation");
        map.put(5, "Alcool");
        return map;
    }

    public static LinkedHashMap<Integer, String> addCategoryMeal() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "Petit déjeuner");
        map.put(2, "En-cas matin");
        map.put(3, "Déjeuner");
        map.put(4, "En-cas après-midi");
        map.put(5, "Diner");
        return map;
    }

    //Je définis un objet de type OnClickListener.
    //Dedans je surcharge la fonction onClick() pour définir la réaction à un click
    public static View.OnClickListener onClickOpenActivity(Context ctx, Class c) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code réagissant à un click
                Intent intention = new Intent(ctx, c);
                ctx.startActivity(intention);
            }
        };
    }

    public static View.OnClickListener onClickOpenCalendarDaytimeRunning(Context ctx, TextView date, TextView jour, LinkedList<TableLayout> tableLayouts, Activity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDaytimeRunning(ctx, date, jour, tableLayouts, activity);
            }
        };
    }

    public static View.OnClickListener onClickCalendarJournal(Context ctx, TextView date, TextView jour) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarJournal(ctx, date, jour);
            }
        };
    }

    public static void calendarDaytimeRunning(Context context, TextView day, TextView jour, LinkedList<TableLayout> tableLayouts, Activity activity) {
        FragmentContainerView fragment = activity.findViewById(R.id.fragmentContainerView);
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                SharedPreferences settings = context.getSharedPreferences("date_journal", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("date_journal", sdf.format(myCalendar.getTime()));
                editor.apply();

                if (day != null) {
                    String date_journal = dateFormatCalendar("EEEE", myCalendar.getTime()) + " " + sdf.format(myCalendar.getTime());
                    day.setText(date_journal);
                }
                if (fragment.findViewById(R.id.ctv_activite_physique) != null) {
                    JourneeHelper jh = new JourneeHelper(sdf.format(myCalendar.getTime()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        jh.updateFragment(activity, fragment, context);
                    }

                } else {
                    RepasHelper rh = new RepasHelper(sdf.format(myCalendar.getTime()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        rh.updateFragment(activity, fragment, context);
                    }
                }
            }
        };
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void calendarJournal(Context context, TextView day, TextView jour) {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                if (day != null) {
                    day.setText(sdf.format(myCalendar.getTime()));
                    jour.setText(com.example.appfood.utils.Utils.dateFormatCalendar("EEEE", myCalendar.getTime()));
                }
            }
        };
        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String dateFormat(String pattern, LocalDate date) {
        Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.FRANCE);
        return sdf.format(d);
    }

    public static String dateFormatCalendar(String pattern, Object date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.FRANCE);
        return sdf.format(date);
    }
}

