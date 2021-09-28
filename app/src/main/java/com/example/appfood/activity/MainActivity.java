package com.example.appfood.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appfood.R;
import com.example.appfood.helper.MainHelper;
import com.example.appfood.rest.RestService;
import com.example.appfood.utils.Utils;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final Context ctx = MainActivity.this;
    private final Activity activity = MainActivity.this;
    LayoutInflater inflater;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll_journal = findViewById(R.id.ll_journal);
        LinearLayout ll_envoi_journal = findViewById(R.id.ll_email);
        inflater = getLayoutInflater();

        MainHelper mh = new MainHelper();
        mh.getHash(ctx);

        TextView tv_journal = findViewById(R.id.tv_journal);
        String user_name ="";

        SharedPreferences settings = ctx.getSharedPreferences("user", 0);
        String u = settings.getString("name_user","");

        if (u != null) {
            String[] user = u.split(" ");
            if (user.length > 0) {
                user_name = user[0];
            }
        }
        tv_journal.setText(String.format("Journal de %s", user_name));

        ll_journal.setOnClickListener(Utils.onClickOpenActivity(ctx, JournalAlimentaireActivity.class));
        ll_envoi_journal.setOnClickListener(Utils.onClickOpenActivity(ctx, EnvoyerJournalActivity.class));
        try {
            postIG();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
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

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void postIG() throws ExecutionException, InterruptedException {
        RestService rs = new RestService();
        LinearLayout linearLayout = findViewById(R.id.gl_post_ig);
        rs.getPostIG(ctx, activity, linearLayout);
    }

    public void parametre(View view) {
        SharedPreferences settings = ctx.getSharedPreferences("user", 0);
        boolean admin = settings.getBoolean("admin_user",false);

        if (admin) {
            Intent intent = new Intent(ctx, AdminParametreActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ctx, ParametreActivity.class);
            startActivity(intent);
        }
    }

    public void contactAndMap(View view) {
        Intent intent = new Intent(ctx, ContactActivity.class);
        startActivity(intent);
    }
}