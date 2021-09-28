package com.example.appfood.asyncTask.asyncSendJournal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.appfood.R;
import com.example.appfood.bo.Day;
import com.example.appfood.bo.DayOptions;
import com.example.appfood.bo.DaytimeRunningElement;
import com.example.appfood.bo.MealElement;
import com.example.appfood.dao.SqlService;
import com.example.appfood.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Tâche secodaire qui crée le pdf du journal
 * Envoi du journal par email
 * Téléchargement du journal
 */
@android.support.annotation.RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
@SuppressLint("StaticFieldLeak")
public class SendAndDownloadJournal extends AsyncTask<Void, Day, List<Day>> {

    private final Context ctx;
    private final TextView date_debut;
    private final TextView date_fin;
    private final boolean email;
    private String name_user;

    public SendAndDownloadJournal(Context ctx, TextView date_debut, TextView date_fin, boolean email, String name_user) {
        this.ctx = ctx;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.email = email;
        this.name_user = name_user;
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected List<Day> doInBackground(Void... voids) {
        SqlService sql_service = new SqlService();
        List<Day> days = new ArrayList<>();
        String[] dd = date_debut.getText().toString().split("/");
        String[] df = date_fin.getText().toString().split("/");
        Date debut = new Date(dd[2] + "/" + dd[1] + "/" + dd[0]);
        Date fin = new Date(df[2] + "/" + df[1] + "/" + df[0]);

        List<MealElement> elements_meal = sql_service.getMealElements(ctx);
        List<DaytimeRunningElement> elements_DR = sql_service.getAllDaytimeRunningElement(ctx);
        List<DayOptions> options_day = sql_service.getAllDayOptions(ctx);

        LocalDate start = debut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = fin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            Day day = new Day();
            day.setDate(date);

            LinkedHashMap<String, LinkedList<String>> meals_map = new LinkedHashMap<>();
            for (String meal : Utils.meal_category_map.values()) {
                meals_map.put(meal, new LinkedList<>());
            }
            LinkedHashMap<String, LinkedList<String>> daytime_running_map = new LinkedHashMap<>();
            for (String daytime : Utils.daytime_running_category_map.values()) {
                daytime_running_map.put(daytime, new LinkedList<>());
            }
            LinkedHashMap<String, String> options_day_map = new LinkedHashMap<>();

            for (MealElement element : elements_meal) {
                String[] de = element.getDay().split("/");
                Date d = new Date(de[2] + "/" + de[1] + "/" + de[0]);
                LocalDate element_date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (date.isEqual(element_date)) {
                    String meal_name = Utils.meal_category_map.get(element.getIdMeal());
                    Objects.requireNonNull(meals_map.get(meal_name)).add(element.getElementName());
                }
            }

            for (DaytimeRunningElement element : elements_DR) {
                String[] de = element.getDay().split("/");
                Date d = new Date(de[2] + "/" + de[1] + "/" + de[0]);
                LocalDate element_date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (date.isEqual(element_date)) {
                    String daytime_running_name = Utils.daytime_running_category_map.get(element.getIdDaytimeRunning());
                    daytime_running_map.get(daytime_running_name).add(element.getElementName());
                }
            }
            for (DayOptions element : options_day) {
                String[] de = element.getDay().split("/");
                Date d = new Date(de[2] + "/" + de[1] + "/" + de[0]);
                LocalDate element_date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (date.isEqual(element_date)) {
                    String is_sport = element.isSport() ? "oui" : "non";
                    String is_alcool = element.getIsDrinkAlcohol() ? "oui" : "non";
                    Log.i("FOOD", is_alcool + " " + is_sport);
                    options_day_map.put(Utils.SPORT, is_sport);
                    options_day_map.put(Utils.ALCOOL, is_alcool);
                }
            }
            day.setMeals(meals_map);
            day.setDaytimeRunning(daytime_running_map);
            day.setDayOptions(options_day_map);
            days.add(day);
        }
        return days;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onPostExecute(List<Day> days) {
        super.onPostExecute(days);
        LinkedHashMap<String, List<Day>> week = new LinkedHashMap<>();
        PdfDocument document = new PdfDocument();

        // Création des semaines
        if (days.size() > 7) {
            int nb_week = (days.size() / 7);
            for (int i = 0; i <= nb_week; i++) {
                week.put("week_" + i, new ArrayList<>());
            }
            int nb_day = 1;
            int position_week = 0;
            for (Day day : days) {
                if (week.get("week_" + position_week) != null) {
                    Objects.requireNonNull(week.get("week_" + position_week)).add(day);
                }
                if (nb_day == 7) {
                    nb_day = 1;
                    position_week ++;
                } else {
                    nb_day ++;
                }
            }
            for (List<Day> list_day : week.values()) {
                weekMealPdf(document, list_day);
                weekDaytimeRunningPdf(document, list_day);
            }
        } else {
            weekMealPdf(document, days);
            weekDaytimeRunningPdf(document, days);
        }

        // Date du journal
        String[] d_debut = date_debut.getText().toString().split("/");
        String[] d_fin = date_fin.getText().toString().split("/");
        String debut = d_debut[0] + "_" + d_debut[1] + "_" + d_debut[2];
        String fin = d_fin[0] + "_" + d_fin[1] + "_" + d_fin[2];

        String name_journal = "journal_" + debut +"_au_" + fin + ".pdf";

        if (email) {
            try {
                File file = new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + "/" + name_journal);
                Uri content_uri = Uri.fromFile(file);
                OutputStream os = new FileOutputStream(file);
                document.writeTo(os);
                document.close();
                os.close();

                shareDocument(content_uri);
            } catch (IOException e) {
                throw new RuntimeException("Error generating file", e);
            }
        } else {
            File file = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + name_journal);
            Toast.makeText(ctx, "le téléchargement à débuté, retrouvez votre pdf dans le dossier suivant : " + Environment.DIRECTORY_DOWNLOADS, Toast.LENGTH_LONG).show();

            try {
//                document.writeTo(new FileOutputStream(file));
               OutputStream os = new FileOutputStream(file);
                document.writeTo(os);
                document.close();
                os.close();

            } catch (IOException e) {
                Log.e("FOOD", e.getMessage());
            }
            document.close();
        }
    }

    private void shareDocument(Uri uri) {
        SharedPreferences settings = ctx.getSharedPreferences("user", 0);
        String u = settings.getString("name_user","");

        Intent email_intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", ctx.getString(R.string.email_nutritionniste), null));
        // Subject
        String subject = "Journal de " + u + " du " + date_debut.getText().toString() + " au " + date_fin.getText().toString();
        email_intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        // Check if the device has an email client
        email_intent.putExtra(Intent.EXTRA_STREAM, uri);

        // Check if the device has an email client
        ctx.startActivity(Intent.createChooser(email_intent,"Choisissez votre application"));
    }


    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void weekDaytimeRunningPdf(PdfDocument document, List<Day> list_day) {
        PdfDocument.PageInfo page_info_daytime_running = new PdfDocument.PageInfo.Builder(2010, 1200, 1).create();
        PdfDocument.Page page_daytime_running = document.startPage(page_info_daytime_running);
        Canvas canvas_daytime_running = page_daytime_running.getCanvas();
        Paint my_paint = new Paint();


        my_paint.setTextAlign(Paint.Align.LEFT);
        my_paint.setStyle(Paint.Style.FILL);
        my_paint.setTextSize(40f);
        my_paint.setColor(Color.RED);
        canvas_daytime_running.drawText("Déroulement de la journée", 25, 50, my_paint);
        int position_x = 140;

        for (Day day : list_day) {
            int position_date_y = 100;
            int interval_title_y = 50;
            int interval_element_y = 30;

            String date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = Utils.dateFormat("EEEE dd/MM", day.getDate());
            }

            my_paint.setTextAlign(Paint.Align.CENTER);
            my_paint.setStyle(Paint.Style.FILL);
            my_paint.setTextSize(35f);
            my_paint.setColor(Color.BLACK);
            canvas_daytime_running.drawText(date, position_x, position_date_y, my_paint);
            int position_y = position_date_y;
                for (String daytime_running : day.getDaytimeRunning().keySet()) {
                    position_y += interval_title_y;
                    if (daytime_running.equals("Activité physique")) {
                        my_paint.setTextAlign(Paint.Align.CENTER);
                        my_paint.setStyle(Paint.Style.FILL);
                        my_paint.setTextSize(30f);
                        my_paint.setColor(Color.parseColor("#147587"));
                        canvas_daytime_running.drawText(daytime_running, position_x, position_y, my_paint);
                        if (day.getDayOptions().get(Utils.SPORT) != null) {
                            position_y += interval_element_y;
                            Log.i("FOOD", "je suis pas null");
                            my_paint.setTextAlign(Paint.Align.CENTER);
                            my_paint.setStyle(Paint.Style.FILL);
                            my_paint.setTextSize(30f);
                            my_paint.setColor(Color.parseColor("#147587"));
                            canvas_daytime_running.drawText(day.getDayOptions().get(Utils.SPORT), position_x, position_y, my_paint);
                        }
                    } else if (daytime_running.equals("Alcool")) {
                        my_paint.setTextAlign(Paint.Align.CENTER);
                        my_paint.setStyle(Paint.Style.FILL);
                        my_paint.setTextSize(30f);
                        my_paint.setColor(Color.parseColor("#147587"));
                        canvas_daytime_running.drawText(daytime_running, position_x, position_y, my_paint);
                        if (day.getDayOptions().get(Utils.ALCOOL) != null) {
                            position_y += interval_element_y;
                            my_paint.setTextAlign(Paint.Align.CENTER);
                            my_paint.setStyle(Paint.Style.FILL);
                            my_paint.setTextSize(30f);
                            my_paint.setColor(Color.parseColor("#147587"));
                            canvas_daytime_running.drawText(day.getDayOptions().get(Utils.ALCOOL), position_x, position_y, my_paint);
                        }
                    } else {
                        my_paint.setTextAlign(Paint.Align.CENTER);
                        my_paint.setStyle(Paint.Style.FILL);
                        my_paint.setTextSize(30f);
                        my_paint.setColor(Color.parseColor("#147587"));
                        canvas_daytime_running.drawText(daytime_running, position_x, position_y, my_paint);
                    }
                    for (String element : Objects.requireNonNull(day.getDaytimeRunning().get(daytime_running))) {
                        position_y += interval_element_y;
                        my_paint.setTextAlign(Paint.Align.CENTER);
                        my_paint.setStyle(Paint.Style.FILL);
                        my_paint.setTextSize(20f);
                        my_paint.setColor(Color.BLACK);
                        canvas_daytime_running.drawText(element, position_x, position_y, my_paint);
                    }
                }
            position_x += 280;
        }
        document.finishPage(page_daytime_running);
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void weekMealPdf(PdfDocument document, List<Day> list_day) {
        PdfDocument.PageInfo page_info_meal = new PdfDocument.PageInfo.Builder(2010, 1200, 1).create();
        PdfDocument.Page page_meal = document.startPage(page_info_meal);
        Canvas canvas_meal = page_meal.getCanvas();
        Paint my_paint = new Paint();

        my_paint.setTextAlign(Paint.Align.LEFT);
        my_paint.setStyle(Paint.Style.FILL);
        my_paint.setTextSize(40f);
        my_paint.setColor(Color.RED);
        canvas_meal.drawText("repas", 25, 50, my_paint);

        int position_x = 140;

        for (Day day : list_day) {
            int position_date_y = 100;
            int interval_title_meal_y = 50;
            int interval_element_y = 30;
            String date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = Utils.dateFormat("EEEE dd/MM", day.getDate());
            }
            my_paint.setTextAlign(Paint.Align.CENTER);
            my_paint.setStyle(Paint.Style.FILL);
            my_paint.setTextSize(35f);
            my_paint.setColor(Color.BLACK);
            canvas_meal.drawText(date, position_x, position_date_y, my_paint);
            int position_y = position_date_y;
            for (String meal : day.getMeals().keySet()) {
                position_y += interval_title_meal_y;
                my_paint.setTextAlign(Paint.Align.CENTER);
                my_paint.setStyle(Paint.Style.FILL);
                my_paint.setTextSize(30f);
                my_paint.setColor(Color.parseColor("#147587"));
                canvas_meal.drawText(meal, position_x, position_y, my_paint);
                for (String element : Objects.requireNonNull(day.getMeals().get(meal))) {
                    position_y += interval_element_y;
                    my_paint.setTextAlign(Paint.Align.CENTER);
                    my_paint.setStyle(Paint.Style.FILL);
                    my_paint.setTextSize(20f);
                    my_paint.setColor(Color.BLACK);
                    canvas_meal.drawText(element, position_x, position_y, my_paint);
                }
            }
            position_x += 280;
        }
        document.finishPage(page_meal);
    }
}
