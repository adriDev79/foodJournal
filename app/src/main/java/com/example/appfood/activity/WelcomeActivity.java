package com.example.appfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.R;
import com.example.appfood.bo.User;
import com.example.appfood.dao.SqlService;
import com.example.appfood.utils.Utils;

import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private final Context ctx = WelcomeActivity.this;
    LinearLayout ll_welcome;
    EditText et_name_user;
    TextView tv_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        et_name_user = findViewById(R.id.et_welcome_prenom);
        ll_welcome = findViewById(R.id.ll_welcome);
        tv_welcome = findViewById(R.id.tv_welcome);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        if (title != null) {
            tv_welcome.setText(title);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ll_welcome.setOnClickListener(validationUser(ctx, et_name_user));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static View.OnClickListener validationUser(Context ctx, EditText et_name_user) {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                String name_user = et_name_user.getText().toString();
                boolean admin = false;
                if (!name_user.equals("")) {
                    if (name_user.equals(Utils.NAME_ADMIN)) {
                        admin = true;
                    }
                    try {
                        boolean final_admin = admin;
                        if (final_admin) {
                            name_user = "admin";
                        }
                        String final_name_user = name_user;
                        new Thread(() -> {
                            SqlService sqlService = new SqlService();
                            List<User> users = sqlService.getAllUser(ctx);
                            if (users.size() > 0) {
                                User user = users.get(0);
                                user.setName(final_name_user);
                                user.setAdmin(final_admin);
                                sqlService.updateUser(ctx, user);
                            } else {
                                User user = new User();
                                user.setName(final_name_user);
                                user.setAdmin(final_admin);
                                sqlService.insertUser(ctx,user);
                            }
                            Intent intent = new Intent(ctx, MainActivity.class);
                            SharedPreferences settings = ctx.getSharedPreferences("user", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("admin_user", final_admin);
                            editor.putString("name_user", final_name_user);
                            editor.apply();
                            ctx.startActivity(intent);
                        }).start();
                    } catch (Exception e) {
                        Log.e("FOOD", e.getMessage());
                    }
                } else {
                    Toast.makeText(ctx, "Merci de saisir votre prenom !", Toast.LENGTH_LONG).show();
                }
            }
        };
    }
}