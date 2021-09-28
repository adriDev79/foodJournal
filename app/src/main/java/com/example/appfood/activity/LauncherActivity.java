package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.appfood.R;
import com.example.appfood.asyncTask.asyncUser.GetUser;
import com.example.appfood.asyncTask.asyncToken.InsertToken;
import com.example.appfood.utils.Utils;

import java.time.LocalDate;

public class LauncherActivity extends AppCompatActivity {
    private final Context ctx = LauncherActivity.this;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
       laucnhWithEndDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void laucnhNoEndDate(LocalDate now) {
        LocalDate fin_demo = now.plusDays(7);

        SharedPreferences settings = ctx.getSharedPreferences("time_end_demo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("time_end_demo", fin_demo.toString());
        editor.apply();

        // Ajout du token si il n'existe pas.
        try {
            InsertToken atit = new InsertToken();
            atit.execute(ctx);
        } catch (Exception e) {
            Log.i("TESTER", e.getMessage());
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GetUser getUser = new GetUser(ctx);
                getUser.execute();
            }
        }, Utils.DELAY_LAUNCHER_PAGE);
    }

    public void laucnhWithEndDate() {
        // Ajout du token si il n'existe pas.
        try {
            InsertToken atit = new InsertToken();
            atit.execute(ctx);
        } catch (Exception e) {
            Log.i("TESTER", e.getMessage());
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GetUser getUser = new GetUser(ctx);
                getUser.execute();
            }
        }, Utils.DELAY_LAUNCHER_PAGE);
    }
}