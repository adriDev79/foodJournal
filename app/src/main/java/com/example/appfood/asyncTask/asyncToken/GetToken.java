package com.example.appfood.asyncTask.asyncToken;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.appfood.bo.TokenIg;
import com.example.appfood.dao.SqlService;

import java.util.List;

/**
 * Tâche secondaire qui récupère le token de connexion à instagram en bdd
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
@SuppressLint("StaticFieldLeak")
public class GetToken extends AsyncTask<Void, TokenIg, List<TokenIg>> {
    Context ctx;

    public GetToken(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected List<TokenIg> doInBackground(Void... voids) {
        SqlService sql_service = new SqlService();
        return sql_service.getAllTokenIg(ctx);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onPostExecute(List<TokenIg> tokens_ig) {
        super.onPostExecute(tokens_ig);
        String token = tokens_ig.get(0).getToken();

        SharedPreferences settings = ctx.getSharedPreferences("token", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", token);
        editor.apply();
    }
}
