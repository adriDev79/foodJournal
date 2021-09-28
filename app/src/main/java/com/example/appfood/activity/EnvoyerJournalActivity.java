package com.example.appfood.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.appfood.R;
import com.example.appfood.asyncTask.asyncSendJournal.SendAndDownloadJournal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class EnvoyerJournalActivity extends AppCompatActivity {

    private final Context ctx = EnvoyerJournalActivity.this;
    GridLayout gridLayout;
    LinearLayout ll_envoyer_journal;
    LinearLayout ll_telecharger_journal;
    LinearLayout ll_date_picker_debut;
    LinearLayout ll_date_picker_fin;
    TextView tv_date_debut;
    TextView tv_jour_debut;
    TextView tv_date_fin;
    TextView tv_jour_fin;
    String name_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoyer_journal);
        gridLayout = findViewById(R.id.gl_envoyer_journal);
        ll_envoyer_journal = findViewById(R.id.ll_envoyer_journal);
        ll_telecharger_journal = findViewById(R.id.ll_telecharger_journal);
        ll_date_picker_debut = findViewById(R.id.ll_date_debut_journal);
        ll_date_picker_fin = findViewById(R.id.ll_date_fin_journal);
        tv_date_debut = findViewById(R.id.tv_date_debut_journal);
        tv_date_fin = findViewById(R.id.tv_date_fin_journal);
        tv_jour_debut = findViewById(R.id.tv_jour_debut_journal);
        tv_jour_fin = findViewById(R.id.tv_jour_fin_journal);

        Intent intent = getIntent();
        name_user = intent.getStringExtra("name_user");

        ImageButton iv_retour = findViewById(R.id.ib_envoyer_journal_retour);
        iv_retour.setOnClickListener(Utils.onClickOpenActivity(ctx, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ll_date_picker_debut.setOnClickListener(Utils.onClickCalendarJournal(ctx, tv_date_debut, tv_jour_debut));
        ll_date_picker_fin.setOnClickListener(Utils.onClickCalendarJournal(ctx, tv_date_fin, tv_jour_fin));
        ll_envoyer_journal.setOnClickListener(envoyerJournal(ctx, tv_date_debut, tv_date_fin, true, name_user));
        ll_telecharger_journal.setOnClickListener(envoyerJournal(ctx, tv_date_debut, tv_date_fin, false, name_user));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public View.OnClickListener envoyerJournal(Context ctx, TextView date_debut, TextView date_fin, boolean email, String name_user) {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    String debut = date_debut.getText().toString();
                    String fin = date_fin.getText().toString();

                    if (!(debut.equals("")&& fin.equals(""))){
                        Date dd = new Date(debut);
                        Date df = new Date(fin);
                        if (!df.before(dd)) {
                            SendAndDownloadJournal sendAndDownloadJournal = new SendAndDownloadJournal(ctx, date_debut, date_fin, email, name_user);
                            sendAndDownloadJournal.execute();
                        } else {
                            Toast.makeText(ctx, "La date de fin ne peut être anterieur à la date de début !", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ctx, "Merci de saisir les dates !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    messagePermission();
                }
            }
        };
    }
    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void messagePermission() {
        Snackbar.make(gridLayout, "Permission obligatoire, allez dans Autorisations > Stockage et cliquez sur \"Autoriser la gestion de tous les fichiers\" ", Snackbar.LENGTH_INDEFINITE)
            .setAction("Paramètres", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    final Uri uri = Uri.fromParts("package", EnvoyerJournalActivity.this.getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }).show();
    }
}