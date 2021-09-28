package com.example.appfood.asyncTask.asyncToken;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.appfood.R;
import com.example.appfood.bo.TokenIg;
import com.example.appfood.dao.SqlService;

import java.time.LocalDate;
import java.util.List;

/**
 * Tâche secondaire qui insert le token instagram en bdd si il n'existe pas.
 */
@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class InsertToken extends AsyncTask<Context, TokenIg, Void> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(Context... contexts) {
        SqlService sql_service = new SqlService();
        List<TokenIg> tokens_IG = sql_service.getAllTokenIg(contexts[0]);
        if (tokens_IG.size() > 0) {
        } else {
            LocalDate creation = LocalDate.now();
            LocalDate expiration = creation.plusDays(60);
            TokenIg token_IG = new TokenIg();
            token_IG.setDateCreation(creation.toString());
            token_IG.setDateExpiration(expiration.toString());
            token_IG.setToken(contexts[0].getString(R.string.token));
            sql_service.insertTokenIg(contexts[0], token_IG);
        }
        return null;
    }
}
