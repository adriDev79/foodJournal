package com.example.appfood.asyncTask.asyncUser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.appfood.activity.MainActivity;
import com.example.appfood.activity.WelcomeActivity;
import com.example.appfood.bo.User;
import com.example.appfood.dao.SqlService;

import java.util.List;

/**
 * Tâche secondaire qui récupère le user.
 * Si le user n'existe pas, on lance l'activité pour la création du user.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
@SuppressLint("StaticFieldLeak")
public class GetUser extends AsyncTask<Void, User, List<User>> {

    private final Context ctx;

    public GetUser(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        SqlService sql_service = new SqlService();
        return sql_service.getAllUser(ctx);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onPostExecute(List<User> users) {
        super.onPostExecute(users);

        if (users.size() > 0) {
            SharedPreferences settings = ctx.getSharedPreferences("user", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("name_user", users.get(0).getName());
            editor.putBoolean("admin_user", users.get(0).isAdmin());
            editor.apply();

            Intent intent = new Intent(ctx.getApplicationContext(), MainActivity.class);
            ctx.startActivity(intent);
        } else {
            Intent intent = new Intent(ctx.getApplicationContext(), WelcomeActivity.class);
            ctx.startActivity(intent);
        }
    }
}
