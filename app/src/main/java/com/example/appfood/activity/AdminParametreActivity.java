package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appfood.R;
import com.example.appfood.activity.MainActivity;
import com.example.appfood.activity.WelcomeActivity;
import com.example.appfood.rest.RestService;
import com.example.appfood.utils.Utils;

import java.util.concurrent.ExecutionException;

public class AdminParametreActivity extends AppCompatActivity {
    Context ctx = this;
    ImageView iv_retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_parametre);
        iv_retour = findViewById(R.id.iv_home_parametres);
        iv_retour.setOnClickListener(Utils.onClickOpenActivity(ctx, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void paramUpdatePrenom(View view) {
        Intent intent = new Intent(ctx, WelcomeActivity.class);
        intent.putExtra("title", "Modifier votre prénom et nom");
        startActivity(intent);
    }

    public void paramUpdateToken(View view) throws ExecutionException, InterruptedException {
        RestService rs = new RestService();
        rs.updateToken(ctx);
    }
}